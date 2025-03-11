package database;

import com.jzargo.Application;
import com.jzargo.entity.User;
import com.jzargo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.AssertionErrors;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

@IT
@Slf4j
@ContextConfiguration(classes = Application.class)
public class UserAndUserRepositoryIntegrationTest {

    @Autowired
    UserRepository userRepository;


    @Test
    public void save(){
        User user = User.builder()
                .phone("+77773882195")
                .username("Test")
                .password("tests1010")
                .createdTime(LocalDate.now())
                .ProfileImage("OIP.jpg")
                .build();
        var save = userRepository.save(user);
        assertNotNull(save);
    }
    @Test
    public void findById(){
        var user =  userRepository.findById(2L);
        assertNotNull(user);
    }

    @Test
    public void findAll(){
        var users = userRepository.findAll();
        assertNotNull(users);
        assertThat(users, null).hasSize(3);
    }
}
