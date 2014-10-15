package com.sai.util;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JSONUtil {

	private JSONUtil() {

	}

	public static String Object2String(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);

	}

	public static <T> T json2Object(String json, Class<T> classOfT) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		return (T) gson.fromJson(json, classOfT);
	}

	public static <T> List<T> json2List(String json, Type type) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		return (List<T>) gson.fromJson(json, type);
	}

	public static Map<String, String> parseJSON2Map(String jsonStr) {
		Gson gson = new Gson();
		Type stringStringMap = new TypeToken<Map<String, String>>() {
		}.getType();
		return gson.fromJson(jsonStr, stringStringMap);
	}

	public static Map<String, String> parseJSON2Map(Object obj) {
		String jsonString = Object2String(obj);
		Gson gson = new Gson();
		Type stringStringMap = new TypeToken<Map<String, String>>() {
		}.getType();
		return gson.fromJson(jsonString, stringStringMap);
	}
}
