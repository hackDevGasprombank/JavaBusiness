package com.example.gasprombankjavabusiness.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class ReviewLoadPolice {

    private final Map<ReviewWebSource, Integer> loadedMap = new ConcurrentHashMap<>();

    public boolean isLoaded(ReviewWebSource source) {
        return loadedMap.getOrDefault(source, 0) == 1;
    }

    public void markLoaded(ReviewWebSource source) {
        loadedMap.put(source, 1);
    }

    public void reset(ReviewWebSource source) {
        loadedMap.put(source, 0);
    }

    public void resetAll() {
        loadedMap.clear();
    }
}
