package utils

import java.util.regex.Pattern

class Utils {
    
    public static final Pattern PHONE_PATTERN = ~/\(\d{2}\) 9?\d{4}-?\d{4}/
    public static final Pattern POSTAL_CODE_PATTERN = ~/\d{5}-\d{3}/
    public static final Pattern STATE_PATTERN = ~/[A-Z]{2}/
    
    public static String removeNonNumeric(String text) {
        if (text == null) return null

        return text?.replaceAll("\\D+","")
    }

    public static Boolean isPhonePatternMatch(String phone) {
        return phone.matches(PHONE_PATTERN)
    }
    
    public static Boolean isPostalCodePatternMatch(String postalCode) {
        return postalCode.matches(POSTAL_CODE_PATTERN)
    }    
    
    public static Boolean isStatePatternMatch(String state) {
        return state.matches(STATE_PATTERN)
    }
    
}
