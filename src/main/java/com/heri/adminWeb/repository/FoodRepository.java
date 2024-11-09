package com.heri.adminWeb.repository;

import com.heri.adminWeb.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
//    List<Food> findAll(); findAll có san không cần định nghĩa lại
}
