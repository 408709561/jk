/*
 *
 *  *  Copyright (C) 2018  Wanghaobin<463540703@qq.com>
 *
 *  *  AG-Enterprise 企业版源码
 *  *  郑重声明:
 *  *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  *  老A将追究授予人和传播人的法律责任!
 *
 *  *  This program is free software; you can redistribute it and/or modify
 *  *  it under the terms of the GNU General Public License as published by
 *  *  the Free Software Foundation; either version 2 of the License, or
 *  *  (at your option) any later version.
 *
 *  *  This program is distributed in the hope that it will be useful,
 *  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *  GNU General Public License for more details.
 *
 *  *  You should have received a copy of the GNU General Public License along
 *  *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */

package com.github.wxiaoqi.security.app.oauth.config;

import com.github.ag.core.constants.CommonConstants;
import com.github.ag.core.util.RsaKeyHelper;
import com.github.wxiaoqi.security.app.oauth.bean.OauthUser;
import com.github.wxiaoqi.security.app.oauth.service.OauthUserDetailsService;
import com.github.wxiaoqi.security.common.constant.RedisKeyConstants;
import com.github.wxiaoqi.security.common.util.Sha256PasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import sun.security.rsa.RSAPrivateCrtKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;
import sun.security.util.SecurityConstants;

import javax.sql.DataSource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class OAuthSecurityConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager auth;

    @Autowired
    private AuthServerConfig authServerConfig;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RsaKeyHelper rsaKeyHelper;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;


    @Bean
    public RedisTokenStore redisTokenStore() {
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        redisTokenStore.setPrefix("AG:OAUTH:");
        return redisTokenStore;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security)
            throws Exception {
        security                .allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
        //需要更换成加密模式
//        security.passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        endpoints
                .authenticationManager(auth)
                .tokenStore(redisTokenStore()).accessTokenConverter(accessTokenConverter())
        ;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
        //需要更换成加密模式
        clients.inMemory()
                .withClient(authServerConfig.getClientId())
                .secret(authServerConfig.getClientSecret())
                .authorizedGrantTypes(authServerConfig.getGrantTypes().split(","))
                // 访问token过期时长为一周过期
                .accessTokenValiditySeconds(7*24*60*60)
                // 刷新token过期时长为两个月
                .refreshTokenValiditySeconds(60*24*60*60)
                .scopes(authServerConfig.getScope());
    }


    @Configuration
    @Order(100)
    protected static class AuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {
        @Autowired
        private OauthUserDetailsService oauthUserDetailsService;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(oauthUserDetailsService).passwordEncoder(new Sha256PasswordEncoder());
        }
    }


    @Bean
    public JwtAccessTokenConverter accessTokenConverter() throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        byte[] pri, pub = null;
        try {
            pri = rsaKeyHelper.toBytes(redisTemplate.opsForValue().get(RedisKeyConstants.REDIS_USER_PRI_KEY).toString());
            pub = rsaKeyHelper.toBytes(redisTemplate.opsForValue().get(RedisKeyConstants.REDIS_USER_PUB_KEY).toString());
        } catch (Exception e) {
            log.error("初始化用户认证公钥/密钥异常...", e);
            throw new RuntimeException("redis异常 或 未启动auth 服务,并保证app和auth处于同一个redis集群当中...");
        }
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter() {
            /***
             * 重写增强token方法,用于自定义一些token返回的信息
             */
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                OauthUser user = (OauthUser) authentication.getUserAuthentication().getPrincipal();
                /** 自定义一些token属性 ***/
                final Map<String, Object> additionalInformation = new HashMap<>();
                Date expireTime = DateTime.now().plusSeconds(accessToken.getExpiresIn()).toDate();
                additionalInformation.put(CommonConstants.JWT_KEY_EXPIRE, expireTime);
                additionalInformation.put(CommonConstants.JWT_KEY_USER_ID, user.getId());
                additionalInformation.put(CommonConstants.JWT_KEY_TENANT_ID, user.getTenantId());
                additionalInformation.put(CommonConstants.JWT_KEY_NAME, user.getName());
                additionalInformation.put("sub", user.getUsername());
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
                OAuth2AccessToken enhancedToken = super.enhance(accessToken, authentication);
                return enhancedToken;
            }

        };
        accessTokenConverter.setKeyPair(new KeyPair(new RSAPublicKeyImpl(pub), RSAPrivateCrtKeyImpl.newKey(pri)));
        return accessTokenConverter;
    }
}