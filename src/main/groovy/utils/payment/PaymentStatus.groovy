package utils.payment

public enum PaymentStatus {
    PENDING,
    OVERDUE,
    RECEIVED,
    RECEIVED_IN_CASH,
    
    public static List<PaymentStatus> getUpdatableList() {
        return [PaymentStatus.PENDING, PaymentStatus.OVERDUE]
    }
 
    public static List<PaymentStatus> getReceivedList() {
        return [PaymentStatus.RECEIVED, PaymentStatus.RECEIVED_IN_CASH]
    }
    
    public Boolean canUpdate() {
        return PaymentStatus.getUpdatableList().contains(this)
    }

    public Boolean isReceived() {
        return PaymentStatus.getReceivedList().contains(this)
    }
    
    public String toLowerCase() {
        return this.name().toLowerCase()
    }
}