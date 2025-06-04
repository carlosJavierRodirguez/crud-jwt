package com.example.crud_jwt.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.crud_jwt.model.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryRequestDTO {
    private int id;
    private String token;
    private boolean isUsed;
    private LocalDateTime expiresAt;
    private User user;
}
