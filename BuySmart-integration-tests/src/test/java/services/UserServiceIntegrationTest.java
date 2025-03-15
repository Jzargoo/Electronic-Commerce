package services;

import Annotations.IT;
import com.jzargo.Application;
import com.jzargo.dto.UserCreateAndUpdateDto;
import com.jzargo.dto.UserReadDto;
import com.jzargo.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@IT
@ContextConfiguration(classes = Application.class)
public class UserServiceIntegrationTest {
    @Autowired
    private UserService userService;
    @Test
    void save (){
        UserCreateAndUpdateDto build = UserCreateAndUpdateDto.builder()
                .password("TestPassword")
                .phone("+711133322")
                .username("TestUsername")
                .build();

        UserReadDto userReadDto = userService.create(build);
        assertNotNull(userReadDto);
        assertEquals("+711133322",userReadDto.getPhone());
    }

}
