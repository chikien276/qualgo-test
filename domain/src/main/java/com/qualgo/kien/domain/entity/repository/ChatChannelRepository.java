package com.qualgo.kien.domain.entity.repository;

import com.qualgo.kien.domain.entity.ChatChannel;
import java.util.List;

public interface ChatChannelRepository extends BaseRepository<ChatChannel> {
  List<ChatChannel> findAll();
}
