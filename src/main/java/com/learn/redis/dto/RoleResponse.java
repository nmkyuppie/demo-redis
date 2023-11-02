package com.learn.redis.dto;

import com.learn.redis.entity.Scope;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse implements Serializable {
    Long id;
    String name;
    Set<ScopeResponse> scopes;
}
