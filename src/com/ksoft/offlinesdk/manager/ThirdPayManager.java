package com.ksoft.offlinesdk.manager;

import com.ksoft.offlinesdk.OfflineSDK;
import com.ksoft.offlinesdk.OfflineSdkImpl;
import com.ksoft.offlinesdk.bean.EventCode;
import com.ksoft.offlinesdk.bean.ExitResultBean;
import com.ksoft.offlinesdk.bean.ReturnCode;
import com.ksoft.offlinesdk.callback.OfflineCallback;
import com.ksoft.offlinesdk.util.Resource;
import com.ksoft.offlinesdk.util.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;

/**
 * 第三方支付管理父类
 * 
 * @author new
 * 
 */
public abstract class ThirdPayManager extends OperatorManager{

	/**
	 * 根据渠道获取支付方式图片，默认为在线支付
	 * 
	 * @param context
	 * @param payType
	 */
	public int getPayIconByType(Context context, String payType) {
		return Resource.getDrawable(context, "ks_zxzf");
	}
}
