#Service简单解析
###分类
######分类1
Local服务: 该服务依附在主进程上，主进程被Kill后，服务便会终止。Local服务因为是在同一进程因此不需要IPC，也不需要AIDL  
Remote服务：该服务是独立的进程，在Activity所在进程被Kill的时候，该服务依然在运行。 android:processs  
######分类2
前台服务： 会在通知一栏显示 ONGOING 的 Notification      
后台服务: 认的服务即为后台服务，即不会在通知一栏显示 ONGOING 的 Notification   
######分类3
startService stopService  
bindService unbindService   
start+bind  


###Service 与 Thread 的区别
Thread: 当一个 Activity 被 finish 之后，如果你没有主动停止 Thread 或者 Thread 里的 run 方法没有执行完毕的话，Thread 也会一直执行  
Service:  service+thread可以解决"不停地隔一段时间连接服务器做某种同步"(另application的创建的全局thread也可以实现? )  


###Service的生命周期
#####start service
(其生命周期为context.startService() ->onCreate()- >onStart()->Service running-->(如果调用context.stopService() )->onDestroy() ->Service shut down)
被开启的service通过其他组件调用 startService()被创建。  
这种service可以无限地运行下去，必须调用stopSelf()方法或者其他组件调用stopService()方法来停止它。  
当service被停止时，系统会销毁它。  
Service被startService 方法多次启动，那么onCreate方法只会调用一次，onStart将会被调用多次   
多少次startService()，系统只会创建Service的一个实例,因此都只需调用一次 stopService()来停止   
#####bind service
(context.bindService()->onCreate()->onBind()->Service running-->onUnbind() -> onDestroy() ->Service stop)
被绑定的service是当其他组件（一个客户）调用bindService()来创建的。  
客户可以通过一个IBinder接口和service进行通信。   
客户可以通过 unbindService()方法来关闭这种连接。  
一个service可以同时和多个客户绑定，当多个客户都解除绑定之后，系统会销毁service。
onBind(只一次，不可多次绑定)      
Context(Activity等),会和Service绑定在一起，Context退出了，service就会onDestroy    
多个客户端可以绑定至同一个服务。如果服务此时还没有加载，bindService()会先加 载它  (bindService(intent, mServiceConnection, BIND_AUTO_CREATE);//如果没有，自动创建service)   
#####start+bind
比如，一个后台音乐service可能因调用 startService()方法而被开启了，稍后，可能用户想要控制播放器或者得到一些当前歌曲的信息，可以通过bindService()将一个activity和service绑定。这种情况下，stopService()或 stopSelf()实际上并不能停止这个service，除非所有的客户都解除绑定。
onCreate始终只会调用一次，对应startService调用多少次   
Service 的终止，需要unbindService与stopService同时调用，才能终止 Service，不管startService 与 bindService 的调用顺序
#####停止
当一个Service被终止（1、调用stopService；2、调用stopSelf；3、不再有绑定的连接（没有被启动））时，onDestroy方法将会被调用   
start+stop 和 bind+unbind 必须是一对  



###主要使用方法

#####Remote AIDL  (参考ServiceDemo/bindservice/MusicService)

![alt text](https://raw.githubusercontent.com/fitzlee/GuruAndroid/master/_images/aidl_remote_register_callback.jpg)

```java
interface IParticipateCallback {
    // 用户加入或者离开的回调
    void onParticipate(String name, boolean joinOrLeave);
}
interface IRemoteService {
    int someOperate(int a, int b);

    void join(IBinder token, String name);
    void leave(IBinder token);
    List<String> getParticipators();

    void registerParticipateCallback(IParticipateCallback cb);
    void unregisterParticipateCallback(IParticipateCallback cb);
}

public class RemoteService extends Service {

    private static final String TAG = RemoteService.class.getSimpleName();

    private List<Client> mClients = new ArrayList<>();

    private RemoteCallbackList<IParticipateCallback> mCallbacks = new RemoteCallbackList<>();

    private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {
        @Override
        public int someOperate(int a, int b) throws RemoteException {
            Log.d(TAG, "called RemoteService someOperate()");
            return a + b;
        }

        @Override
        public void join(IBinder token, String name) throws RemoteException {
            int idx = findClient(token);
            if (idx >= 0) {
                Log.d(TAG, "already joined");
                return;
            }

            Client client = new Client(token, name);

            // 注册客户端死掉的通知
            token.linkToDeath(client, 0);
            mClients.add(client);

            // 通知client加入
            notifyParticipate(client.mName, true);
        }

        @Override
        public void leave(IBinder token) throws RemoteException {
            int idx = findClient(token);
            if (idx < 0) {
                Log.d(TAG, "already left");
                return;
            }

            Client client = mClients.get(idx);
            mClients.remove(client);

            // 取消注册
            client.mToken.unlinkToDeath(client, 0);

            // 通知client离开
            notifyParticipate(client.mName, false);
        }

        @Override
        public List<String> getParticipators() throws RemoteException {
            ArrayList<String> names = new ArrayList<>();
            for (Client client : mClients) {
                names.add(client.mName);
            }
            return names;
        }

        @Override
        public void registerParticipateCallback(IParticipateCallback cb) throws RemoteException {
            mCallbacks.register(cb);
        }

        @Override
        public void unregisterParticipateCallback(IParticipateCallback cb) throws RemoteException {
            mCallbacks.unregister(cb);
        }
    };

    public RemoteService() {
    }

    private int findClient(IBinder token) {
        for (int i = 0; i < mClients.size(); i++) {
            if (mClients.get(i).mToken == token) {
                return i;
            }
        }
        return -1;
    }

    private void notifyParticipate(String name, boolean joinOrLeave) {
        final int len = mCallbacks.beginBroadcast();
        for (int i = 0; i < len; i++) {
            try {
                // 通知回调
                mCallbacks.getBroadcastItem(i).onParticipate(name, joinOrLeave);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mCallbacks.finishBroadcast();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("service","destroy");
        // 取消掉所有的回调
        mCallbacks.kill();
    }

    private final class Client implements IBinder.DeathRecipient {
        public final IBinder mToken;
        public final String mName;

        public Client(IBinder token, String name) {
            mToken = token;
            mName = name;
        }

        @Override
        public void binderDied() {
            // 客户端死掉，执行此回调
            int index = mClients.indexOf(this);
            if (index < 0) {
                return;
            }

            Log.d(TAG, "client died: " + mName);
            mClients.remove(this);

            // 通知client离开
            notifyParticipate(mName, false);
        }
    }
}

```


#####Local BindService
```java
public class MusicService extends Service
	public class MyBinder extends Binder {
		public MusicService getService() {
			return MusicService.this;
		}
	}
	
	public void MyMethod(){
		//your methods
	}
}
```


```java
public class MainActivity extends Activity{

	ServiceConnection conn = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName name) {
				
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				MusicService.MyBinder binder = (MusicService.MyBinder) service;
				bindService = binder.getService();
			}
	};
	
	void test(){
		//调用service的接口
		bindService.MyMethod();
		//注：可以使用broadcast或者注册回调interface来实现service主动通知Activity
	}
}
```

#####IntentService = thread+service

```java
public class MyIntentService extends IntentService {
	private static String TAG = "MusicService";

	public MyIntentService() {
		super("MyIntentServiceName");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// IntentService使用队列的方式将请求的Intent加入队列，然后开启一个worker
		// thread(线程)来处理队列中的Intent
		// 对于异步的startService请求，IntentService会处理完成一个之后再处理第二个
		try {
			int time = 20000;
			Log.e(TAG, "begin sleep" + time);
			Thread.sleep(time);
			Log.e(TAG, "after sleep" + time);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
```

#####Broadcast+Service
```java
public MyService extends service{

	@Override
	public void onDestroy() {   //重写的onDestroy方法
		myThread.flag = false;  //停止线程运行
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		myThread = new MyThread() ;  //初始化线程
		myThread.start();            //启动线程
		return super.onStartCommand(intent, flags, startId);
	}

	//定义线程类
	class MyThread extends Thread{
		boolean flag = true;        //循环标志位
		int c = 0;                  //其值为发送的消息
		@Override
		public void run() {
			while(flag){
				Intent i = new Intent("cn.com.sgmsc.ServBroad.myThread");//创建Intent
				i.putExtra("myThread", c);      //放入数据
				sendBroadcast(i);               //发送广播
				c++;
				try{
					Thread.sleep(1000);        //睡眠指定毫秒数
				}catch(Exception e){           //捕获异常
					e.printStackTrace();       //打印异常
				}
			}
		}
	};
}
```

#####System Service
```java
TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
SmsManager sManager = SmsManager.getDefault();
```


###原理


[service_life]: http://www.cnblogs.com/mengdd/archive/2013/03/24/2979944.html

