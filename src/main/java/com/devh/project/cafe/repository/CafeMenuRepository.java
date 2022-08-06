package com.devh.project.cafe.repository;

import com.devh.project.cafe.entity.CafeMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeMenuRepository extends JpaRepository<CafeMenu, Long> {
}
