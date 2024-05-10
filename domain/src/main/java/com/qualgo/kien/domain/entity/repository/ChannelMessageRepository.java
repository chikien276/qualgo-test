package com.qualgo.kien.domain.entity.repository;

import com.qualgo.kien.domain.entity.ChannelMessage;
import com.qualgo.kien.domain.entity.ChatChannel;
import java.util.List;

public interface ChannelMessageRepository extends BaseRepository<ChannelMessage> {
  void deleteById(Long id);
}
