package com.devh.project.cafe.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"date", "menu_id"}
        )
)
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DailySales {
    @Id @GeneratedValue
    @Column(name = "DAILY_SALES_ID")
    private Long id;
    private String date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    private Menu menu;
    private Long sales;

    public void addSales(long amount) {
        this.sales += amount;
    }
}
