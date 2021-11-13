package com.example.rpc.example.spring.util;

import java.util.Map;

public class AnnotationUtils {
    public static <T> T getAttribute(Map<String, Object> attributes, String attributeName) {
        return (T) attributes.get(attributeName);
    }
}
