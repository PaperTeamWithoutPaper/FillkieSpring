package com.fillkie.repository.user;

import com.fillkie.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
  public Optional<User> findByEmail(String email);
}
