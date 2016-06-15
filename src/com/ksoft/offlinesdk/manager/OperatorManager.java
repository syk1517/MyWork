package com.ksoft.offlinesdk.manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import com.ksoft.offlinesdk.bean.PayItems;

public abstract class OperatorManager {
	public void launch(Application context) {
	}
	
	public void attachBaseContext(Application app,Context context) {
	}

	public abstract void init(Activity paramActivity);

	public abstract void pay(Activity paramActivity, PayItems paramPayItems,
			String paramString);

	public void exit(Activity context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage("确认退出吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				CallBackManager.onCallBack(4, 5000, null, null);
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				CallBackManager.onCallBack(4, 5001, null, null);
			}
		});
		builder.create().show();
	}

	public void onResume(Activity activity) {
	}

	public void onPause(Activity activity) {
	}

	public void onStop(Activity activity) {
	}

	public void onDestroy(Activity activity) {
	}

	public void onRestart(Activity activity) {
	}

	public void onStart(Activity activity) {
	}

	public void onCreate(Activity activity) {
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	}

	public void onNewIntent(Intent intent) {
	}
}