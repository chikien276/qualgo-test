package com.qualgo.kien.domain.entity;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@KeySequence(dbType = DbType.H2, value = "channel_message_seq")
@NoArgsConstructor
@AllArgsConstructor
public class ChatChannel extends BaseEntity {

  @Setter
  @TableId(type = IdType.AUTO)
  Long id;

  String name;

  Long creatorUserInfoId;
}
