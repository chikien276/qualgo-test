package com.qualgo.kien.infrastructure.web.config;

import com.qualgo.kien.domain.entity.UserInfo;
import com.qualgo.kien.domain.entity.repository.UserInfoRepository;
import com.qualgo.kien.infrastructure.web.filter.MyUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
  final UserInfoRepository userInfoRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserInfo userInfo = userInfoRepository.findByUserName(username);
    if (userInfo == null) {
      throw new UsernameNotFoundException("User detail not found for " + username);
    }
    return new MyUserDetail(userInfo);
  }
}
