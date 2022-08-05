package com.devh.project.common.repository;

import com.devh.project.common.entity.RedisMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisMemberRepository extends CrudRepository<RedisMember, String> {
}
