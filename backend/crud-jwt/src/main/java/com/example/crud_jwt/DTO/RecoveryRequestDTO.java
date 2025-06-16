package com.example.crud_jwt.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryRequestDTO {
    private int id;
    private String token;
    private boolean isUsed;
    private LocalDateTime expiresAt;
    private Integer userId;
    
}