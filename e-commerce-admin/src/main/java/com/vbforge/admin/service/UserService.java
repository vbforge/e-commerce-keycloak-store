package com.vbforge.admin.service;

import com.vbforge.admin.dto.UserDto;
import com.vbforge.admin.entity.User;
import com.vbforge.admin.exception.AdminException;
import com.vbforge.admin.exception.ResourceNotFoundException;
import com.vbforge.admin.mapper.UserMapper;
import com.vbforge.admin.repository.OrderRepository;
import com.vbforge.admin.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       OrderRepository orderRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userMapper.toDtoList(
            userRepository.findAll(),
            user -> orderRepository.countByStatus(null) >= 0
                ? orderRepository.findAll().stream()
                    .filter(o -> o.getUser().getId().equals(user.getId()))
                    .count()
                : 0L
        );
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        long orderCount = orderRepository.findAll().stream()
            .filter(o -> o.getUser().getId().equals(id))
            .count();
        return userMapper.toDto(user, orderCount);
    }

    public void toggleEnabled(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        user.setEnabled(!user.getEnabled());
        userRepository.save(user);
    }

    public void delete(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        boolean isAdmin = user.getRoles().stream()
            .anyMatch(r -> r.getName().name().equals("ROLE_ADMIN"));
        if (isAdmin) {
            throw new AdminException("Admin users cannot be deleted through this interface");
        }
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long count() { return userRepository.count(); }
}