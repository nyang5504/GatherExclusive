package com.yang.gatherexclusive.repository;

import com.yang.gatherexclusive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *     JPA repository for User entity model
 * </p>
 * <p>
 *     Provides methods for CRUD operations
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a User based on a given email
     * @param email email associated with a user
     * @return {@code User} object related to the given email
     */
    User findByEmail(String email);
}
