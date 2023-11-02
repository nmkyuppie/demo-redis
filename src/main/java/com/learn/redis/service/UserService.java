package com.learn.redis.service;

import com.learn.redis.dto.CreateUserRequest;
import com.learn.redis.dto.UserResponse;
import com.learn.redis.entity.User;
import com.learn.redis.repository.RoleRepository;
import com.learn.redis.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ModelMapper modelMapper;

    public UserResponse getUser(Long id) {
        User user =userRepository.findById(id).orElseThrow();
        log.info("user {}", user);
        return modelMapper.map(user, UserResponse.class);
    }

    public List<UserResponse> findAll() {
        log.info("user {}",userRepository.findAll());
        log.info("role {}",roleRepository.findAll());
        return mapList(StreamSupport.stream(userRepository.findAll().spliterator(), false).toList(), UserResponse.class);
    }

    public UserResponse save(CreateUserRequest createUserRequest) {
        User user = User.builder()
                .name(createUserRequest.getName())
                .email(createUserRequest.getEmail())
                .roleId(createUserRequest.getRoleId())
                .build();
        User createdUser = userRepository.save(user);
        return modelMapper.map(userRepository.findById(createdUser.getId()).orElseThrow(), UserResponse.class);
    }

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .toList();
    }
}
