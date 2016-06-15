package com.ksoft.offlinesdk.util;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * ICCID获取和解析
 * <p>
 * Requires Permission: {@link android.Manifest.permission#READ_PHONE_STATE
 * READ_PHONE_STATE}
 * 
 * @see {@link android.telephony.TelephonyManager}
 * 
 */
public class ICCID {

	public static final String MII = "89";
	public static final String MCC_CHINA = "86"; // China
	public static final String OPERATOR_CHINAMOBILE = "00"; // China Mobile
	public static final String OPERATOR_CHINAUNICOM = "01"; // China Unicom
	public static final String OPERATOR_CHINATELECOM = "03"; // China Telecom

	public enum OPERATOR {
		CHINAMOBILE("中国移动", "00,02"), CHINAUNICOM("中国联通", "01,06,09"), CHINATELECOM(
				"中国电信", "03");

		private final String text;
		private final String code;

		private OPERATOR(final String text, final String code) {
			this.text = text;
			this.code = code;
		}

		@Override
		public String toString() {
			return text;
		}

		public String getCode() {
			return code;
		}

		public static OPERATOR getEnum(String value) {
			if (null == value)
				throw new IllegalArgumentException();
			for (OPERATOR item : OPERATOR.values()) {
				if (item.getCode().equals(value))
					return item;
			}
			throw new IllegalArgumentException();
		}
	}

	public enum AREA {
		UNKNOWN("未知", "00"), BEIJING("北京", "01"), SHANGHAI("上海", "09"), TIANJIN(
				"天津", "02"), CHONGQING("重庆", "31"), HEBEI("河北", "03"), SHANXI(
				"山西", "04"), HENAN("河南", "16"), LIAONING("辽宁", "06"), JILIN(
				"吉林", "07"), HEILONGJIANG("黑龙江", "08"), NEIMENGGU("内蒙古", "05"), JIANGSU(
				"江苏", "10"), SHANDONG("山东", "15"), ANHUI("安徽", "12"), ZHEJIANG(
				"浙江", "11"), FUJIAN("福建", "13"), HUBEI("湖北", "17"), HUNAN("湖南",
				"18"), GUANGDONG("广东", "19"), GUANGXI("广西", "20"), JIANGXI(
				"江西", "14"), SICHUAN("四川", "22"), GUIZHOU("贵州", "23"), XIZANG(
				"西藏", "25"), HAINAN("海南", "21"), YUNNAN("云南", "24"), SHAANXI(
				"陕西", "26"), GANSU("甘肃", "27"), NINGXIA("宁夏", "29"), QINGHAI(
				"青海", "28"), XINJIANG("新疆", "30");

		private final String text;
		private final String code;

		private AREA(final String text, final String code) {
			this.text = text;
			this.code = code;
		}

		@Override
		public String toString() {
			return text;
		}

		public String getCode() {
			return code;
		}

		public static AREA getEnum(String value) {
			if (null == value)
				throw new IllegalArgumentException();
			for (AREA item : AREA.values()) {
				if (item.getCode().equals(value))
					return item;
			}
			throw new IllegalArgumentException();
		}
	}

	private Context context;

	private String iccid;

	/**
	 * Major industry identifier
	 */
	private String mii;
	/**
	 * Mobile country code
	 */
	private String mcc;
	private String operator;
	private String area_code;
	private AREA area = AREA.UNKNOWN;

	public String getIccid() {
		return iccid;
	}

	public String getOperator() {
		if(operator == null){
			return null;
		}
		if (OPERATOR.CHINAMOBILE.getCode().contains(operator))
			return OPERATOR_CHINAMOBILE;
		else if (OPERATOR.CHINAUNICOM.getCode().contains(operator))
			return OPERATOR_CHINAUNICOM;
		else if (OPERATOR.CHINATELECOM.getCode().contains(operator))
			return OPERATOR_CHINATELECOM;
		return null;
	}

	public String getArea() {
		return area.getCode();
	}

	public ICCID(Context context) {
		this.context = context;
		parseICCID();
	}

