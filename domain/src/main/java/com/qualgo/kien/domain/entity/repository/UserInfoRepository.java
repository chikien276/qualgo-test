package com.qualgo.kien.domain.entity.repository;

import com.qualgo.kien.domain.entity.UserInfo;

public interface UserInfoRepository extends BaseRepository<UserInfo> {
  UserInfo findByUserName(String userName);
}
