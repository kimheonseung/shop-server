package com.devh.project.cafe.repository;

import com.devh.project.cafe.entity.DailySales;
import com.devh.project.cafe.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DailySalesRepository extends JpaRepository<DailySales, Long> {
    Optional<DailySales> findByDateAndMenu(String date, Menu menu);
}
