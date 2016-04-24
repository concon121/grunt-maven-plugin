package net.cbr.software.grunt_runner.utils;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonHelper {

	private static final JsonNodeFactory factory = JsonNodeFactory.instance;

	public static String toJSON(Map<String, String> reqs) {

		ArrayNode arry = factory.arrayNode();
		
		Set<Entry<String, String>> entries = reqs.entrySet();
		
		for (Entry<String, String> entry : entries) {
			ObjectNode obj = arry.addObject();
			obj.put("key", entry.getKey());
			obj.put("value", entry.getValue());
		}

		String json = arry.toString();
		json = json.replace("\"", "\\\"");

		return json;
	}

}
