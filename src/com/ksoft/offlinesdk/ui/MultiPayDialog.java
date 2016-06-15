package com.ksoft.offlinesdk.ui;

import java.util.ArrayList;
import java.util.List;

import com.ksoft.offlinesdk.bean.EventCode;
import com.ksoft.offlinesdk.bean.ItemPayInfo;
import com.ksoft.offlinesdk.bean.ReturnCode;
import com.ksoft.offlinesdk.manager.CallBackManager;
import com.ksoft.offlinesdk.manager.PaymentManager;
import com.ksoft.offlinesdk.util.DeviceUtil;
import com.ksoft.offlinesdk.util.LogUtil;
import com.ksoft.offlinesdk.util.Resource;
import com.ksoft.offlinesdk.util.StringUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * 支付方式选择对话框
 * 
 * @author new
 * 
 */
public class MultiPayDialog {
	private static MultiPayDialog multiPayDialog;
	private Activity context;
	private View dialogView, payView;
	private Dialog dialog;

	public void dismissDialog() {
		if (dialog != null && dialog.isShowing()) {
			try {
				dialog.dismiss();
			} catch (Exception e) {
				System.out.println("dialog.dismiss:" + e.getMessage());
			}

		}
	}

	public MultiPayDialog(Activity context) {
		super();
		this.context = context;
	}

	/**
	 * 
	 */
	private String orderNumber;

	private boolean isOpened = false;

	public boolean isOpened() {
		return isOpened;
	}

	public void setOpened(boolean isOpened) {
		this.isOpened = isOpened;
	}

	public static MultiPayDialog getMultiPayDialog(Activity context) {
//		if (multiPayDialog == null) {
			multiPayDialog = new MultiPayDialog(context);
//		}
		return multiPayDialog;
	}

	private Dialog getDialog(Context context) {
		dialog = new Dialog(context, Resource.getStyle(context, "MyDialog"));
		return dialog;
	}

	public void doPayDialog(String orderNumber, List<ItemPayInfo> payList) {
		LogUtil.infoLog("打开支付界面");
		isOpened = true;
		this.orderNumber = orderNumber;
		LayoutInflater inflater = LayoutInflater.from(context);
		dialog = getDialog(context);
		dialogView = DeviceUtil.setSDKDialogScreen(
				DeviceUtil.isLandScape(context), inflater,
				Resource.getLayout(context, "ks_offline_dialog_port"),
				Resource.getLayout(context, "ks_offline_dialog"));
		payView = dialogView.findViewById(Resource.getId(context, "payView"));
		selectShowView();
		dialog.setContentView(dialogView);
		dialog.show();
		dialog.setCancelable(false);
		setAppName();
		setPayAmount(payList);
		setGridView(payList);
	}

	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (v.getId() == Resource.getId(context, "closeImg")) {
				dismissDialog();
				CallBackManager.onCallBack(EventCode.PAY_EVENT_CODE,
						ReturnCode.PAY_MSG_CANCEL, "支付取消", orderNumber);
				PaymentManager.setPaying(false);

			}
		}
	};

	private void selectShowView() {
		// TODO Auto-generated method stub
		payView.setVisibility(View.VISIBLE);

		dialogView.findViewById(Resource.getId(context, "closeImg"))
				.setOnClickListener(clickListener);

		TextView titleTxt = (TextView) payView.findViewById(Resource.getId(
				context, "titleTxt"));
		TextView payPromt = (TextView) payView.findViewById(Resource.getId(
				context, "payPrompt"));

		titleTxt.setText(Resource.getString(context, "payCenter"));

		// // 添加支付提示
		// if ((payHelper.getPayMethodTypeList().length) > 0) {
		// PayMethodType payTypeInfo = payHelper.getPayMethodTypeList()[0];
		// Paytype payType = payHelper.getPaytypeByPayMethodType(payTypeInfo);
		// System.out.println("payType is " + payType.getName());
		// System.out.println("payType getMessage is " + payType.getMessage());
		// if (payType.getMessage() != null && payType.getMessage() != ""
		// && payPromt != null) {
		// payPromt.setText(payType.getMessage());
		// }
		// }

	}

	private void setGridView(final List<ItemPayInfo> itemPayInfos) {
		// TODO Auto-generated method stub
		MyGridView mGridView = (MyGridView) payView.findViewById(Resource
				.getId(context, "payGridView"));
		// 给MyGridView配置适配器
		PayTypeAdapter payTypeAdapter = new PayTypeAdapter(context,
				itemPayInfos);
		mGridView.setAdapter(payTypeAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				ItemPayInfo info = itemPayInfos.get(position);
				if (info != null) {
					PaymentManager.doPayMent(orderNumber, context, info);
					dismissDialog();
				}
			}
		});
	}

	private void setAppName() {
		// TODO Auto-generated method stub
		TextView appNameTxt = (TextView) payView.findViewById(Resource.getId(
				context, "payAppName"));
		appNameTxt.setText(PaymentManager.getPayment().getApplicationName());

	}

	//
	private void setPayAmount(List<ItemPayInfo> payList) {
		// TODO Auto-generated method stub
		TextView orderAmountTxt = (TextView) payView.findViewById(Resource
				.getId(context, "orderAmount"));
		String money = getPayMoneyFromList(payList);
		orderAmountTxt.setText(Resource.getString(context, "yuan") + money);
	}

	private String getPayMoneyFromList(List<ItemPayInfo> payList) {
		for (int i = 0; i < payList.size(); i++) {
			if (payList.get(i) != null
					&& payList.get(i).getPayItems() != null
					&& StringUtil.isStrValid(payList.get(i).getPayItems()
							.getMoney()))
				return payList.get(i).getPayItems().getMoney();
		}
		return "";
	}
}
