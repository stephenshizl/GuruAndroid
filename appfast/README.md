#应用快速开发


###前台&后台

#####1. 前台
- UI

#####2. 后台
- Cache
- Database
- Request
- DesignModel
- Bus


###项目框架
adapter 适配器，如果业务复杂，根据不同的业务可以添加子包来进行分类；
base 用来存放View的基类，例如BaseAcitivity、BaseFragment，甚至可以添加某些不同actionbar主题的Base类；
common 当然是存放一些共用的配置类信息，常量等等；
controller 控制器，将一部分的业务类需求放到里面，充当db和View交互的中间层，减少Activity中业务的复杂性；
db数据库类
event 观察者模式，事件通知；
task一些AsyncTask任务类
view一些自定义组件
vo 值对象，其实就是给各个组件使用的对象，比如ListView的Item对象等等
widget UI界面
AppContext 自定义Application类


###开源库
UI: 各种开源控件,可以在这里找 Trinea/android-open-project · GitHub
依赖注入：Dagger + ButterKnife
图片加载：Picasso
网络请求: Retrofit + OkHttp + Gson
数据库访问: Content Provider + Schematic, 或某款orm
消息事件队列：otto


###各种免费好用的第三方开发者服务
···
统计分析
国内：Talking Data
国外: Flurry, 国外统计分析系统的标杆，免费的。

Crash分析
国内: 腾讯Bugly, 号称全球唯一自带ANR收集，其实原理很简单，不知别家为何不做。
国外: Crashlytics, 已经收归Twitter Fabric开发者工具集，免费好用。

推送
国内: LeanCloud，这个没实际用过，身边朋友反馈很好。
国外: Parse, 正如覃超所说,FB也在用，30qps免费限制，一般中小应用够用。

分享
国内: ShareSDK，专业做社交分享。
国外: 各社交平台自家SDK, 注意不同国家主流社交平台不同。

评论
国内：畅言, 基本抄的Disqus, 免费，算是良心产品了。
国外：Disqus, 基本不用考虑其他家的，虽然确实有竞品。

广告变现
国内：百度 or 广点通，两个效果差不多。
国外：FB or Google，做native广告，效果最佳。

支付
国内: 支付宝, 微信
国外: payssion, 专业做海外跨境收款的，能省很多事。

短信验证
国内：没用过，Google找一家最便宜的就行。
国外：Fabric Digits, twitter出品，居然不要钱。

灰度测试
国内：AppAdhoc，移动AB测试国内最专业的一家。
国外：optimizely, 支持Android, iOS, 直接在线改UI做AB测试, 三观都要颠覆了。

云测
国内：百度云测试。
国外：test in, 其实百度的也跑，国内的Android设备都卖到国外去了。

客服
国内：微客服, 有免费额度，中小应用够用。
国外：helpshift，国外最专业的客服平台。

推广
国内：这个真不了解。
国外：Appsflyer, 海外推广为数不多的选择之一。

可视化分析
国内：growingio, 还在内测中，linkedin数据分析大牛回国创业，据说黑科技，可以直接在app上实时查看各种转化率数据。
国外：appsee, 绝对黑科技，PM最爱，转化率什么的再也不怕上不去了，用过后我们团队成员一致好评，在我的微信公众号AndroidTrending里有专门文章介绍使用体验。
···




#参考
[在Android开发过程中搭建一个自己的应用框架有几个步骤？需要注意什么？][app_frame]
[如何姿势正确地开始一个Android项目][jianshu]


---
[app_frame]: http://www.zhihu.com/question/35009721
[jianshu]: http://www.jianshu.com/p/f1ccc9762a17