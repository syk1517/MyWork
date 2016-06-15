package com.ksoft.offlinesdk.util;

import com.google.gson.Gson;

public class Tools {
	
	public static String ToJson(Object object) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		return gson.toJson(object);
	}
	
	public static Object ToObject(String json, Class object) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		return gson.fromJson(json, object);
	}
}
