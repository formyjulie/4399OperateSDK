package cn.m4399.game;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import cn.m4399.operate.OperateCenter;
import cn.m4399.operate.OperateCenter.OnCheckFinishedListener;
import cn.m4399.operate.OperateCenter.OnLoginFinishedListener;
import cn.m4399.operate.OperateCenter.OnLogoutFinishedListener;
import cn.m4399.operate.OperateCenter.OnRechargeFinishedListener;
import cn.m4399.operate.OperateCenter.OnSwitchUserAccountListener;
import cn.m4399.operate.OperateCenter.OnUpdateListener;
import cn.m4399.operate.OperateCenterConfig;
import cn.m4399.operate.User;
import cn.m4399.operate.model.ApkCheckResult;
import cn.m4399.operate.model.GameUpgradeInfo;

public class MainActivity extends Activity {
	public static final String TAG = "4399SDK-GameActivity";
	public static final int LANDSCAPE = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE; // 0
	public static final int PORTRAIT = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT; // 1

	String toastPre = "【DEMO】";

	OperateCenter mOpeCenter;
	OperateCenterConfig mOpeConfig;
	TextView txtVersion = null;
	SharedPreferences mSp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		mSp = getSharedPreferences("sdk_sp", 0);
		int orientation = getOriPreference();
		setRequestedOrientation(orientation);

		setContentView(R.layout.main);

		initOrientationSetting();

		// 游戏接入SDK；
		mOpeCenter = OperateCenter.getInstance();
		// 配置sdk属性,比如可扩展横竖屏配置
		mOpeConfig = new OperateCenterConfig.Builder(this).setDebugEnabled(true).setOrientation(orientation).setSupportExcess(true).setGameKey("40027")
				.setGameName("测试游戏").build();
		mOpeCenter.setConfig(mOpeConfig);
		mOpeCenter.init(MainActivity.this, new OperateCenter.OnInitGloabListener() {

			@Override
			public void onInitFinished(boolean isLogin, User userInfo) {
				Log.d(TAG, "Ope inited: " + userInfo.toString());
				Toast.makeText(MainActivity.this,
						toastPre + (isLogin ? getString(R.string.logging) : getString(R.string.not_logging) + ": " + userInfo.toString()), Toast.LENGTH_SHORT)
						.show();
			}

			@Override
			public void onUserAccountLogout() {

			}
		});

		mOpeCenter.setSwitchUserAccountListener(new OnSwitchUserAccountListener() {
			@Override
			public void onSwitchUserAccountFinished(User userInfo) {
				Log.d(TAG, "Switch Account: " + userInfo.toString());
				Toast.makeText(MainActivity.this, toastPre + userInfo, Toast.LENGTH_SHORT).show();
			}

		});

