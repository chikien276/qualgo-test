package com.qualgo.kien.infrastructure.persistent.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qualgo.kien.domain.entity.ChatChannel;
import com.qualgo.kien.domain.entity.repository.ChatChannelRepository;
import com.qualgo.kien.infrastructure.persistent.mapper.ChatChannelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatChannelRepositoryImpl implements ChatChannelRepository {
  final ChatChannelMapper mapper;

  @Override
  public ChatChannel findById(Long id) {
    return mapper.selectById(id);
  }

  @Override
  public int update(ChatChannel entity) {
    return mapper.updateById(entity);
  }

  @Override
  public int insert(ChatChannel entity) {
    return mapper.insert(entity);
  }

  @Override
  public List<ChatChannel> findAll() {
    return mapper.selectList(new LambdaQueryWrapper<>());
  }
}