	private void parseICCID() {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		iccid = tm.getSimSerialNumber();
		LogUtil.infoLog("iccid is:"+iccid);
		if (null == iccid || iccid.length() < 6)
			return;

		mii = iccid.substring(0, 2);
		if (!MII.equals(mii))
			return;
		mcc = iccid.substring(2, 4);
		if (!MCC_CHINA.equals(mcc))
			return;
		operator = iccid.substring(4, 6);
		if (OPERATOR_CHINAMOBILE.equals(operator) && iccid.length() >= 10) {
			area_code = iccid.substring(8, 10);
			area = transArea_ChinaMobile(area_code);
		} else if (OPERATOR_CHINAUNICOM.equals(operator) && iccid.length() >= 11) {
			area_code = iccid.substring(9, 11);
			area = transArea_ChinaUnicom(area_code);
		} else if (OPERATOR_CHINATELECOM.equals(operator) && iccid.length() >= 13) {
			area_code = iccid.substring(10, 13);
			area = transArea_ChinaTelecom(area_code);
		} else {
			return;
		}
	}

	private AREA transArea_ChinaMobile(String area_code) {
		int nArea = Integer.parseInt(area_code);
		switch (nArea) {
		case 1:
			return AREA.BEIJING;
		case 2:
			return AREA.TIANJIN;
		case 3:
			return AREA.HEBEI;
		case 4:
			return AREA.SHANXI;
		case 5:
			return AREA.NEIMENGGU;
		case 6:
			return AREA.LIAONING;
		case 7:
			return AREA.JILIN;
		case 8:
			return AREA.HEILONGJIANG;
		case 9:
			return AREA.SHANGHAI;
		case 10:
			return AREA.JIANGSU;
		case 11:
			return AREA.ZHEJIANG;
		case 12:
			return AREA.ANHUI;
		case 13:
			return AREA.FUJIAN;
		case 14:
			return AREA.JIANGXI;
		case 15:
			return AREA.SHANDONG;
		case 16:
			return AREA.HENAN;
		case 17:
			return AREA.HUBEI;
		case 18:
			return AREA.HUNAN;
		case 19:
			return AREA.GUANGDONG;
		case 20:
			return AREA.GUANGXI;
		case 21:
			return AREA.HAINAN;
		case 22:
			return AREA.SICHUAN;
		case 23:
			return AREA.GUIZHOU;
		case 24:
			return AREA.YUNNAN;
		case 25:
			return AREA.XIZANG;
		case 26:
			return AREA.SHAANXI;
		case 27:
			return AREA.GANSU;
		case 28:
			return AREA.QINGHAI;
		case 29:
			return AREA.NINGXIA;
		case 30:
			return AREA.XINJIANG;
		case 31:
			return AREA.CHONGQING;
		}
		return AREA.UNKNOWN;
	}

	private AREA transArea_ChinaUnicom(String area_code) {
		int nArea = Integer.parseInt(area_code);
		switch (nArea) {
		case 10:
			return AREA.NEIMENGGU;
		case 11:
			return AREA.BEIJING;
		case 13:
			return AREA.TIANJIN;
		case 17:
			return AREA.SHANDONG;
		case 18:
			return AREA.HEBEI;
		case 19:
			return AREA.SHANXI;
		case 30:
			return AREA.ANHUI;
		case 31:
			return AREA.SHANGHAI;
		case 34:
			return AREA.JIANGSU;
		case 36:
			return AREA.ZHEJIANG;
		case 38:
			return AREA.FUJIAN;
		case 50:
			return AREA.HAINAN;
		case 51:
			return AREA.GUANGDONG;
		case 59:
			return AREA.GUANGXI;
		case 70:
			return AREA.QINGHAI;
		case 71:
			return AREA.HUBEI;
		case 74:
			return AREA.HUNAN;
		case 75:
			return AREA.JIANGXI;
		case 76:
			return AREA.HENAN;
		case 79:
			return AREA.XIZANG;
		case 81:
			return AREA.SICHUAN;
		case 83:
			return AREA.CHONGQING;
		case 84:
			return AREA.SHAANXI;
		case 85:
			return AREA.GUIZHOU;
		case 86:
			return AREA.YUNNAN;
		case 87:
			return AREA.GANSU;
		case 88:
			return AREA.NINGXIA;
		case 89:
			return AREA.XINJIANG;
		case 90:
			return AREA.JILIN;
		case 91:
			return AREA.LIAONING;
		case 97:
			return AREA.HEILONGJIANG;
		}
		return AREA.UNKNOWN;
	}

