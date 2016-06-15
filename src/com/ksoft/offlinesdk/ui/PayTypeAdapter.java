package com.ksoft.offlinesdk.ui;

import java.util.ArrayList;
import java.util.List;

import com.ksoft.offlinesdk.bean.ItemPayInfo;
import com.ksoft.offlinesdk.bean.PayItems;
import com.ksoft.offlinesdk.manager.PaymentManager;
import com.ksoft.offlinesdk.manager.ThirdPayManager;
import com.ksoft.offlinesdk.util.DeviceUtil;
import com.ksoft.offlinesdk.util.Resource;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class PayTypeAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Context mContext;
	private List<ItemPayInfo> goodsPayList;

	public PayTypeAdapter(Context context, List<ItemPayInfo> goodsPayList) {
		mInflater = LayoutInflater.from(context);
		this.mContext = context;
		this.goodsPayList = goodsPayList;
	}

	@Override
	public int getCount() {
		return goodsPayList.size();
	}

	@Override
	public Object getItem(int position) {
		return goodsPayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			if (DeviceUtil.isLandScape(mContext)) {
				convertView = mInflater
						.inflate(Resource.getLayout(mContext,
								"ks_offline_paytype_item"), parent, false);
			} else {
				convertView = mInflater.inflate(Resource.getLayout(mContext,
						"ks_offline_paytype_item_port"), parent, false);
			}
			viewHolder = new ViewHolder();
			viewHolder.mImageView = (ImageView) convertView
					.findViewById(Resource.getId(mContext, "imageView1"));
			viewHolder.mLayout = (FrameLayout) convertView
					.findViewById(Resource.getId(mContext, "off_layout"));
			viewHolder.mOffText = (TextView) convertView.findViewById(Resource
					.getId(mContext, "off_text"));
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		ItemPayInfo payInfo = goodsPayList.get(position);
		// Paytype payType = payHelper.getPaytypeByPayMethodType(payInfo);
		// System.out.println("payType.getName is "+payType.getName());

		// String sellOff = goodsPayList.get(position).getSellOff();
		if (payInfo.getPaytype().getOperators() != null
				&& !payInfo.getPaytype().getOperators()
						.equals(PaymentManager.THIRD_CHANNEL_OP)) {
			viewHolder.mImageView.setImageResource(Resource.getDrawable(
					mContext, "ks_dxzf"));
		} else {
			int resId = PaymentManager.getChannelThirdManager(mContext)
					.getThirdPayManager(mContext)
					.getPayIconByType(mContext, payInfo.getPaytype().getId());
			if (resId != 0) {
				viewHolder.mImageView.setImageResource(resId);
			} else {
				viewHolder.mImageView.setImageResource(Resource.getDrawable(
						mContext, "ks_zxzf"));
			}

		}
		return convertView;
	}

	private void setSellOff(String sellOff, ViewHolder viewHolder) {
		if (sellOff == null) {
			System.err.println("sellOff is null");
		}
		if (sellOff != null && !sellOff.equals("100")) {
			int sellOffCount = Integer.parseInt(sellOff);
			if (sellOffCount % 10 == 0) {
				viewHolder.mOffText.setText(sellOffCount / 10 + "");
			} else {
				viewHolder.mOffText.setText(sellOff);
			}
			viewHolder.mLayout.setVisibility(View.VISIBLE);
		}
	}

	private final class ViewHolder {
		ImageView mImageView;
		FrameLayout mLayout;
		TextView mOffText;
	}

}
