import lombok.With;

import java.time.Instant;
import java.util.List;

public record Order(
        String id,
        @With Status orderStatus,
        Instant orderTimestamp,
        List<Product> products
) {
}
