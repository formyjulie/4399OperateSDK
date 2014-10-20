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
A: 请联系运营人员提供后台入口，或提供游戏名称、游戏内货币名称、游戏币与人民币兑换率给运营人员代为配置GAME_KEY以及服务端密钥

充值
---------------
Q: 无法充值时，该如何解决？    
A: 请分别测试支付宝、短代、游币兑换这3个渠道的充值来定位问题。  
- 完全无法进入充值主界面进行充值渠道选择。  
  请检查初始化时是否传入了正确地GameKey  
- 以上3个渠道均无法充值
  请检查充值时传入的mark参数是否符合要求，要求32位以下，仅包含字母与数字，无任何符号，且每次充值mark不能有重复。  
  如果mark号无问题，请联系运营人员要求核对Client_ID一致性。
- 仅支付宝无法充值
  请检查商品名是否超过8个字符

Q: 为什么每次充值显示的渠道都不一样？
A: 因为SDK会根据传入的充值金额，自动过滤掉不匹配的渠道

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


Q: 文档里订单查询接口中的order字段是4399生成的订单号，还是我们这边的mark？      
A: 是4399的订单号

调试及测试
-----------------
Q: 如何调试，找到问题？        
A: 有的，您可用logcat查看日志，快速定位问题。   
```java
>OperateCenterConfig mOpeConfig = newOperateCenterConfig.Builder(this)
		.setDebugEnabled(true)  //打开调试开关
		.setOrientation(orientation)
		.setShowPopWindow(true)
		.setGameKey("40027")
		.setGameName("测试游戏")
		.build();
```
setDebugEnabled为true后，就能打印出各个逻辑的log。

Q:游戏为横屏，但sdk弹出的界面为竖屏。   
A:我们sdk支持横竖屏配置，使用接口OperateCenterConfig.Builder(this).setOrientation(orientation)设置，也可以在AndroidManifest.xml 中设置screenOrientation = "landscape".但是某些第三方界面，如支付宝界面，只能在AndroidManifest.xml中设置。

Q:客户端哪个参数是可以放服务器id？    
A:setServerId，请在用户登录后立即设置，如果有选择角色选择服务器的，请在用户选择结束后立即设置。

Q:有没有虚拟游币给测试充值？
A:目前暂无测试环境。



