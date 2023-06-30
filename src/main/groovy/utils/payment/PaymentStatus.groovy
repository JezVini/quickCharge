package utils.payment

public enum PaymentStatus {
    PENDING,
    OVERDUE,
    RECEIVED,
    RECEIVED_IN_CASH,
    
    public static List<PaymentStatus> getUpdatableList() {
        return [PaymentStatus.PENDING, PaymentStatus.OVERDUE]
    }
    
    public Boolean canUpdate() {
        return PaymentStatus.getUpdatableList().contains(this)
    }

    public Boolean isReceived() {
        return [PaymentStatus.RECEIVED, PaymentStatus.RECEIVED_IN_CASH].contains(this)
    }
    
    public String toLowerCase() {
        return this.name().toLowerCase()
    }
}