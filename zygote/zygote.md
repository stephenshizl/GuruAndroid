init.rc[init.rc解析]

service zygote /system/bin/app_process -Xzygote /system/bin --zygote --start-system-server
    class main
    socket zygote stream 660 root system
    onrestart write /sys/android_power/request_state wake
    onrestart write /sys/power/state on
    onrestart restart media
    onrestart restart netd
	
	
app_process -> frameworks/base/cmds/app_process/app_main.cpp



[图解Android - Zygote, System Server 启动分析]: http://www.cnblogs.com/samchen2009/p/3294713.html
[init.rc解析]: http://allenlsy.com/android-kernel-2/#start-service

[zygote分析--AndroidRuntime::start()]；http://www.jianshu.com/p/ef49e0ce0894
http://www.programering.com/a/MDM5EzNwATM.html

[dalvikvm]: http://www.boyunjian.com/do/article/snapshot.do?uid=7952267932717011527
[Dalvik虚拟机JNI方法的注册过程分析]: http://blog.csdn.net/luoshengyang/article/details/8923483
[dalvikvm compile]: http://blog.csdn.net/rambo2188/article/details/7391000
[dex]: http://source.android.com/devices/tech/dalvik/dex-format.html
[Zygote进程的启动流程]: http://blog.csdn.net/jltxgcy/article/details/48488921?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io


[Package Installer]: https://dzone.com/articles/depth-android-package-manager


[SystemServer]: http://roylee17.blogspot.com/2011/02/zygote-initialization-flow.html