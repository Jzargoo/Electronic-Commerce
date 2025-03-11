package database;

import com.jzargo.entity.Cart;
import com.jzargo.repository.CartRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

@IT
public class CartAndCartRepositoryIntegrationTest {
    final CartRepository cartRepository;

    public CartAndCartRepositoryIntegrationTest(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Test
    public void save(){
        Cart cart= Cart.builder()
                .payment(14.30F)
                .quantity(2)
                .LastUpdate(LocalDate.now())
                .build();
    }
}
