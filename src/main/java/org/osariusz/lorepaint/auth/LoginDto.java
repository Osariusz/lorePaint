package org.osariusz.lorepaint.auth;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;

    public String getUsername() {
        return username.trim();
    }
}
