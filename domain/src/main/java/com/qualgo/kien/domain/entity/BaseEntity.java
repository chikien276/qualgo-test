package com.qualgo.kien.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity implements Serializable {
  @TableField(fill = FieldFill.INSERT)
  Instant createTime;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  Instant updateTime;

  //TODO: adding optimistic lock
  int version;
}
