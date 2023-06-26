package utils.email.payment

enum PaymentEmailAction {
    CREATED("Criada"),
    UPDATED("Modificada"),
    DELETED("Removida"),
    RESTORED("Restaurada"),
    RECEIVED("Recebida"),
    OVERDUE("Venceu")
    
    private String description
    
    PaymentEmailAction(String description) {
        this.description = description    
    }
    
    public String getDescription() {
        return this.description
    }
}