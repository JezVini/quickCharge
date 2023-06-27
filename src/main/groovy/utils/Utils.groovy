package utils

import javax.swing.text.MaskFormatter
import java.text.DecimalFormat
import java.util.regex.Pattern

class Utils {
    
    public static String removeNonNumeric(String text) {
        if (text == null) return null

        return text?.replaceAll("\\D+","")
    }

    public static Boolean isPhonePatternMatch(String phone) {
        final Pattern PHONE_PATTERN = ~/\(\d{2}\) 9?\d{4}-\d{4}/
        return phone.matches(PHONE_PATTERN)
    }
    
    public static Boolean isPostalCodePatternMatch(String postalCode) {
        final Pattern POSTAL_CODE_PATTERN = ~/\d{5}-\d{3}/
        return postalCode.matches(POSTAL_CODE_PATTERN)
    }    
    
    public static Boolean isStatePatternMatch(String state) { 
        final Pattern STATE_PATTERN = ~/[A-Z]{2}/
        return state.matches(STATE_PATTERN)
    }
    
    public static BigDecimal toBigDecimalFormatted(String value) {
        return new BigDecimal(value.replaceAll('\\.', "").replaceAll(',', "."))
    }
    
    public static String formatPhone(String phone) {
        String mask = phone.length() == 10 ? "(##) ####-####" : "(##) #####-####"
        MaskFormatter maskFormatter = new MaskFormatter(mask)
        maskFormatter.setValueContainsLiteralCharacters(false)
        return maskFormatter.valueToString(phone)
    }
    
    public static String formatCpfCnpj(String cpfCnpj) {
        String mask = cpfCnpj.length() == 11 ? "###.###.###-##" : "##.###.###/####-##"
        MaskFormatter maskFormatter = new MaskFormatter(mask)
        maskFormatter.setValueContainsLiteralCharacters(false)
        return maskFormatter.valueToString(cpfCnpj)
    }
    
    public static String formatNumberSeparatorWithComma(Double value) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00")
        return decimalFormat.format(value)
    }

    public static String formatCep(String cep) {
        String mask = "#####-###"
        MaskFormatter maskFormatter = new MaskFormatter(mask)
        maskFormatter.setValueContainsLiteralCharacters(false)
        return maskFormatter.valueToString(cep)
    }
}
