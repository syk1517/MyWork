package com.ksoft.offlinesdk.util;

import java.io.ByteArrayInputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import com.ksoft.offlinesdk.util.ICCID.OPERATOR;


import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

public class DeviceUtil {

	public static String[] getDeviceInfo(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = mTelephonyMgr.getSubscriberId();
		// String imsi = "";
		String imei = mTelephonyMgr.getDeviceId();
		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		String macAddress = info.getMacAddress();
		return new String[] { imei, imsi, macAddress };
	}

	/**
	 * 获取sim卡状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getSimState(Context context) {
		if(context == null){
			return false;
		}
		try {
			TelephonyManager mTelephonyManager;
			mTelephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			return (mTelephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY)
					&& (mTelephonyManager.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE);
		} catch (Exception e) {
			return false;
		}
		
	}

	/**
	 * 获取手机分辨率
	 * 
	 * @return
	 */
	public static String getDisplayMetrics(Context context) {
		Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		return width + " * " + height;
	}

	/**
	 * 判断网络状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean haveInternet(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivity.getActiveNetworkInfo();
		if (info == null || !info.isConnected()) {
			return false;
		}
		return true;
	}
//
//	/**
//	 * 获取应用签名
//	 * 
//	 * @param view
//	 */
//	public static String getSignature(Context context) {
//		PackageManager manager = context.getPackageManager();
//		try {
//			/** 通过包管理器获得指定包名包含签名的包信息 **/
//			PackageInfo packInfo = context.getPackageManager().getPackageInfo(
//					context.getPackageName(), PackageManager.GET_SIGNATURES);
//			/******* 通过返回的包信息获得签名数组 *******/
//			android.content.pm.Signature[] signatures = packInfo.signatures;
//
//			// parseSignature(signatures[0].toByteArray());
//
//			/******* 循环遍历签名数组拼接应用签名 *******/
//			StringBuilder builder = new StringBuilder();
//			for (Signature signature : signatures) {
//				builder.append(signature.toCharsString());
//			}
//			/************** 得到应用签名 **************/
//			String signature = builder.toString();
//			String signMd5 = MD5.getMD5(signature);
//			// System.out.println(signMd5);
//			return signMd5;
//		} catch (NameNotFoundException e) {
//			e.printStackTrace();
//			return null;
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
//	}

	public static void parseSignature(byte[] signature) {
		try {
			CertificateFactory certFactory = CertificateFactory
					.getInstance("X.509");
			X509Certificate cert = (X509Certificate) certFactory
					.generateCertificate(new ByteArrayInputStream(signature));
			String pubKey = cert.getPublicKey().toString();
			String signNumber = cert.getSerialNumber().toString();
			System.out.println("signName:" + cert.getSigAlgName());
			System.out.println("pubKey:" + pubKey);
			System.out.println("signNumber:" + signNumber);
			System.out.println("subjectDN:" + cert.getSubjectDN().toString());
		} catch (CertificateException e) {
			e.printStackTrace();
		}
	}

	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()&& !inetAddress.isLinkLocalAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("DEVICEUTIL", ex.toString());
		}
		return null;
	}
	
	/**
	 * 获取当前进程名称
	 * @param context
	 * @return
	 */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }
    
    /**
	 * 获取运营商
	 */
	public static String getOperatorCode(Context context) {
		if (getSimState(context)) {
			TelephonyManager telManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String operator = telManager.getSimOperator();
			String code = getOperatorCode(operator);
			if (null == code || TextUtils.isEmpty(code)) {
				operator = telManager.getNetworkOperator();
			}

			Log.i("DeviceUtil", "OperatorCode:" + operator);
			return getOperatorCode(operator);
		}else{
			return null;
		}
		
	}

	/**
	 * 获取运营商编码
	 * 
	 * @param operator
	 * @return
	 */
	private static String getOperatorCode(String operator) {
		if (operator != null) {
			if (operator.equals("46000") || operator.equals("46002")
					|| operator.equals("46007")) {
				return OPERATOR.CHINAMOBILE.getCode();
				// 中国移动
			} else if (operator.equals("46001") || operator.equals("46006")
					|| operator.equals("46009")) {
				return OPERATOR.CHINAUNICOM.getCode();
				// 中国联通
			} else if (operator.equals("46003") || operator.equals("46005")) {
				return OPERATOR.CHINATELECOM.getCode();
				// 中国电信
			}
		}
		return "";
	}
	

	/**
	 * 是否横屏
	 * @param context
	 * @return
	 */
	public static boolean isLandScape(Context context){
		if(context == null){
			return true;
		}
		LogUtil.infoLog("context.getResources().getConfiguration().orientation"+context.getResources().getConfiguration().orientation);
		if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			return true;
		} else {
			return false;
		}
	}
	
	public static View setSDKDialogScreen(boolean isLandscape,
			LayoutInflater inflater, int portLayoutId, int landLayoutId) {
		// TODO Auto-generated method stub
		if (isLandscape) {
			return inflater.inflate(landLayoutId, null);
		} else {
			return inflater.inflate(portLayoutId, null);
		}

	}

}
