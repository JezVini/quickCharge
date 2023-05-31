package utils

import java.util.regex.Pattern

class Utils {
    
    public static final Pattern PHONE_PATTERN = ~/\(\d{2}\) 9?\d{4}-?\d{4}/
    
    public static String removeNonNumeric(String text) {
        if (text == null) return null

        return text?.replaceAll("\\D+","")
    }

    public static Boolean isPhonePatternMatch(String phone) {
        return phone.matches(PHONE_PATTERN)
    }
    
}
