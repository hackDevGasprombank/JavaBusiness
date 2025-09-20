package com.example.gasprombankjavabusiness.utils;

import java.util.Collection;

public record ApiListResponse<T>(
        Collection<T> result,
        Long count
) {

}
