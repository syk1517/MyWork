package com.ksoft.offlinesdk.ui;


import com.ksoft.offlinesdk.util.Resource;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

public class KsImageView extends ImageView {
	
	private Context mContext;

	public KsImageView(Context context) {
		super(context);
		this.mContext = context;
		// TODO Auto-generated constructor stub
	}

	public KsImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
	}

	public KsImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
	}



	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub

		super.onDraw(canvas);
		// 画边框
		Rect rec = canvas.getClipBounds();
		rec.bottom--;
		rec.right--;
		Paint paint = new Paint();
		paint.setColor(Resource.getColor(mContext, "image_border"));
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3);  
		canvas.drawRect(rec, paint);
	}

}