		txtVersion = (TextView) findViewById(R.id.text_version);
		txtVersion.setText("运营SDK v" + mOpeCenter.getVersion());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		Log.d(TAG, "onDestroy...");
		mOpeCenter.destroy();
		mOpeCenter = null;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Log.d(TAG, "onBackPressed...");
	}

	// //////////////////////////////////////////////////////////////
	public void initOrientationSetting() {
		CheckBox oriCheckBox = (CheckBox) findViewById(R.id.ori_checkbox);

		if (getOriPreference() == LANDSCAPE)
			oriCheckBox.setChecked(true);
		else
			oriCheckBox.setChecked(false);

		if (oriCheckBox != null) {
			oriCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					int orientation = isChecked ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

					saveOriPreference(orientation);
				}
			});
		}
	}

	private int getOriPreference() {
		int orientation = mSp.getInt("orientation", ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		return orientation;
	}

	private void saveOriPreference(int orientation) {
		if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT || orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
			mSp.edit().putInt("orientation", orientation).commit();
	}

	// ////////////////////////////////////////////////////////////////////////

	public void onLoginButtonClicked(View view) {
		mOpeCenter.login(MainActivity.this, new OnLoginFinishedListener() {

			@Override
			public void onLoginFinished(boolean success, int resultCode, User userInfo) {
				Log.d(TAG, "Login: " + success + ", " + resultCode + ": " + userInfo.toString());
				String messageString = OperateCenter.getResultMsg(resultCode) + ": " + userInfo;
				Toast.makeText(MainActivity.this, toastPre + messageString, Toast.LENGTH_SHORT).show();
			}

		});
		Log.d(TAG, "isLogin: " + mOpeCenter.isLogin());
	}

	public void onSwitchAccountButtonClicked(View view) {
		mOpeCenter.switchAccount(MainActivity.this, new OnLoginFinishedListener() {

			@Override
			public void onLoginFinished(boolean success, int resultCode, User userInfo) {
				Log.d(TAG, "Login: " + success + ", " + resultCode + ": " + userInfo.toString());
				String messageString = OperateCenter.getResultMsg(resultCode) + ": " + userInfo;
				Toast.makeText(MainActivity.this, toastPre + messageString, Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void onIsLoginButtonClicked(View view) {
		boolean isLogin = mOpeCenter.isLogin();
		String login = isLogin ? getString(R.string.logging) : getString(R.string.not_logging);
		Toast.makeText(MainActivity.this, toastPre + login, Toast.LENGTH_SHORT).show();
	}

	public void onLogoutButtonClicked(View view) {
		Log.d(TAG, "Logout Button Clicked...");

		mOpeCenter.logout(new OnLogoutFinishedListener() {
			@Override
			public void onLogoutFinished(boolean success, int resultCode) {
				Log.d(TAG, "Logout: [" + success + ", " + resultCode + "]");
				Toast.makeText(MainActivity.this, toastPre + OperateCenter.getResultMsg(resultCode), Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void onServerButtonClicked(View view) {
		Log.d(TAG, "Server Button Clicked...");
		String servers[][] = new String[][] { { "Server 1", "Server 2", "Server 3" }, { "Server 4", "Server 5", "Server 6" },
				{ "Server 7", "Server 8", "Server 9" } };
		TableLayout serverTable = new TableLayout(MainActivity.this);
		TableLayout.LayoutParams layoutParam = new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		serverTable.setLayoutParams(layoutParam);
		serverTable.setColumnStretchable(3, true);
		for (int i = 0; i < servers.length; i++) {
			final int a = i;
			TableRow tr = new TableRow(MainActivity.this);

			for (int j = 0; j < servers[i].length; j++) {
				final int b = j;
				Button button = new Button(MainActivity.this);
				button.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						mOpeCenter.setServer(String.valueOf(a * 3 + b + 1));
						Toast.makeText(MainActivity.this, toastPre + "Select Server " + (a * 3 + b + 1), Toast.LENGTH_SHORT).show();
					}
				});
				button.setText(servers[i][j]);
				tr.addView(button, new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
			}

			serverTable.addView(tr);
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("Select server").setView(serverTable).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).create().show();
	}

	public void onUpdateButtonClicked(View view) {
		Log.d(TAG, "Update Button Clicked...");

		mOpeCenter.checkUpdateApk(MainActivity.this, new OnCheckFinishedListener() {

			@Override
			public void onCheckResponse(ApkCheckResult checkResultInfo, String newApkFilePath) {
				Log.d(TAG, checkResultInfo + ": " + newApkFilePath);
				showCheckResult(checkResultInfo);
			}

		});
	}

	public void onUserButtonClicked(View view) {
		Log.d(TAG, "Get User info Button Clicked...");

		User user = mOpeCenter.getCurrentAccount();
		Log.d(TAG, user.toString());
		Toast.makeText(MainActivity.this, toastPre + user.toString(), Toast.LENGTH_SHORT).show();
	}

	public void onRechargeButtonClicked(View view) {
		Log.d(TAG, "Pay Button Clicked...");

		LayoutInflater inflator = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		final ScrollView sv = (ScrollView) inflator.inflate(R.layout.params_scrollview, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

		builder.setTitle(R.string.start_pay).setView(sv).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				TextView jeTV = (TextView) sv.findViewById(R.id.je);
				String je = jeTV.getText().toString();

				String mark = "20140417150717" + System.currentTimeMillis();
				if (mark.length() > 22)
					mark = mark.substring(0, 22);

				TextView subjectTV = (TextView) sv.findViewById(R.id.subject);
				String productName = subjectTV.getText().toString();

				CheckBox chCB = (CheckBox) sv.findViewById(R.id.changable);
				boolean supportExcess = chCB.isChecked();
				mOpeCenter.setSupportExcess(supportExcess);

				CheckBox hsCB = (CheckBox) (CheckBox) sv.findViewById(R.id.have_subject);
				boolean hasSubject = hsCB.isChecked();
				if (!hasSubject) {
					productName = null;
				}

				Log.d(TAG, "[" + je + ", " + mark + ", " + productName + ", " + supportExcess + "]");
				mOpeCenter.recharge(MainActivity.this, je, mark, productName, new OnRechargeFinishedListener() {

					@Override
					public void onRechargeFinished(boolean success, int resultCode, String msg) {
						Log.d(TAG, "Pay: [" + success + ", " + resultCode + ", " + msg + "]");
						Toast.makeText(MainActivity.this, toastPre + resultCode + ": " + msg, Toast.LENGTH_SHORT).show();
					}
				});
				dialog.dismiss();
			}
		}).create().show();
	}

	public void onRemoveAccountButtonClicked(View view) {
		LayoutInflater inflator = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		final LinearLayout layout = (LinearLayout) inflator.inflate(R.layout.remove_account_dialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle(R.string.delete_account).setView(layout).setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				EditText editText = (EditText) layout.findViewById(R.id.account_edittext);
				Log.d("MainActivityFTNN", editText.getText().toString());
				boolean result = mOpeCenter.removeCacheAccount(editText.getText().toString());
				String res = result ? getString(R.string.success) : getString(R.string.fail);
				Toast.makeText(MainActivity.this, toastPre + "delete account: " + editText.getText().toString() + res, Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		}).create().show();
	}

	private void showCheckResult(ApkCheckResult result) {
		int code = result.getCode();
		final GameUpgradeInfo updateInfo = result.getNewApkInfo();
		StringBuilder msgBuilder = new StringBuilder();

		if (code == 0x00) {
			msgBuilder.append("\n检查结果：" + "已经是最新版本");
		} else if (code == 0x01) {
			if (updateInfo != null)
				msgBuilder.append("\n检查结果：").append("\n版本号: ").append(updateInfo.getVersion()).append("\n时间: ").append(updateInfo.getDate())
						.append("\n是否强制更新：").append(updateInfo.isCompel()).append("\n补丁/整包大小：")
						.append(updateInfo.getPatchSize() + "/" + updateInfo.getGameSizeByte()).append("\n更新内容：").append(updateInfo.getUpdateMsg());
			else
				msgBuilder.append("\ncode: ").append(code).append("\n检查结果: ").append("(更新结果)");
		} else {
			msgBuilder.append("\n检查结果：" + "检查更新失败");
		}

		String action = updateInfo.isHaveDownApk() ? "安装更新包" : "开始更新";
		Builder builder = new Builder(MainActivity.this);
		builder.setTitle("自定义更新").setMessage(msgBuilder)
			.setPositiveButton(action, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				doUpdate(updateInfo);
			}

		}).setNegativeButton("取消更新", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}

		}).create().show();

	}

	private void doUpdate(final GameUpgradeInfo info) {
		final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
		dialog.setMessage("准备下载。。。");
		dialog.show();
		
		if (info.isHaveDownApk())
			dialog.dismiss();
		mOpeCenter.doUpdate(MainActivity.this, info, new OnUpdateListener() {

			@Override
			public void onUpdateSuccess(File newApkFile) {
				Log.d(TAG, "onUpdateSuccess");
				dialog.setMessage("下载成功...");
				dialog.dismiss();
			}

			@Override
			public void onUpdateFail(int resultCode) {
				Log.d(TAG, "onUpdateFail");
				dialog.setMessage("下载失败...");
				dialog.dismiss();
			}

			@Override
			public void onUpdateStart() {
				Log.d(TAG, "onUpdateStart");
				dialog.setMessage("开始更新...");
			}

			@Override
			public void onUpdateProgress(long progress, long max) {
				long percentage = progress*100/max;
				dialog.setMessage("正在更新, 更新进度：" + percentage + "%");
			}
		});
	}
}