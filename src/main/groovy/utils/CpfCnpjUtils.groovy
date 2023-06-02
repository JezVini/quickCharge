package utils

import java.util.regex.Pattern

class CpfCnpjUtils {
    
    public static final CPF_LENGTH = 11
    public static final CNPJ_LENGTH = 14
    public static final INVALID_CPF_SEQUENCES = ["00000000000", "11111111111", "22222222222", "33333333333",
                                                 "44444444444", "55555555555", "66666666666", "77777777777",
                                                 "88888888888", "99999999999"]

    public static Boolean isCpfCnpjPatternMatch(String cpfCnpj) {
        final Pattern CPF_PATTERN = ~/\d{3}\.\d{3}\.\d{3}-\d{2}/
        final Pattern CNPJ_PATTERN = ~/\d{2}\.\d{3}\.\d{3}\/\d{4}-{2}/
        return cpfCnpj.matches(CPF_PATTERN) || cpfCnpj.matches(CNPJ_PATTERN)
    }
    
    public static Boolean validate(String cpfCnpj) {
        cpfCnpj = Utils.removeNonNumeric(cpfCnpj)

        if (cpfCnpj == null || (cpfCnpj.length() != CPF_LENGTH && cpfCnpj.length() != CNPJ_LENGTH)) return false

        if (isCpf(cpfCnpj))
            return cpfValidate(cpfCnpj)
        else if (isCnpj(cpfCnpj)) {
            return cnpjValidate(cpfCnpj)
        }
    }

    public static Boolean isCnpj(String cpfCnpj) {
        if (!cpfCnpj) return false

        cpfCnpj = Utils.removeNonNumeric(cpfCnpj)

        return cpfCnpj.length() == CNPJ_LENGTH
    }

    public static Boolean isCpf(String cpfCnpj) {
        if (!cpfCnpj) return false

        cpfCnpj = Utils.removeNonNumeric(cpfCnpj)

        return cpfCnpj.length() == CPF_LENGTH
    }

    private static boolean cpfValidate(String cpf) {
        int auxFirstDigit = 0, auxSecondDigit = 0, cpfDigit
        int firstDigit = 0, secondDigit = 0, divisionRemainder = 0
        String checkerDigit, resultDigit

        if (!cpfSequenceValidate(cpf)) {
            return false
        }

        if (cpf.substring(0, 1) != "") {
            try {
                cpf = cpf.replace('.',' ')
                cpf = cpf.replace('-',' ')
                cpf = cpf.replaceAll(" ","")

                for (int i = 1; i < cpf.length() - 1; i++) {
                    cpfDigit = Integer.valueOf(cpf.substring(i -1, i)).intValue()
                    auxFirstDigit = auxFirstDigit + (11 - i) * cpfDigit
                    auxSecondDigit = auxSecondDigit + (12 - i) * cpfDigit
                }

                divisionRemainder = (auxFirstDigit % 11)
                if (divisionRemainder < 2) {
                    firstDigit = 0
                } else {
                    firstDigit = 11 - divisionRemainder
                }

                auxSecondDigit += 2 * firstDigit
                divisionRemainder = (auxSecondDigit % 11)
                if (divisionRemainder < 2) {
                    secondDigit = 0
                } else {
                    secondDigit = 11 - divisionRemainder
                }

                checkerDigit = cpf.substring(cpf.length()-2, cpf.length())
                resultDigit = String.valueOf(firstDigit) + String.valueOf(secondDigit)

                return checkerDigit == resultDigit

            } catch (Exception e) {
                return false
            }
        } else {
            return false
        }
    }

    private static boolean cpfSequenceValidate(String cpf) {
        if (cpf?.trim().isEmpty()) {
            return false
        }

        if (INVALID_CPF_SEQUENCES.contains(cpf) ) {
            return false
        }

        return true
    }

    private static boolean cnpjValidate(String cnpj) {
        int total = 0, digit
        char[] cnpjCharacters
        String calculatedCNPJ

        if (cnpj.startsWith("00000000000000")) return false

        if (cnpj.substring(0, 1) != ""){
            try {
                cnpj = cnpj.replace('.',' ')
                cnpj = cnpj.replace('/',' ')
                cnpj = cnpj.replace('-',' ')
                cnpj = cnpj.replaceAll(" ","")
                calculatedCNPJ = cnpj.substring(0,12)

                if (cnpj.length() != 14) return false

                cnpjCharacters = cnpj.toCharArray()

                for (int i = 0; i < 4; i++) {
                    if ((cnpjCharacters[i]-48 >= 0) && (cnpjCharacters[i]-48 <= 9)) {
                        total += (cnpjCharacters[i] - 48 ) * (6 - (i + 1))
                    }
                }

                for (int i = 0; i < 8; i++) {
                    if ((cnpjCharacters[i+4]-48 >= 0) && (cnpjCharacters[i+4]-48 <= 9)) {
                        total += (cnpjCharacters[i+4] - 48 ) * (10 - (i + 1))
                    }
                }

                digit = 11 - (total % 11)
                calculatedCNPJ += ((digit == 10) || (digit == 11)) ? "0" : Integer.toString(digit)

                total = 0
                for (int i = 0; i < 5; i++) {
                    if ((cnpjCharacters[i]-48 >= 0) && (cnpjCharacters[i]-48 <= 9)) {
                        total += (cnpjCharacters[i] - 48) * (7 - (i + 1))
                    }
                }

                for (int i = 0; i < 8; i++) {
                    if ((cnpjCharacters[i+5]-48 >= 0) && (cnpjCharacters[i+5]-48 <= 9)) {
                        total += (cnpjCharacters[i+5] - 48) * (10 - (i + 1))
                    }
                }

                digit = 11 - (total % 11)
                calculatedCNPJ += ((digit == 10) || (digit == 11)) ? "0" : Integer.toString(digit)

                return cnpj == calculatedCNPJ
            } catch (Exception e) {
                return false
            }
        } else return false
    }
}
