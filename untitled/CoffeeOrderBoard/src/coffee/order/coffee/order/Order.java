package coffee.order;

public record Order(int orderNumber, String customerName) {

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", customerName='" + customerName + "'" +
                '}';
    }
}
