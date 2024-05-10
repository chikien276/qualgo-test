package com.qualgo.kien.infrastructure.persistent;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

  @Override
  public void insertFill(MetaObject metaObject) {
    this.strictInsertFill(metaObject, "updateTime", Instant::now, Instant.class);
    this.strictInsertFill(metaObject, "createTime", Instant::now, Instant.class);
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    this.strictUpdateFill(metaObject, "updateTime", Instant::now, Instant.class);
    this.strictUpdateFill(metaObject, "createTime", Instant::now, Instant.class);
  }
}
