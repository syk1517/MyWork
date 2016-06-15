package com.ksoft.offlinesdk.manager.channel;

import com.ksoft.offlinesdk.bean.PayItems;
import com.ksoft.offlinesdk.callback.OfflineCallback;
import com.ksoft.offlinesdk.manager.PaymentManager;
import com.ksoft.offlinesdk.manager.ThirdPayManager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

/**
 * 渠道第三方支付的管理类
 * 
 * @author new
 * 
 */
public class ChannelThirdPayManager extends ThirdPayManager {

	private ThirdPayManager payInterFace;

	public ChannelThirdPayManager(Context context) {
		getThirdPayManager(context);
	}

	public ThirdPayManager getPayInterFace() {
		return payInterFace;
	}

	public ThirdPayManager getThirdPayManager(Context context) {
		// TODO Auto-generated method stub
		if (payInterFace == null) {
			String className = PaymentManager.getPayment().getThirdClass();
			if(className == null){
				return null;
			}
			try {
				payInterFace = (ThirdPayManager) Class.forName(className)
						.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return payInterFace;
	}

	@Override
	public void launch(Application context) {
		if (payInterFace != null) {
			payInterFace.launch(context);
		}
	}

	@Override
	public void init(Activity activity) {
		if (payInterFace != null) {
			payInterFace.init(activity);
		}
	}

	@Override
	public void pay(final Activity activity, final PayItems payItems,
			final String orderNumber) {
		if (payInterFace != null) {
			payInterFace.pay(activity, payItems, orderNumber);
		}

	}

	@Override
	public void exit(Activity context) {
		if (payInterFace != null) {
			payInterFace.exit(context);
		} else {
			super.exit(context);
		}
	}

	@Override
	public int getPayIconByType(Context context, String payType) {
		if (payInterFace != null) {
			return payInterFace.getPayIconByType(context, payType);
		} else {
			return 0;
		}
	}

}
