package com.devh.project.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "member", timeToLive = 300)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RedisMember {
    @Id
    private String username;
    private String password;
    private String authKey;
}
