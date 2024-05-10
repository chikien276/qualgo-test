package com.qualgo.kien.infrastructure.web.filter;

import com.qualgo.kien.domain.entity.UserInfo;
import java.util.Collection;
import java.util.List;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record MyUserDetail(@NonNull UserInfo object) implements UserDetails {
  public Long getUserId() {
    return object.getId();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override
  public String getPassword() {
    return object.getPassword();
  }

  @Override
  public String getUsername() {
    return object.getUsername();
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
