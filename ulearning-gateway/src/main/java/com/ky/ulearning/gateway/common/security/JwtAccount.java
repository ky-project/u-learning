package com.ky.ulearning.gateway.common.security;

import com.ky.ulearning.spi.common.dto.UserContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 登录账号信息
 *
 * @author luyuhao
 * @date 19/12/06 21:30
 */
@Data
@Accessors(chain = true)
public class JwtAccount extends UserContext implements UserDetails {

    /**
     * 权限集合
     */
    private Collection<GrantedAuthority> authorities;

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
