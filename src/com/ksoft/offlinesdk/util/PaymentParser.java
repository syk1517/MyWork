package com.ksoft.offlinesdk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.ksoft.offlinesdk.bean.PayItems;
import com.ksoft.offlinesdk.bean.Payment;
import com.ksoft.offlinesdk.bean.Paytype;
import com.thoughtworks.xstream.XStream;

public class PaymentParser {
	
	public static Payment getPaymentFromXML(Context context){
		//1,解密assets中的payment.ini
		AssetManager assetManager = context.getAssets();
		String result = "";
		String xmlContent = "";
		try {
			String encoding = "UTF-8";
			InputStreamReader read = new InputStreamReader(
					assetManager.open("Payment.ini"), encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTXT = null;
			while ((lineTXT = bufferedReader.readLine()) != null) {
				result = result + lineTXT;
			}
			read.close();
			bufferedReader.close();
			xmlContent = DesUtils.decrypt(result);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//2，把xml转化为对象
		XStream xStream = new XStream();
		xStream.autodetectAnnotations(true);
	    xStream.alias("payment", Payment.class);
	    xStream.alias("paytype", Paytype.class);
	    xStream.alias("paygood", PayItems.class);
	    xStream.addImplicitCollection(Payment.class, "payTypes");//隐式集合 隐藏contentslist
	    xStream.addImplicitCollection(Paytype.class, "payItems");
	    try {
//			Object obj = xStream.fromXML(context.getAssets().open(
//			        "Payment.xml"));
	    	Object obj = xStream.fromXML(xmlContent);
			return (Payment) obj;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
		
}
