package com.ets.filemanager.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDto implements Serializable {
    private Long id;
    private String username;
    private String token;
}
