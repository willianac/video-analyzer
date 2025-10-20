package com.willianac.video_analyzer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.willianac.video_analyzer.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByName(String name);
}
