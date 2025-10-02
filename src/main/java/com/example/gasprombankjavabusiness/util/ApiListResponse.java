package com.example.gasprombankjavabusiness.util;

import java.util.Collection;

public record ApiListResponse<T>(
        Collection<T> result,
        Long total
) {

}
