package com.devh.project.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@Getter
public abstract class BaseMember implements Serializable {
	private static final long serialVersionUID = -2158602508448402581L;
	@Id @GeneratedValue
	@Column(name = "MEMBER_ID")
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
}
