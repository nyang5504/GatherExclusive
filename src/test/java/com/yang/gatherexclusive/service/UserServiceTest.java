package com.yang.gatherexclusive.service;

import com.yang.gatherexclusive.entity.User;
import com.yang.gatherexclusive.repository.UserRepository;
import com.yang.gatherexclusive.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {
        "spring.test.database.replace=none",
        "spring.datasource.url=jdbc:mysql://localhost:3306/gather_exclusive?createDatabaseIfNotExist=true",
})
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testFindUserByEmail(){
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setName("name");
        user.setPassword("test");

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(user);

        assertEquals(user.getName(), userService.findUserByEmail("test@gmail.com").getName());
    }
}
