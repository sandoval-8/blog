package com.blog.util;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Convertir <T> {
	
	public static ObjectMapper mapper = new ObjectMapper();
	
	public static JSONObject stringToJson(Object stringObject) {
		JSONObject jsonObject = null;
		try {
			String jsonString = mapper.writeValueAsString(stringObject);
			jsonObject = new JSONObject(jsonString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	public static String jsonToString(JSONObject jsonObject) {
		String jsonString = jsonObject.toString();
		try {
			mapper.readValue(jsonString, String.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
