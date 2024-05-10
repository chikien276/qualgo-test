package com.qualgo.kien.infrastructure.persistent.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qualgo.kien.domain.entity.UserInfo;
import com.qualgo.kien.domain.entity.repository.UserInfoRepository;
import com.qualgo.kien.infrastructure.persistent.mapper.UserInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfoRepositoryImpl implements UserInfoRepository {
  final UserInfoMapper mapper;

  @Override
  public UserInfo findById(Long id) {
    return mapper.selectById(id);
  }

  @Override
  public int update(UserInfo entity) {
    return mapper.updateById(entity);
  }

  @Override
  public int insert(UserInfo entity) {
    return mapper.insert(entity);
  }

  @Override
  public UserInfo findByUserName(String userName) {
    return mapper.selectOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getUsername, userName));
  }
}
