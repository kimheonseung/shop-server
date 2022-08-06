package com.devh.project.item.repository;

import com.devh.project.item.entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
