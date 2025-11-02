package lk.wedrent.wedrent_backend.service;

import lk.wedrent.wedrent_backend.entity.User;
import lk.wedrent.wedrent_backend.dto.RegisterRequest;

public interface UserService {
    User register(RegisterRequest request);
    User findByEmail(String email);
}
