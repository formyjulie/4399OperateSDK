4399运营SDK Android客户端v2.1.1.12接入说明
======================

##修改记录
版本号  |   修改日期    |   修改者  |   修改内容
--------|---------------|-----------|-------------------------
v1.0.0  |   2014-07-08  |   张生    |   创建文档
v1.0.1  |   2014-07-14  |   张生    |   增加部分接口参数的说明
v2.0.0  |   2014-08-22  |   郑旭    |   增加全局监听、修改SDK部署配置、修改部分接口调用方式
v2.1.0  |   2014-10-14  |   郑旭    |   移除悬浮窗配置接口，移除自动更新接口，新增自定义更新接口，替换移动短代运营商配置
v2.1.1.12	|   2014-11-21  |   张生    |   增加充值测试模式，游戏退出接口，获取SDK版本号接口说明，mark字符扩展，充值金额改为整型

#目录

[1 文档说明](#文档说明)  
&nbsp;&nbsp;&nbsp;&nbsp;[1.1 功能描述](#功能描述)  
&nbsp;&nbsp;&nbsp;&nbsp;[1.2 阅读对象](#阅读对象)  
&nbsp;&nbsp;&nbsp;&nbsp;[1.3 开发包内容](#开发包内容)  
[2 集成流程](#集成流程)  
&nbsp;&nbsp;&nbsp;&nbsp;[2.1 接入前期准备](#接入前期准备)  
&nbsp;&nbsp;&nbsp;&nbsp;[2.2 SDK集成流程](#SDK%E9%9B%86%E6%88%90%E6%B5%81%E7%A8%8B)  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[2.2.1 关联资源工程](#关联资源工程)  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[2.2.2 配置AndroidManifest.xml文件](#%E9%85%8D%E7%BD%AEandroidmanifestxml%E6%96%87%E4%BB%B6)  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[2.2.3 代码混淆配置](#代码混淆配置)  
[3 接入流程](#接入流程)  
&nbsp;&nbsp;&nbsp;&nbsp;[3.1 初始化【必接】](#初始化与析构)  
&nbsp;&nbsp;&nbsp;&nbsp;[3.2 设置切换用户监听器](#设置切换用户监听器)  
&nbsp;&nbsp;&nbsp;&nbsp;[3.3 用户登录【必接】](#用户登录)  
&nbsp;&nbsp;&nbsp;&nbsp;[3.4 获取当前登录用户信息](#获取当前登录用户信息)  
&nbsp;&nbsp;&nbsp;&nbsp;[3.5 用户切换](#用户切换)  
&nbsp;&nbsp;&nbsp;&nbsp;[3.6 用户注销](#用户注销)  
&nbsp;&nbsp;&nbsp;&nbsp;[3.7 游戏关闭【必接】](#游戏关闭)  
&nbsp;&nbsp;&nbsp;&nbsp;[3.8 登录状态查询](#登录状态查询)  
&nbsp;&nbsp;&nbsp;&nbsp;[3.9 获取缓存用户名列表](#获取缓存用户名列表)  
&nbsp;&nbsp;&nbsp;&nbsp;[3.10 删除缓存用户名](#删除缓存用户名)  
&nbsp;&nbsp;&nbsp;&nbsp;[3.11 设置用户所在服务器ID【必接】](#%E8%AE%BE%E7%BD%AE%E7%94%A8%E6%88%B7%E6%89%80%E5%9C%A8%E6%9C%8D%E5%8A%A1%E5%99%A8id)  
&nbsp;&nbsp;&nbsp;&nbsp;[3.12 检查更新](#检查更新)  
&nbsp;&nbsp;&nbsp;&nbsp;[3.13 充值【必接】](#充值)  
&nbsp;&nbsp;&nbsp;&nbsp;[3.14 获取SDK版本号](#获取SDK版本号)  
&nbsp;&nbsp;&nbsp;&nbsp;[3.15 获取状态信息](#获取状态信息)  
&nbsp;&nbsp;&nbsp;&nbsp;[3.16 析构【必接】](#析构)  
# 文档说明
## 功能描述
4399运营SDK（以下简称：SDK）主要用来向第三方游戏开发者提供便捷、安全一级可靠的4399账户登录、多渠道充值付费、版本升级检测等功能。本文主要描述SDK接口的使用方法，供合作伙伴的开发者接入使用。

## 阅读对象
本文档面向具有一定Android客户端开发能力，了解Android客户端的开发和管理人员。

## 开发包内容

 - 4399运营SDK（Android）接入说明：SDK接入文档，即本文档
 - m4399RechargeSDK：SDK资源文件工程内含SDK jar包
 - m4399OperateSDKDemo工程：Demo工程

# 集成流程
## 接入前期准备
1. 向4399运营人员提供游戏名称、游戏内货币名称、人民币与游戏币的兑换率
2. 4399运营人员会提供接入时需要的`GameKey`和`Secrect`。
3. GameKey为接入客户端SDK时使用，在初始化SDK时传入。请勿将Secrect写入游戏客户端。
4. GameKey，Secrect同时需要配置在服务端，详见[服务端接口文档](/Document/ServerDocument.md)。

## SDK集成流程
假设现在你的工程目录名字叫project，下面将具体介绍如何将SDK接入project中。

### 关联资源工程
1. 将m4399OperateSDK工程关联到project
* 将m4399OperateSDK导入到eclipse中
* 右键点击4399OperateSDK工程名→Properties→Android
* 勾选Is Library→OK
* 右键点击project工程名→Properties→Add
* 在弹出的对话框中点选资源工程m4399OperateSDK→OK  

若游戏仅支持部分指令集，需要在引入资源工程后将m4399RechargeSDK\lib\目录下未使用的指令集文件夹删除。如游戏仅支持arm6（armeabi），即可将其余的x86、arm64-v8a、armeabi-v7a文件夹删除。

### 配置AndroidManifest.xml文件
- 添加SDK所需的权限
``` xml
<uses-permission android:name="android.permission.CALL_PHONE"/>
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" 	/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.SEND_SMS" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```
- 注册SDK相关Activity&Service，注意必须放入`<application>`元素区块内
```xml
        <!-- For 4399 Recharge SDK -->
        <activity
            android:name="cn.m4399.recharge.ui.activity.RechargeActivity"
            android:launchMode="singleTask"
            android:configChanges="orientation|screenSize|keyboardHidden"
            android:theme="@style/m4399ActivityTheme" />

        <!-- For 4399 Operation SDK -->
        <activity
            android:name="cn.m4399.operate.ui.activity.LoginActivity"
            android:launchMode="singleTask"
            android:theme="@style/m4399TransparentStyle" />
        <activity
            android:name="cn.m4399.operate.ui.activity.UserCenterActivity"
            android:hardwareAccelerated="false"
            android:theme="@android:style/Theme.NoTitleBar.Fullscreen" />
        <activity
            android:name="cn.m4399.operate.ui.activity.CustomWebActivity"
            android:configChanges="orientation|screenSize|keyboardHidden"
            android:theme="@android:style/Theme.NoTitleBar.Fullscreen" />
            
	<!--------以下为第三方支付SDK Activity&Service配置------------>
        <activity android:name="com.alipay.sdk.app.H5PayActivity" 
            android:screenOrientation="landscape"/>
```
* 注：第三方支付SDK的Activity需在AndroidManifest.xml中强制配置横竖屏，请游戏方根据游戏的横竖屏要求手工配置`landscape`|`portrait`

### 代码混淆配置
如果游戏有需要进行代码混淆，请不要混淆联编的jar包下的类，可以在`proguard.cfg`文件里追加以下配置排除SDK jar包中得类

```
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**

-dontwarn com.unipay.**
-keep class cn.m4399.operate.** {*;}
-keep class cn.m4399.recharge.** {*;}
-keepclassmembers class cn.m4399.recharge.R$* {*;}
```
# 接入流程
## 初始化
初始化推荐在游戏初始化过程中进行，析构函数则在游戏退出前执行。
```java
mOpeCenter = OperateCenter.getInstance();
mOpeConfig = new OperateCenterConfig.Builder(this)
	.setGameKey("GAME_KEY")     //设置GameKey
	.setDebugEnabled(false)     //设置DEBUG模式,用于接入过程中开关日志输出，发布前必须设置为false或删除该行。默认为false。
	.setTestRecharge(false)	    // 充值测试模式开关控制
	.setOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)  //设置横竖屏方向，默认为横屏，现支持横竖屏，和180度旋转
	.setSupportExcess(true)     //设置服务端是否支持处理超出部分金额，默认为false
	.setPopLogoStyle(PopLogoStyle.POPLOGOSTYLE_ONE) //设置悬浮窗样式，现有四种可选
	.setsetPopWinPosition(PopWinPosition.POS_LEFT)	//设置悬浮窗默认显示位置，现有四种可选
	.build();
mOpeCenter.setConfig(mOpeConfig);
mOpeCenter.init(new OperateCenter.OnInitGloabListener() {
	//初始化完成
	@Override
	public void onInitFinished(boolean isLogin, User userInfo)
	{
		//初始化完成后操作（例如检查当前登录状态）
	}

   //用户通过悬浮窗-个人中心-注销成功时SDK调用该回调
	@Override
	public void onUserAccountLogout()
	{
		//游戏注销逻辑
	}
});
```
`充值测试模式`
```
在充值测试模式下，充值时，SDK只用一些固定参数调用下单接口，让服务器去模拟整个充值和回调过程，
最后服务器将充值结果返回给SDK显示，以测试游戏充值配置是否正确。
完成测试后，要关闭此开关，再进行正式的各个渠道的测试。
```

`是否支持处理超出部分金额`也可单独设置
```java
mOpeCenter.setSupportExcess(support);
```
`能否支持处理超出部分金额`指在使用SDK充值时，由于用户选择的充值渠道不同，可能造成实际充值金额超出游戏下单时传入的金额。如果游戏服务端能够正确处理超出部分的金额，则本接口传入true。如果无法支持处理超出部分的金额，则传入false，SDK将会根据传入金额自动隐藏无法满足充值金额的渠道（例：开发者设置SupportExcess为false，充值时传入7元，此时4399一卡通中无7元面额的充值卡，此时4399一卡通的充值渠道将自动隐藏）。*SupportExcess*默认为false。

*注：代码中`MainActivity`为当前Activity.下文的`mOpeCenter`指`OperateCenter`实例，通过`getInstance()`静态方法获得。*  

* 悬浮窗  
悬浮窗会在用户登录后显示在游戏最前端，用户可通过悬浮窗进入个人中心和游戏圈。个人中心包含切换用户、修改密码、绑定手机、注销、游币余额显示功能。游戏圈依赖1.4.1以上版本的4399游戏盒，当用户有安装4399游戏盒时，会自动弹出本游戏的游戏圈页面。  

* 悬浮窗类型  
游戏开发者可根据游戏的风格自由选择合适的悬浮窗样式，在配置SDK时设置。 

|样式类型|图示|
|--------|----|
|PopLogoStyle.POPLOGOSTYLE_ONE|<img src="/Client_SDK/m4399RechargeSDK/res/drawable-hdpi/m4399_ope_pop_logo_one_normal.png" alt="POPLOGOSTYLE_ONE" />|
|PopLogoStyle.POPLOGOSTYLE_TWO|<img src="/Client_SDK/m4399RechargeSDK/res/drawable-hdpi/m4399_ope_pop_logo_two_normal.png" alt="POPLOGOSTYLE_TWO" />|
|PopLogoStyle.POPLOGOSTYLE_THREE|<img src="/Client_SDK/m4399RechargeSDK/res/drawable-hdpi/m4399_ope_pop_logo_three_normal.png" alt="POPLOGOSTYLE_THREE" />|
|PopLogoStyle.POPLOGOSTYLE_FOUR|<img src="/Client_SDK/m4399RechargeSDK/res/drawable-hdpi/m4399_ope_pop_logo_four_normal.png" alt="POPLOGOSTYLE_FOUR" />|

* 悬浮窗默认位置
游戏开发者可根据游戏的风格自由选择合适的悬浮窗初始化的默认位置，在配置SDK时设置。 
默认有四种位置 ：

|样式类型|位置|
|--------|----|
|PopWinPosition.POS_LEFT（默认位置）| 屏幕左侧|
|PopWinPosition.POS_RIGHT|屏幕右侧|
|PopWinPosition.POS_TOP|屏幕上侧|
|PopWinPosition.POS_BOTTOM|屏幕下侧|



## 设置切换用户监听器
用户在个人中心中成功切换账户后，SDK将检测游戏方是否有设置切换用户监听器。如果有，SDK建在切换成功后自动执行游戏方提供的逻辑，如果没有，SDK将强制应用重新启动，以达到切换账号的效果。建议游戏接入本接口以提升用户体验。
```java
mOpeCenter.setSwitchUserAccountListener(new OnSwitchUserAccountListener()
{
    public void onSwitchUserAccountFinished(User userInfo){
        //游戏切换账号逻辑
    }
});
```

## 用户登录
用户在触发登录时，调用该接口，如果SDK内已包含未注销的用户凭证，将自动返回用户信息。如需强制调出登录界面，请使用【用户切换】接口。
```java
mOpeCenter.login(MainActivity.this, new OnLoginFinishedListener() {

	@Override
	public void onLoginFinished(boolean success, int resultCode, User userInfo)
	{
	    //登录结束后的游戏逻辑
	}
});
```
SDK会自动识别用户手机中是否安装了新版的4399游戏盒1.4.1以上版本，如果已安装，自动跳转至游戏盒授权登录。如果未安装，则弹出Web版4399统一登录界面。
在登录成功后，监听器返回的`User`类型的用户信息中将包含`State`登录凭证，该信息可用于游戏服务端进行[用户信息二次验证](/Document/ServerDocument.md#%E7%99%BB%E5%BD%95%E5%87%AD%E8%AF%81%E9%AA%8C%E8%AF%81%E6%8E%A5%E5%8F%A3)

*注：登录后如果未注销，登录状态将一直保持直至登录凭证过期或失效（若用户修改平台账户密码，所有游戏授权凭证将失效，需重新登录）。建议游戏在初始化完成后调用[登录状态查询](#登录状态查询)接口查询用户当前登录状态。*

## 获取当前登录用户信息
在SDK处于登录状态时，可通过该接口获取当前用户的信息（`UID`、`用户名`、`昵称`、`登录凭证`）。
```java
User user = mOpeCenter.getCurrentAccount();
```

## 用户切换
当用户需要注销当前登录状态，且同时弹出登录界面时，使用本接口。本接口的监听器类型与【用户登录】接口相同。
```java
mOpeCenter.switchAccount(MainActivity.this, new OnLoginFinishedListener() {

	@Override
	public void onLoginFinished(boolean success, int resultCode, User userInfo)
	{
	    //用户账号切换结束后的游戏逻辑
	}
});
```

## 用户注销
当用户需要注销当前登录状态时，使用本接口。
```java
mOpeCenter.logout(new OnLogoutFinishedListener() {
	@Override
	public void onLogoutFinished(boolean success, int resultCode)
	{
        	//用户注销后的游戏逻辑
	}
});
```
## 游戏关闭
```java
// 如果游戏已经配置游戏圈， 则在关闭前，SDK会弹出对话框询问“退出游戏”还是“前往游戏圈”
// 若没有则不会弹框，但依然会调用onQuitGame方法
mOpeCenter.shouldQuitGame(MainActivity.this, new OnQuitGameListener() {

	@Override
	public void onQuitGame(boolean shouldQuit) {
		// 如果游戏还没有配置游戏圈，shouldQuit会直接返回true
		
		// 点击“退出游戏”时，shouldQuit为true，游戏处理自己的退出业务逻辑
		// 点击“前往游戏圈”时，shouldQuit为false，SDK会进入游戏圈或者下载
		// 	游戏盒子界面，游戏可以不做处理。
	}
});
```

## 登录状态查询
查询当前客户端是否有账号登录
```java
boolean isLogin = mOpeCenter.isLogin();
```
## 获取缓存用户名列表
当使用WEB版4399统一登录时，系统会记忆最多5次登录的用户名,用于下一次在登录界面提供用户候选。
```java
String[] accounts = mOpeCenter.getCacheAccounts();
```

## 删除缓存用户名
当需要充缓存中删除已登录用户信息时，可调用该接口，删除历史登录用户信息。（最后一次登录用户无法删除）
```java
mOpeCenter.removeCacheAccount("USER_NAME");
```

## 设置用户所在服务器ID
当游戏有分服时，在用户选择角色进入分服时，请务必立即通过本接口设置所在服的ID。如果无分服，则可不设置。
```java
mOpeCenter.setServer("SERVER_ID");
```
## 检查更新
SDK将检查后台是否有新版本游戏上线，如果有，则显示更新内容，并提示用户升级。该升级为`增量升级`，后台在提交新版游戏时自动制作差分包，更新时用户只需下载APK文件中新旧版本有差异的部分。相关更新内容和版本提交事宜，请联系4399相关运营对接人员。  
4399SDK的增量升级分为  
- 全自动增量更新（无需操作，默认初始化完成）
- 自定义界面增量更新 （当游戏选择使用该策略时，上线前需与4399运营人员沟通）

自定义界面增量更新接入方法详见：[4399运营SDK增量升级说明](DeltaUpdate.md)

## 充值
当用户需要充值时，可调用本接口启动充值中心界面。  
```java
mOpeCenter.recharge(MainActivity.this,
	je,     	//整型，充值金额（元）
	mark,   	//游戏方订单号
	productName,    //商品名称
	new OnRechargeFinishedListener() {
		@Override
		public void onRechargeFinished(boolean success, int resultCode, String msg) {
			if (success) {
				//请求游戏服，获取充值结果
			}else{
				//充值失败逻辑
			}
		}
});
```
* `je`充值金额：整型数字，4399充值中心仅支持整数金额充值，最小充值金额`1`元，最大不超过`50000`元。
* `mark`订单号：最大长度32位，支持大小写字母、数字、‘|’(竖线)、‘-’（中划线）、‘_’（下划线），该字段不可为空，不可重复。
* `productName`商品名称：最长不超过8个字符。 如果传入商品名，充值中心将直接显示改商品名称，如果充值金额大于下单时传入的`je`时，将显示商品名+XXX游戏币，相关游戏币的兑换比例在接入时提供给运营人员配置。如果未传入商品名，则直接显示XXX游戏币。

## 获取SDK版本号
```java
mOpeCenter.getVersion();
```

## 获取状态信息
工具接口，用于将回调函数中的`resultCode`解析为中文的说明(充值接口recharge的resultCode对应的中文是回调中的msg)。
```java
String resultMessage = OperateCenter.getResultMsg(resultCode);
```

## 析构
游戏退出时调用本接口，释放SDK资源以及保存相关数据。
```java
mOpeCenter.destroy();
```
