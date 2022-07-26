package com.ivis.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;



public class HashMapUtil {
	
	public JSONArray hashmapToList(JSONObject map) {
		JSONArray arr = new JSONArray();
		for (String i: map.keySet())
		{
			JSONObject tempObj = new JSONObject();
			tempObj.put("title", i);
			tempObj.put("value",map.get(i) );
			arr.put(tempObj);
		}
		return arr;
	}
	
	public static void main(String [] args)
	{
		JSONObject response = new MngtServerUtils().getCentralUnitDetails_1_0(1016);

		
		
		JSONObject r = response.getJSONObject("CentralBoxDetails");
		Object o = new HashMapUtil().hashmapToList(response.getJSONObject("CentralBoxDetails"));

		System.out.println(r);
		
		
		System.out.println("**************************");
		
		System.out.println(o);
	}

}
