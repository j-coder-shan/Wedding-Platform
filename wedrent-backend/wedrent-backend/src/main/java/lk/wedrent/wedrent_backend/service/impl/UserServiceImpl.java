package lk.wedrent.wedrent_backend.service.impl;

import lk.wedrent.wedrent_backend.service.UserService;
import lk.wedrent.wedrent_backend.repository.UserRepository;
import lk.wedrent.wedrent_backend.dto.RegisterRequest;
import lk.wedrent.wedrent_backend.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        User u = new User();
        u.setEmail(request.getEmail());
        u.setPassword(passwordEncoder.encode(request.getPassword()));
        u.setFullName(request.getFullName());
        u.setPhone(request.getPhone());
        u.setRole(lk.wedrent.wedrent_backend.entity.Role.ROLE_USER);
        return userRepository.save(u);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
