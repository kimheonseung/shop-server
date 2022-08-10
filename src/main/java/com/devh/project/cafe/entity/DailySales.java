package com.devh.project.cafe.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"date", "menu_id"}
        ),
        name = "CAFE_DAILY_SALES"
)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailySales {
    @Id @GeneratedValue
    @Column(name = "DAILY_SALES_ID")
    private Long id;
    private String date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    private Menu menu;
    private Long sales;

    public static DailySales create(String date, Menu menu) {
        return DailySales.builder()
                .date(date)
                .menu(menu)
                .sales(0L)
                .build();
    }
    
    public void addSales(long amount) {
        this.sales += amount;
    }
}
