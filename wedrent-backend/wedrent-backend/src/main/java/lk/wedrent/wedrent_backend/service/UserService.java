package lk.wedrent.wedrent_backend.service;

import lk.wedrent.wedrent_backend.dto.RegisterRequest;
import lk.wedrent.wedrent_backend.entity.User;

import java.util.Optional;

public interface UserService {
    
    User register(RegisterRequest request);
    
    Optional<User> findByEmail(String email);
}
