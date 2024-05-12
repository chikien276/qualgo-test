package com.qualgo.kien.domain.entity.repository;

import com.qualgo.kien.domain.entity.ChannelMessage;
import java.util.List;

public interface ChannelMessageRepository extends BaseRepository<ChannelMessage> {
  void deleteById(Long id);
  List<ChannelMessage> selectPage(Long channelId, Long minimumId, int pageSize);
}
