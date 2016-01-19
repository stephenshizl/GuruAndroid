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

[service_life]: http://www.cnblogs.com/mengdd/archive/2013/03/24/2979944.html

