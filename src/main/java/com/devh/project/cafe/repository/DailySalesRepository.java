package com.devh.project.cafe.repository;

import com.devh.project.cafe.entity.DailySales;
import com.devh.project.cafe.entity.Menu;
import com.devh.project.cafe.projection.DailySalesAggregationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DailySalesRepository extends JpaRepository<DailySales, Long> {
    Optional<DailySales> findByDateAndMenu(String date, Menu menu);

    @Query(value =
            "select " +
                "sum(ds.count) as sales, " +
                "new com.devh.project.cafe.dto.MenuDTO(m.id, m.name, m.price, m.ice, m.onSale) as menu " +
            "from daily_sales ds " +
                "inner join menu m on ds.menu_id = m.id " +
            "where ds.date >= :date " +
            "group by ds.menu_id " +
            "order by sum desc " +
            "limit :top", nativeQuery = true)
    List<DailySalesAggregationProjection> aggregateByDateGreaterThanEqual(@Param("date") String date, @Param("top") int top);
}
