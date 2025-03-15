package database;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import Annotations.IT;
import com.jzargo.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.jzargo.entity.Cart;
import com.jzargo.entity.User;
import com.jzargo.repository.CartRepository;
import com.jzargo.repository.UserRepository;
@IT
@ContextConfiguration(classes = Application.class)
public class CartAndCartRepositoryIntegrationTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveCart() {

        User user = User.builder()
                .username("test_user")
                .password("securepassword")
                .phone("+77773662100")
                .build();
        User savedUser = userRepository.save(user);

        Cart cart = new Cart();
        cart.setBuyer(savedUser);

        Cart savedCart = cartRepository.save(cart);

        assertNotNull(savedCart.getId());
        assertThat(savedCart.getBuyer()).isEqualTo(savedUser);
    }
}

