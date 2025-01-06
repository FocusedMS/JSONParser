package com.jsonparser;

import java.util.*;

public class JSONBuilder {

    public static String buildJson(Object object) {
        if (object instanceof Map) {
            return buildJsonObject((Map<String, Object>) object);
        } else if (object instanceof List) {
            return buildJsonArray((List<Object>) object);
        }
        return String.valueOf(object);
    }

    private static String buildJsonObject(Map<String, Object> jsonObject) {
        StringBuilder sb = new StringBuilder("{");
        Iterator<Map.Entry<String, Object>> iterator = jsonObject.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            sb.append("\"").append(entry.getKey()).append("\": ").append(buildJson(entry.getValue()));
            if (iterator.hasNext()) sb.append(", ");
        }
        sb.append("}");
        return sb.toString();
    }

    private static String buildJsonArray(List<Object> jsonArray) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < jsonArray.size(); i++) {
            sb.append(buildJson(jsonArray.get(i)));
            if (i < jsonArray.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
