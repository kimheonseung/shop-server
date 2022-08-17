package com.devh.project.common.dto;

import com.devh.project.common.constant.AuthType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
public class MemberDTO {
    private Long id;
    private String username;
    private Set<AuthType> authTypes = new HashSet<>();
}
