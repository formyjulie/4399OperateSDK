联系我们
-------------
Q:接入过程遇到问题，如何联系我们？   
A:请详细阅读下面的FAQ，我们收集了一些常见问题的解答。如果仍有问题，可以联系我们运营人员。

关于SDK
------------
Q: SDK支持什么版本的安卓系统   
A: 目前支持Android2.3及以上系统。

接入前准备
------------
Q: 如何获取GAME_KEY、登陆验证密钥、订单查询密钥   
A: 

账号相关
--------------

充值
---------------
调试及测试
-----------------
Q: 如何调试，找到问题？     
A: 有的，您可用logcat查看日志，快速定位问题。   
```java
>OperateCenterConfig mOpeConfig = newOperateCenterConfig.Builder(this)

		`.setDebugEnabled(true)`
		.setOrientation(orientation)

		.setShowPopWindow(true)
		.setSupportExcess(true)
		.setGameKey("40027")
		.setGameName("测试游戏")

		.build();
```
setDebugEnabled为true后，就能打印出各个逻辑的log。



