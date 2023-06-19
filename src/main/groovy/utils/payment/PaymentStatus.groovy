package utils.payment

public enum PaymentStatus {
    PENDING,
    OVERDUE,
    RECEIVED,
    RECEIVED_IN_CASH,

    public Boolean canUpdate() {
        return [PaymentStatus.PENDING, PaymentStatus.OVERDUE].contains(this)
    }

    public Boolean isReceived() {
        return [PaymentStatus.RECEIVED, PaymentStatus.RECEIVED_IN_CASH].contains(this)
    }
}