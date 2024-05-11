package com.qualgo.kien.infrastructure.persistent.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qualgo.kien.domain.entity.ChannelMessage;
import com.qualgo.kien.domain.entity.repository.ChannelMessageRepository;
import com.qualgo.kien.infrastructure.persistent.mapper.ChannelMessageMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelMessageRepositoryImpl implements ChannelMessageRepository {
  final ChannelMessageMapper mapper;

  @Override
  public ChannelMessage findById(Long id) {
    return mapper.selectById(id);
  }

  @Override
  public int update(ChannelMessage entity) {
    return mapper.updateById(entity);
  }

  @Override
  public int insert(ChannelMessage entity) {
    return mapper.insert(entity);
  }

  @Override
  public void deleteById(Long id) {
    mapper.deleteById(id);
  }

  @Override
  public List<ChannelMessage> selectPage(Long channelId, Long minimumId, int pageSize) {
    LambdaQueryWrapper<ChannelMessage> query = new LambdaQueryWrapper<>();
    query.eq(ChannelMessage::getChatChannelId, channelId);
    if (minimumId != null) {
      query.lt(ChannelMessage::getId, minimumId);
    }
    query.orderByDesc(ChannelMessage::getId).last(" limit " + pageSize);
    return mapper.selectList(query);
  }
}
