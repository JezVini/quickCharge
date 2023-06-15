package utils.payment

public enum PaymentStatus {
    PENDING,
    OVERDUE,
    RECEIVED,
    RECEIVED_IN_CASH,

    public static final List<PaymentStatus> UPDATABLE_STATUS = [PaymentStatus.PENDING, PaymentStatus.OVERDUE] 
    
    public Boolean canUpdate() {
        return PaymentStatus.UPDATABLE_STATUS.contains(this)
    }
}