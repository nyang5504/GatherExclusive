package com.yang.gatherexclusive.repository;

import com.yang.gatherexclusive.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.test.database.replace=none",
        "spring.datasource.url=jdbc:mysql://localhost:3306/gather_exclusive?createDatabaseIfNotExist=true"
})
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmail(){
        User user = new User();
        user.setName("hello");
        user.setEmail("hello@gmail.com");
        userRepository.save(user);
        final User found = userRepository.findByEmail("hello@gmail.com");
        assertEquals(user.getEmail(), found.getEmail());
        assertEquals(user.getName(), found.getName());
    }
}
