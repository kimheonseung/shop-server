package com.devh.project.common.dto;

import com.devh.project.common.constant.Role;
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
    private Set<Role> roles = new HashSet<>();
}
