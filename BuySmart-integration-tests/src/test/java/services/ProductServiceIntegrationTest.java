package services;

import Annotations.IT;
import com.jzargo.Application;
import com.jzargo.common.Tags;
import com.jzargo.dto.ProductCreateAndUpdateDto;
import com.jzargo.dto.ProductReadDto;
import com.jzargo.dto.UserCreateAndUpdateDto;
import com.jzargo.services.ProductService;
import com.jzargo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@IT
@ContextConfiguration(classes = Application.class)
public class ProductServiceIntegrationTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    MultipartFile emptyFile = new MockMultipartFile("file", new byte[0]);
    ProductReadDto productReadDto;
    Long id;

    @BeforeEach
    void prepare(){

        UserCreateAndUpdateDto userDto = UserCreateAndUpdateDto.builder()
                .phone("+77773662188")
                .username("TestUser")
                .password("Test@1234")
                .cartId(null)
                .ownProductIds(List.of(101, 102))
                .ordersId(List.of(201L, 202L))
                .build();

         id = userService.create(userDto).getId();

        ArrayList<Tags> tags = new ArrayList<>();
        tags.add(Tags.EXCLUSIVE);
        ProductCreateAndUpdateDto build = ProductCreateAndUpdateDto.builder()
                .price(15F)
                .tags(tags)
                .userId(id)
                .images(List.of(emptyFile))
                .name("ProductTest")
                .description("This is a test")
                .build();

        productReadDto = productService.create(build);
    }
    @Test
    void save(){
        ArrayList<Tags> tags = new ArrayList<>();
        tags.add(Tags.ORGANIC);
        ProductCreateAndUpdateDto build = ProductCreateAndUpdateDto.builder()
                .price(15F)
                .images(List.of(emptyFile))
                .tags(tags)
                .userId(id)
                .name("Product2Test")
                .description("This is a test2")
                .build();

        ProductReadDto saved = productService.create(build);
        assertNotNull(saved);
        assertEquals(saved.getName(), build.getName());
    }
    @Test
    void find(){
        ProductReadDto byId = productService.findById(productReadDto.getId());
        assertEquals(productReadDto,byId);
    }
}