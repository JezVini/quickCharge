package utils

class CpfCnpjUtils {

    public static final CPF_LENGTH = 11
    public static final CNPJ_LENGTH = 14
    public static final INVALID_CPF_SEQUENCES = ["00000000000", "11111111111", "22222222222", "33333333333",
                                                 "44444444444", "55555555555", "66666666666", "77777777777",
                                                 "88888888888", "99999999999"]

    public static Boolean validate(String cpfCnpj) {
        cpfCnpj = Utils.removeNonNumeric(cpfCnpj)

        if (cpfCnpj == null || (cpfCnpj.length() != CPF_LENGTH && cpfCnpj.length() != CNPJ_LENGTH)) return false

        if (isCpf(cpfCnpj))
            return validaCPF(cpfCnpj)
        else if (isCnpj(cpfCnpj)) {
            return validaCNPJ(cpfCnpj)
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

    private static boolean validaCPF(String strCpf) {
        int digito1Aux = 0, digito2Aux = 0, digitoCPF
        int digito1 = 0, digito2 = 0, restoDivisao = 0
        String strDigitoVerificador, strDigitoResultado

        if (!validateCPFSequence(strCpf)) {
            return false
        }

        if (strCpf.substring(0, 1) != "") {
            try {
                strCpf = strCpf.replace('.',' ')
                strCpf = strCpf.replace('-',' ')
                strCpf = strCpf.replaceAll(" ","")

                for (int contador = 1; contador < strCpf.length() - 1; contador++) {
                    digitoCPF = Integer.valueOf(strCpf.substring(contador -1, contador)).intValue()
                    digito1Aux = digito1Aux + (11 - contador) * digitoCPF
                    digito2Aux = digito2Aux + (12 - contador) * digitoCPF
                }

                restoDivisao = (digito1Aux % 11)
                if (restoDivisao < 2) {
                    digito1 = 0
                } else {
                    digito1 = 11 - restoDivisao
                }

                digito2Aux += 2 * digito1
                restoDivisao = (digito2Aux % 11)
                if (restoDivisao < 2) {
                    digito2 = 0
                } else {
                    digito2 = 11 - restoDivisao
                }

                strDigitoVerificador = strCpf.substring(strCpf.length()-2, strCpf.length())
                strDigitoResultado = String.valueOf(digito1) + String.valueOf(digito2)

                return strDigitoVerificador == strDigitoResultado

            } catch (Exception e) {
                return false
            }
        } else {
            return false
        }
    }

    private static boolean validateCPFSequence(String strCpf) {
        if (strCpf?.trim().isEmpty()) {
            return false
        }

        if (INVALID_CPF_SEQUENCES.contains(strCpf) ) {
            return false
        }

        return true
    }

    private static boolean validaCNPJ(String strCNPJ) {
        int soma = 0, digito
        char[] caracteresCNPJ
        String strCNPJ_Calculado

        if (strCNPJ.startsWith("00000000000000")) return false

        if (strCNPJ.substring(0, 1) != ""){
            try {
                strCNPJ = strCNPJ.replace('.',' ')
                strCNPJ = strCNPJ.replace('/',' ')
                strCNPJ = strCNPJ.replace('-',' ')
                strCNPJ = strCNPJ.replaceAll(" ","")
                strCNPJ_Calculado = strCNPJ.substring(0,12)

                if (strCNPJ.length() != 14) return false

                caracteresCNPJ = strCNPJ.toCharArray()

                for (int i = 0; i < 4; i++) {
                    if ((caracteresCNPJ[i]-48 >= 0) && (caracteresCNPJ[i]-48 <= 9)) {
                        soma += (caracteresCNPJ[i] - 48 ) * (6 - (i + 1))
                    }
                }

                for (int i = 0; i < 8; i++) {
                    if ((caracteresCNPJ[i+4]-48 >= 0) && (caracteresCNPJ[i+4]-48 <= 9)) {
                        soma += (caracteresCNPJ[i+4] - 48 ) * (10 - (i + 1))
                    }
                }

                digito = 11 - (soma % 11)
                strCNPJ_Calculado += ((digito == 10) || (digito == 11)) ? "0" : Integer.toString(digito)

                soma = 0
                for (int i = 0; i < 5; i++) {
                    if ((caracteresCNPJ[i]-48 >= 0) && (caracteresCNPJ[i]-48 <= 9)) {
                        soma += (caracteresCNPJ[i] - 48) * (7 - (i + 1))
                    }
                }

                for (int i = 0; i < 8; i++) {
                    if ((caracteresCNPJ[i+5]-48 >= 0) && (caracteresCNPJ[i+5]-48 <= 9)) {
                        soma += (caracteresCNPJ[i+5] - 48) * (10 - (i + 1))
                    }
                }

                digito = 11 - (soma % 11)
                strCNPJ_Calculado += ((digito == 10) || (digito == 11)) ? "0" : Integer.toString(digito)

                return strCNPJ == strCNPJ_Calculado
            } catch (Exception e) {
                return false
            }
        } else return false
    }
}
