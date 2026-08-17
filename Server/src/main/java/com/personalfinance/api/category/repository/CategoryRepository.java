package com.personalfinance.api.category.repository;

import com.personalfinance.api.category.entity.Category;
import com.personalfinance.api.category.entity.CategoryType;
import com.personalfinance.api.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsByUserAndNameAndDeletedFalse(User user, String name);

    Optional<Category> findByIdAndUserAndDeletedFalse(Integer id, User user);

    @EntityGraph(attributePaths = "color")
    List<Category> findByUserAndDeletedFalse(User user);

    @EntityGraph(attributePaths = "color")
    List<Category> findByUserAndTypeAndDeletedFalse(User user, CategoryType type);
}
