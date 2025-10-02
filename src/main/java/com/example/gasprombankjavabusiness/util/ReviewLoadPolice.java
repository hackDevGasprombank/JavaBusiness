package com.example.gasprombankjavabusiness.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class ReviewLoadPolice {

    private final Map<WebSource, Integer> loadedMap = new ConcurrentHashMap<>();

    public boolean isLoaded(WebSource source) {
        return loadedMap.getOrDefault(source, 0) == 1;
    }

    public void markLoaded(WebSource source) {
        loadedMap.put(source, 1);
    }

    public void reset(WebSource source) {
        loadedMap.put(source, 0);
    }

    public void resetAll() {
        loadedMap.clear();
    }
}
