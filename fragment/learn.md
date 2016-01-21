###问题
1. fragment不重复new
2. 如何能回退？
3. demo 
4. 从生命周期destroy和create分析fragment优化
5. 利用生命周期第二条路优化 (集合优化\适配器优化\布局优化)  
	//这种优化只适用于addToBackStack()方案的第二条生命周期路线  	
	https://hzj163.gitbooks.io/android-fragment/content/fragmentyi_xie_you_hua.html  
5. fragment懒加载
6. 锁屏周期变化	
	
###锁屏解锁 周期变化

###屏幕转动 周期变化




###Fragment的生命周期
```
1.onAttach->onCreate.....->onDestroy->onDettach

  这条生命周期是一个完整的生命周期，onAttach表示从Actitivity附着，onDettach表示从Activity剥离，一般来说，调用add方法后悔有这条流程。

  注意：在FragmentActivity中使用事务的方法attach和dettach并不会调用onAttach和onDettach，那会发生什么变化呢，看第三条

2. onSaveInstanceState-->onStop ... onStart->onResume-->....

   这条生命周期和Activity的onRestart有着一定的关联。

   注意，Activity每次调用onRestart之后，Fragment就会执行这条生命周期，但是要注意的是，这条生命周期并不可靠，有时不会执行。

3.onDestroyView-> .... ->onCreateView ->onViewCreate

  生命周期反了么，答案是否定的。调用者条生命周期往往是使用了事务的方法dettach和attach。

注意：在这种流程中，可以更好的管理Fragment的加载，也可以解决叠加问题，生命周期循环问题。

4.重复onAttach->onCreate.....->onDestroy->onDettach

这条生命周期是由于每次都是用的是replace方法

5.持久态

在经历了.onAttach->onCreate->onCreatView-->...->onResume之后，如果没掉用replace，add,attach,dettach，而是使用了简单的hide,show等方法

注意：这种可用于回退栈操作。
```



###fragment防止重复加载
#####问题
辛苦耗时加载出来的页面又要重新加载一次，这样也就给内存增加了无意思的压力，用户体验上也不大友好，尤其在有网络请求等开销时长比较长的情况下
#####现状
* 默认情况下当ViewPager滑动到第三页的时候，第一页的Fragment就会执行onDestroyView，当再次滑动到第二页的时候，第一页的Fragment的onCreateView又会重新执行绘制页面
* fragment0,1,2,3; viewpager_init(0),初始化时会加载0,1; 向右滑动加载2,再向右初始化3    ???
* viewpager会加载和当前页面相连的两个fragment，会销毁相邻第三个页面的view，再次调用是会重新oncreateview和onactivityreate
#####解决思路1 
```java
//加入rootView,缓存加载后的view，如果有就不重新加载数据。
//加入判断是否已经加载数据完成的标志变量，如果已经加载了数据，就不重新加载数据
public abstract class BaseFragment extends Fragment {  
  
    //根部view  
    private View rootView;  
    protected Context context;  
    private Boolean hasInitData = false;  
  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        context = getActivity();  
    }  
  
	/**
	 * onDestroyView的执行和Activity的onDestroy不一样，不会销毁当前的页面，所以Fragment的所有成员变量的引用都还在。那就好办了，我们在onCreateView的时候，先判断该取到的数据是否为空，比如Fragment的根视图rootView，网络请求获取到的数据等，如果不为空就不用再次执行
	 */
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
                             Bundle savedInstanceState) {  
        if (rootView == null) {  
            rootView = initView(inflater);  
        }  
        return rootView;  
    }  
  
    @Override  
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState);  
        if (!hasInitData) {  
            initData();  
            hasInitData = true;  
        }  
    }  
	//重要点： 如果重用rootView的话，一定要记得在onDestroyView里面把rootView先给移除掉，因为已经有过父布局的View是不能再次添加到另一个新的父布局上面的。代码如下
    @Override  
    public void onDestroyView() {  
        super.onDestroyView();  
        ((ViewGroup) rootView.getParent()).removeView(rootView);  
    }  
  
    /** 
     * 子类实现初始化View操作 
     */  
    protected abstract View initView(LayoutInflater inflater);  
  
    /** 
     * 子类实现初始化数据操作(子类自己调用) 
     */  
    public abstract void initData();  
  
    /** 
     * 封装从网络下载数据 
     */  
    protected void loadData(HttpRequest.HttpMethod method, String url,  
                            RequestParams params, RequestCallBack<String> callback) {  
        if (0 == NetUtils.isNetworkAvailable(getActivity())) {  
            new CustomToast(getActivity(), "无网络，请检查网络连接！", 0).show();  
        } else {  
            NetUtils.loadData(method, url, params, callback);  
        }  
    }  
}

```

#####解决思路2
```java
//replace()这个方法只是在上一个Fragment不再需要时采用的简便方法
public void switchContent(Fragment fragment) {
	if(mContent != fragment) {
		mContent = fragment;
		mFragmentMan.beginTransaction()
			.setCustomAnimations(android.R.anim.fade_in, R.anim.slide_out)
			.replace(R.id.content_frame, fragment) // 替换Fragment，实现切换
			.commit();
	}
}
//正确的切换方式是add()，切换时hide()，add()另一个Fragment；再次切换时，只需hide()当前，show()另一个
public void switchContent(Fragment from, Fragment to) {
	if (mContent != to) {
		mContent = to;
		FragmentTransaction transaction = mFragmentMan.beginTransaction().setCustomAnimations(
				android.R.anim.fade_in, R.anim.slide_out);
		if (!to.isAdded()) {    // 先判断是否被add过
			transaction.hide(from).add(R.id.content_frame, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
		} else {
			transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
		}
	}
}

//查找所有fragment getFragmentManager().findFragmentById
if (savedInstanceState == null) {
    // getFragmentManager().beginTransaction()...commit()
}else{
  //先通过id或者tag找到“复活”的所有UI-Fragment
  UIFragment fragment1 = getFragmentManager().findFragmentById(R.id.fragment1);
  UIFragment fragment2 = getFragmentManager().findFragmentByTag("tag");
  UIFragment fragment3 = ...
  ...
  //show()一个即可
  getFragmentManager().beginTransaction()
          .show(fragment1)
          .hide(fragment2)
          .hide(fragment3)
          .hide(...)
          .commit();
}	
```


###fragment和activity交互
- http://hukai.me/android-training-course-in-chinese/basics/fragments/communicating.html
- 通常fragment之间可能会需要交互，比如基于用户事件改变fragment的内容。所有fragment之间的交互需要通过他们关联的activity，两个fragment之间不应该直接交互。





###参考
- http://www.yrom.net/blog/2013/03/10/fragment-switch-not-restart/
- http://hukai.me/android-training-course-in-chinese/basics/fragments/communicating.html
- http://blog.csdn.net/harvic880925/article/details/44927375
- http://www.lightskystreet.com/2015/02/02/fragment-note/
- https://github.com/bboyfeiyu/android-tech-frontier/tree/master/androidweekly/Square%20开源库Flow和Mortar的介绍
- http://toughcoder.net/blog/2015/04/30/android-fragment-the-bad-parts/
- http://blog.csdn.net/u013173289/article/details/44002371	
- http://mobile.51cto.com/abased-446691.htm
- https://hzj163.gitbooks.io/android-fragment/content/fragmentyi_xie_you_hua.html
- http://my.oschina.net/ososchina/blog/342210
- http://www.trinea.cn/android/android-performance-demo/