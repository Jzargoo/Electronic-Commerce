package services;

import Annotations.IT;
import com.jzargo.Application;
import com.jzargo.dto.UserCreateAndUpdateDto;
import com.jzargo.dto.UserReadDto;
import com.jzargo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@IT
@ContextConfiguration(classes = Application.class)
public class UserServiceIntegrationTest {
    @Autowired
    private UserService userService;
    UserReadDto savedUser;
    @BeforeEach
    void setUp() {
        UserCreateAndUpdateDto userDto = UserCreateAndUpdateDto.builder()
                .phone("+77773662188")
                .username("TestUser")
                .password("Test@1234")
                .cartId(null)
                .ownProductIds(List.of(101, 102))
                .ordersId(List.of(201L, 202L))
                .build();

        savedUser = userService.create(userDto);
    }

    @Test
    void save() {
        UserCreateAndUpdateDto build = UserCreateAndUpdateDto.builder()
                .phone("+77773662100")
                .username("Tests")
                .password("Test@5678")
                .build();

        UserReadDto userReadDto = userService.create(build);
        assertNotNull(userReadDto);
        assertEquals("+77773662100", userReadDto.getPhone());
        assertEquals("Tests", userReadDto.getUsername());
    }
    @Test
    void findAll(){
        List<UserReadDto> all = userService.findAll();
        assertThat(all, null).hasSize(1);
    }
    @Test
    void findById() {
        UserReadDto foundUser = userService.findById(savedUser.getId());

        assertNotNull(foundUser);
        assertEquals(savedUser.getId(), foundUser.getId());
        assertEquals("TestUser", foundUser.getUsername());
    }

    @Test
    void update_ShouldModifyUser() {
        UserCreateAndUpdateDto updatedDto = UserCreateAndUpdateDto.builder()
                .phone("+77770001122")
                .username("UpdatedUser")
                .password("NewPass@123")
                .cartId(null)
                .ownProductIds(List.of(103))
                .ordersId(List.of(203L))
                .build();

        UserReadDto updatedUser = userService.update(savedUser.getId(), updatedDto);

        assertNotNull(updatedUser);
        assertEquals("UpdatedUser", updatedUser.getUsername());
        assertEquals("+77770001122", updatedUser.getPhone());
    }
}
