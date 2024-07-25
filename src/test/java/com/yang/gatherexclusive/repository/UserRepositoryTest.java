package com.yang.gatherexclusive.repository;

import com.yang.gatherexclusive.entity.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

    @ParameterizedTest
    @ValueSource(strings = {"test1@gmail.com", "test2@gmail.com", "test3@gmail.com", "anothertest@gmail.com"})
    public void testFindByEmail(String email){
        User user = new User();
        user.setName("hello"+email);
        user.setEmail(email);
        userRepository.save(user);
        final User found = userRepository.findByEmail(email);
        assertEquals(user.getEmail(), found.getEmail());
        assertEquals(user.getName(), found.getName());
    }
}
