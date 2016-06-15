package com.ksoft.offlinesdk.util;

import android.content.Context;

public class Resource {

	public static int getIdentifier(Context context, String name,
			String defType, String defPackage) {
		if (null == context || null == name || name.length() == 0)
			return 0;
		return context.getResources().getIdentifier(name, defType, defPackage);
	}

	public static int getId(Context context, String name) {
		return Resource.getIdentifier(context, name, "id",
				context.getPackageName());
	}

	public static String getString(Context context, String name) {
		int id = Resource.getIdentifier(context, name, "string",
				context.getPackageName());
		return 0 == id ? null : context.getResources().getString(id);
	}

	public static int getDrawable(Context context, String name) {
		return Resource.getIdentifier(context, name, "drawable",
				context.getPackageName());
	}

	public static int getLayout(Context context, String name) {
		int id = Resource.getIdentifier(context, name, "layout",
				context.getPackageName());
		return id;
	}
	
	public static int getStyle(Context context, String name) {
		return Resource.getIdentifier(context, name, "style",
				context.getPackageName());
	}
	
	public static int getColor(Context context, String name) {
		return Resource.getIdentifier(context, name, "color",
				context.getPackageName());
	}

}
