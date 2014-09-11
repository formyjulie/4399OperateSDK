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
Q: mark参数最大限制多长？    
A: 

Q: recharge充值接口，充值失败。    
A:
- 核对下mark参数是否为纯英文和数字组成；
- 问一下服务端clientId是否配置正确；
- 是否有配置Gamekey,在AndroidManifest.xml里；(充值后，游戏服务端没有任何返回消息）
- 核对下后台，gamekey和clientId是否匹配。

Q: 游币转换    
A: 返回给游戏方服务端的充值的钱不一定等于玩家充值的数目； 而是x根据各个渠道配置的转换率.

Q: 调用接口，出现挂起。挂起log为：Can't create handler inside thread that has not called Looper.prepare();    
A: 充值接口的调用，必须在UI线程。可以参考：
```java
    runOnUiThread(new Runnable() {
			
		@Override
		public void run() {
		    // 调用充值接口
				
		}
    });

```
在Activity对象里，才用这样的调用方式。


Q: 文档里订单查询接口中的order字段是4399生成的订单号，还是我们这边的？      
A: 

调试及测试
-----------------
Q: 如何调试，找到问题？        
A: 有的，您可用logcat查看日志，快速定位问题。   
```java
>OperateCenterConfig mOpeConfig = newOperateCenterConfig.Builder(this)

		*.setDebugEnabled(true)*
		.setOrientation(orientation)

		.setShowPopWindow(true)
		.setSupportExcess(true)
		.setGameKey("40027")
		.setGameName("测试游戏")

		.build();
```
setDebugEnabled为true后，就能打印出各个逻辑的log。

Q:游戏为横屏，但sdk弹出的界面为竖屏。   
A:我们sdk支持横竖屏配置， 需要AndroidManifest.xml 设置screenOrientation = "landscape".

Q:客户端哪个参数是可以放服务器id？    
A:setServerId

Q:有没有虚拟游币给测试充值？
A:没法申请游币进行测试。一般都是两边各自真实测试充值的。



