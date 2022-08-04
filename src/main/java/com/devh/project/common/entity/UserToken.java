package com.devh.project.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "user"})
@ToString
public class UserToken implements Serializable {
    private static final long serialVersionUID = -4514969123821169077L;
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name="user_username", referencedColumnName = "username")
    private User user;
    @Column(nullable = false, unique = true)
    private String refreshToken;

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
