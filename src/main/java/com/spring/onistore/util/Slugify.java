package com.spring.onistore.util;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

public class Slugify {
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public static String toSlug(String input) {
        Random random = new Random();
        int upper = 100000;
        int random_generate = random.nextInt(upper);
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH) + "-" + random_generate;
    }
}
