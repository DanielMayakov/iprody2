package coffee.order;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoffeeOrderBoard {

    private static final Logger logger = Logger.getLogger(CoffeeOrderBoard.class.getName());
    private final List<Order> orders = new LinkedList<>();
    private int nextOrderNumber = 1; // Счетчик для генерации уникальных номеров заказов

    /**
     * Добавляет новый заказ в очередь.
     *
     * @param customerName Имя клиента
     */
    public void add(String customerName) {
        Order newOrder = new Order(nextOrderNumber++, customerName);
        orders.add(newOrder);
        logger.log(Level.INFO, "Добавлен новый заказ: {0}", newOrder);
    }

    /**
     * Выдает ближайший на очереди заказ.
     */
    public void deliver() {
        if (orders.isEmpty()) {
            logger.log(Level.WARNING, "Попытка выдать заказ из пустой очереди.");
            return;
        }
        Order deliveredOrder = orders.remove(0);
        logger.log(Level.INFO, "Выдан заказ: {0}", deliveredOrder);
    }

    /**
     * Выдает заказ с определенным номером.
     *
     * @param orderNumber Номер заказа
     */
    public void deliver(int orderNumber) {
        Optional<Order> optionalOrder = orders.stream()
                .filter(order -> order.orderNumber() == orderNumber)
                .findFirst();

        if (optionalOrder.isPresent()) {
            Order deliveredOrder = optionalOrder.get();
            orders.remove(deliveredOrder);
            logger.log(Level.INFO, "Выдан заказ с номером {0}: {1}", new Object[]{orderNumber, deliveredOrder});
        } else {
            logger.log(Level.WARNING, "Заказ с номером {0} не найден.", orderNumber);
        }
    }

    /**
     * Выводит текущее состояние очереди в консоль.
     */
    public void draw() {
        logger.log(Level.INFO, "Текущее состояние очереди:");
        System.out.println("============ Coffee Order Board ============");
        System.out.println("Num | Name");
        for (Order order : orders) {
            System.out.printf("%-4d| %s%n", order.orderNumber(), order.customerName());
        }
        System.out.println("============================================");
    }
}