	private AREA transArea_ChinaTelecom(String area_code) {
		int nArea = Integer.parseInt(area_code);
		if (nArea == 10) {
			return AREA.BEIJING;
		} else if (nArea == 21) {
			return AREA.SHANGHAI;
		} else if (nArea == 22) {
			return AREA.TIANJIN;
		} else if (nArea == 23) {
			return AREA.CHONGQING;
		} else if (nArea >= 310 && nArea <= 339) {
			return AREA.HEBEI;
		} else if (nArea >= 340 && nArea <= 359) {
			return AREA.SHANXI;
		} else if (nArea >= 370 && nArea <= 399) {
			return AREA.HENAN;
		} else if ((nArea == 24) || (nArea >= 410 && nArea <= 429)) {
			return AREA.LIAONING;
		} else if (nArea >= 430 && nArea <= 449) {
			return AREA.JILIN;
		} else if (nArea >= 450 && nArea <= 469) {
			return AREA.HEILONGJIANG;
		} else if (nArea >= 470 && nArea <= 489) {
			return AREA.NEIMENGGU;
		} else if ((nArea == 25) || (nArea >= 510 && nArea <= 529)) {
			return AREA.JIANGSU;
		} else if ((nArea >= 530 && nArea <= 549)
				|| (nArea >= 630 && nArea <= 639)) {
			return AREA.SHANDONG;
		} else if (nArea >= 550 && nArea <= 569) {
			return AREA.ANHUI;
		} else if (nArea >= 570 && nArea <= 589) {
			return AREA.ZHEJIANG;
		} else if (nArea >= 590 && nArea <= 599) {
			return AREA.FUJIAN;
		} else if ((nArea == 20) || (nArea >= 660 && nArea <= 669)
				|| (nArea >= 750 && nArea <= 769)) {
			return AREA.GUANGDONG;
		} else if (nArea >= 690 && nArea <= 699) {
			return AREA.YUNNAN;
		} else if ((nArea == 27) || (nArea >= 710 && nArea <= 729)) {
			return AREA.HUBEI;
		} else if (nArea >= 730 && nArea <= 749) {
			return AREA.HUNAN;
		} else if (nArea >= 770 && nArea <= 779) {
			return AREA.GUANGXI;
		} else if ((nArea >= 700 && nArea <= 709)
				|| (nArea >= 790 && nArea <= 799)) {
			return AREA.JIANGXI;
		} else if ((nArea == 28) || (nArea >= 810 && nArea <= 839)) {
			return AREA.SICHUAN;
		} else if (nArea >= 850 && nArea <= 859) {
			return AREA.GUIZHOU;
		} else if (nArea >= 870 && nArea <= 889) {
			return AREA.YUNNAN;
		} else if (nArea >= 890 && nArea <= 899) {
			return AREA.XIZANG;
		} else if ((nArea == 29) || (nArea >= 910 && nArea <= 919)) {
			return AREA.SHAANXI;
		} else if (nArea >= 930 && nArea <= 949) {
			return AREA.GANSU;
		} else if (nArea >= 950 && nArea <= 959) {
			return AREA.NINGXIA;
		} else if (nArea >= 970 && nArea <= 979) {
			return AREA.QINGHAI;
		} else if ((nArea >= 900 && nArea <= 909)
				|| (nArea >= 990 && nArea <= 999)) {
			return AREA.XINJIANG;
		}
		return AREA.UNKNOWN;
	}

}
