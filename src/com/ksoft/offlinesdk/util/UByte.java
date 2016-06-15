package com.ksoft.offlinesdk.util;


public final class UByte {

	public static byte[] digitStringToBytes(String str) {
		byte[] rtn = new byte[str.length()];

		for (int i = 0; i < str.length(); i++)
			rtn[i] = (byte) (str.charAt(i));

		return rtn;
	}

	public static int digitBytesToInt(byte[] b) {
		String str = UByte.bytesToCharString(b);
		return Integer.parseInt(str);
	}

	public static byte[] intToLiteralBytes(int v, int size) {
		String str = Integer.toString(v);
		byte[] rtn = new byte[size];
		byte[] b = UByte.digitStringToBytes(str);
		if (b.length >= size)
			System.arraycopy(b, 0, rtn, 0, size);
		else
			rtn = UByte.padingLeft(b, size - b.length, (byte) 0x30);
		return rtn;
	}

	public static byte[] longToLiteralBytes(long v, int size) {
		String str = Long.toString(v);

		int strlen = str.length();
		if (strlen < 2 * size) {
			for (int i = 0; i < 16 - strlen; i++)
				str = "0" + str;
		}
		return (StringToBytes(str));
	}

	public static byte shortToByte(short v) {
		short value = (short) (v & 0xFF);

		byte baseB = 0;
		byte valueB = 0;

		if (value <= 127) {
			valueB = (byte) value;
			return valueB;
		}

		baseB = 1;
		baseB = (byte) (baseB << 7);
		valueB = (byte) (value - 128);
		valueB = (byte) (valueB | baseB);

		return (valueB);
	}

	public static short byteToShort(byte value) {
		short v = 0;

		if (0 != (value & 0x80)) { 
			v = (short) (value & 0x7F);
			v += 128;
		} else {
			v = (short) value;
		}
		return v;
	}

	public static String hexPrintByte(byte b) {
		String rsstr = "";
		short shortHi;
		short shortLo;

		char charHi;
		char charLo; // 

		shortLo = (short) (b & 0x0F);
		shortHi = (short) ((b & 0xF0) >> 4);

		if (shortHi >= 10)
			charHi = (char) ('A' + shortHi - 10);
		else
			charHi = (char) ('0' + shortHi);

		if (shortLo >= 10)
			charLo = (char) ('A' + shortLo - 10);
		else
			charLo = (char) ('0' + shortLo);

		rsstr = "" + charHi + charLo; 
		return rsstr;
	}

	public static String hexPrintBytes(int len, byte[] b) {
		StringBuffer strbf = new StringBuffer("");
		if (0 >= len)
			return strbf.toString();
		for (int i = 0; i < len; i++) {
			strbf.append(hexPrintByte(b[i]));
		}
		return strbf.toString();
	}

	public static String hexPrintBytes(int len, byte[] b, int offset) {
		StringBuffer strbf = new StringBuffer("");
		if (0 >= len)
			return strbf.toString();
		for (int i = 0; i < len; i++) {
			strbf.append(hexPrintByte(b[i + offset]));
		}
		return strbf.toString();
	}

	public static void padingBlank(byte[] des, int start, int len, byte value) {
		for (int j = 0; j < len; j++) {
			des[start + j] = value;
		}
	}

	public static byte[] getSubSequence(byte[] srcb, int s, int size) {
		if (0 >= srcb.length)
			return null;

		byte[] desb = new byte[size];
		System.arraycopy(srcb, s, desb, 0, size);

		return desb;
	}

	public static String bytesToString(byte[] barray) {
		String str = new String("");
		char cl, cr;
		short s, sr, sl;

		for (int i = 0; i < barray.length; i++) {
			s = UByte.byteToShort(barray[i]);
			sr = (short) (s & 0x0F);
			sl = (short) (s & 0xF0);
			sl = (short) (sl >> 4);

			if (sl >= 0 && sl <= 9)
				cl = (char) ('0' + sl);
			else
				cl = (char) ('A' + sl - 10);

			if (sr >= 0 && sr <= 9)
				cr = (char) ('0' + sr);
			else
				cr = (char) ('A' + sr - 10);

			str += cl;
			str += cr;
		}
		return str;
	}

	public static String bytesToCharString(byte[] barray) {
		String str = new String("");
		char c;

		for (int i = 0; i < barray.length; i++) {
			c = (char) barray[i];
			str = str + c;
		}
		return str;
	}

	public static byte[] StringToBytes(String str) {
		char l, r;
		short tv;
		byte[] b = new byte[str.length() / 2];

		for (int i = 0; i < str.length() / 2; i++) {
			l = str.charAt(i * 2);
			r = str.charAt(i * 2 + 1);

			if (l >= '0' && l <= '9')
				tv = (short) ((l - '0'));
			else if (l >= 'A' && l <= 'F')
				tv = (short) ((l - 'A') + 10);
			else if (l >= 'a' && l <= 'f')
				tv = (short) ((l - 'a') + 10);
			else
				tv = 0;

			tv = (short) (tv << 4);

			if (r >= '0' && r <= '9')
				tv |= (short) ((r - '0'));
			else if (r >= 'A' && r <= 'F')
				tv |= (short) ((r - 'A') + 10);
			else if (r >= 'a' && r <= 'f')
				tv |= (short) ((r - 'a') + 10);
			else
				tv = 0;

			b[i] = shortToByte(tv);
		}
		return b;
	}

	public static byte[] padingRight(byte[] bs, int n, byte b) {
		int len = bs.length;
		byte[] rtn = new byte[len + n];
		System.arraycopy(bs, 0, rtn, 0, len);
		for (int i = 0; i < n; i++)
			rtn[len + i] = b;
		return rtn;
	}

	public static byte[] padingLeft(byte[] bs, int n, byte b) {
		int len = bs.length;
		byte[] rtn = new byte[len + n];
		for (int i = 0; i < n; i++) {
			rtn[i] = b;
		}
		System.arraycopy(bs, 0, rtn, n, len);
		return rtn;
	}
	
	public static int literalBytesToInt(byte[] b) {
		int rtn = (int) (b[0] - 0x30);
		for (int i = 1; i < b.length; i++)
			rtn = rtn * 10 + (int) (b[i] - 0x30);

		return rtn;
	}
	
	
	public static String byteToHex(byte[] bArr){
		String hex = "";
		for(int i = 0 ; i<bArr.length ; i++){
			hex = hex + UByte.hexPrintByte(bArr[i]) + " " ;
		}
		return hex;
	}
}

