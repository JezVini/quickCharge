package utils.message

import org.apache.commons.lang3.EnumUtils

class MessagePattern {
    private static final String CREATE = "default.created.message"
    private static final String UPDATE = "default.updated.message"
    private static final String DELETE = "default.deleted.message"
    
    public static String createPayer() {
        return MessagePattern.createMessage(MessagePattern.CREATE, "Pagador")
    }
    
    private static String createMessage(String code, List<String> args) {
        return message(code: code, args: args)
    }
}
