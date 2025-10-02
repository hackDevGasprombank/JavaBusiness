package com.example.gasprombankjavabusiness.util;


import org.springframework.stereotype.Component;

@Component
public class TextJsonCleaner {

    public String clean(String text) {
        if (text == null) {
            return null;
        }
        return text
                .replace("\\n", "\n")   // строка "\n" -> перенос строки
                .replace("\u00A0", " ") // NBSP -> обычный пробел
                .replaceAll("[ ]{2,}", " ") // двойные пробелы -> один
                .trim();
    }
}
