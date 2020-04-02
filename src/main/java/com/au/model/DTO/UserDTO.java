package com.au.model.DTO;

import lombok.Data;

/**
 * @author: huzhijin
 * @create: 2020/4/2 10:53 上午
 **/
@Data
public class UserDTO {
    private Integer id;

    private String username;

    private String phone;

    private Integer sex;

    private String token;
}
