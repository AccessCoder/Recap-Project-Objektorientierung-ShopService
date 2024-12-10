import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) throws ProductNotFoundException {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Product productToOrder = productRepo.getProductById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product mit der Id: " + productId + " konnte nicht bestellt werden!"));
            products.add(productToOrder);
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), Status.PROCESSING, Instant.now(), products);

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getOrdersByStatus(Status status) {
        List<Order> filteredOrders = orderRepo.getOrders().stream()
                .filter(order -> order.orderStatus() == status)
                .toList();
        return filteredOrders;
    }

    public Order updateOrder(String orderId, Status status)  {
        Order orderToChange = orderRepo.getOrderById(orderId);
        return orderRepo.addOrder(orderToChange.withOrderStatus(status));
    }
}
