package quickcharge.utils

import utils.Utils

class UtilsTagLib {

    def formatNumberSeparatorWithComma = { attrs ->
        out << Utils.formatNumberSeparatorWithComma(attrs.value as Double)
    }

    def formatMonetaryValue = { attrs ->
        String valueWithComma = Utils.formatNumberToMonetary(attrs.value as Double)
        out << 'R$: ' + valueWithComma
    }

    def formatCpfCnpj = { attrs ->
        out << Utils.formatCpfCnpj(attrs.value as String)
    }
    
    def formatPhone = { attrs ->
        out << Utils.formatPhone(attrs.value as String)
    }

    def formatCep = { attrs ->
        out << Utils.formatCep(attrs.value as String)
    }
}
