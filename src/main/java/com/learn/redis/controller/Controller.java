package com.learn.redis.controller;

import com.learn.redis.dto.CreateUserRequest;
import com.learn.redis.dto.UserResponse;
import com.learn.redis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class Controller {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    UserService userService;

    private static final String STRING_KEY_PREFIX = "MY_APP";

    @GetMapping("getUsers")
    @Cacheable(value = "users", cacheNames = "users")
    public List<UserResponse> getUsers() {
        return userService.findAll();
    }

    @PostMapping("user/add")
    @CacheEvict(cacheNames = "users", allEntries = true)
    public UserResponse addUser(@RequestBody CreateUserRequest createUserRequest) {
        return userService.save(createUserRequest);
    }

    @GetMapping("user/{id}")
    @Cacheable(value = "user", cacheNames = "user", key = "{#id}")
    public UserResponse getUser(@PathVariable(name = "id") Long id) {
        return userService.getUser(id);
    }

    @GetMapping
    public String helloWorld() {
        return "Hello!";
    }

    @GetMapping("/getString")
    public String getString(@RequestParam String key) {
        return (String) redisTemplate.opsForValue().get(STRING_KEY_PREFIX + key);
    }

    @PostMapping("/strings")
    @ResponseStatus(HttpStatus.CREATED)
    public Map.Entry<String, Object> setString(@RequestBody Map.Entry<String, Object> kvp) {
        redisTemplate.opsForValue().set(STRING_KEY_PREFIX + kvp.getKey(), kvp.getValue());
        return kvp;
    }
}
