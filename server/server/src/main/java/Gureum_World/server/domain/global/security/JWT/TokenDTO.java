package Gureum_World.server.domain.global.security.JWT;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TokenDTO {

    private String types;
    private String token;
    private Date tokenExpiresTime;
}