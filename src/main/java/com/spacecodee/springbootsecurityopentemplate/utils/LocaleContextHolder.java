// LocaleContextHolder.java
package com.spacecodee.springbootsecurityopentemplate.utils;

public class LocaleContextHolder {
    private static final ThreadLocal<String> localeContext = new ThreadLocal<>();

    public static void setLocale(String locale) {
        localeContext.set(locale != null ? locale : "en");
    }

    public static String getLocale() {
        return localeContext.get() != null ? localeContext.get() : "en";
    }

    public static void clear() {
        localeContext.remove();
    }
}