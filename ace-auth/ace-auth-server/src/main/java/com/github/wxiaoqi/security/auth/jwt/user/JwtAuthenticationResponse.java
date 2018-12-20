

package com.github.wxiaoqi.security.auth.jwt.user;

import com.github.wxiaoqi.security.common.constant.RequestHeaderConstants;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {
    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;

    public JwtAuthenticationResponse(String token) {

        this.token = RequestHeaderConstants.JWT_TOKEN_TYPE + token;
    }

    public String getToken() {
        return this.token;
    }
}
