package com.qualgo.kien.infrastructure.persistent;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes({Instant.class})
@MappedJdbcTypes({JdbcType.BIGINT})
public class InstantTypeHandler extends BaseTypeHandler<Instant> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Instant parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setLong(i, parameter.toEpochMilli());
  }

  @Override
  public Instant getNullableResult(ResultSet rs, String columnName) throws SQLException {
    long result = rs.getLong(columnName);
    return result == 0 && rs.wasNull() ? null : Instant.ofEpochMilli(result);
  }

  @Override
  public Instant getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    long result = rs.getLong(columnIndex);
    return result == 0 && rs.wasNull() ? null : Instant.ofEpochMilli(result);
  }

  @Override
  public Instant getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    long result = cs.getLong(columnIndex);
    return result == 0 && cs.wasNull() ? null : Instant.ofEpochMilli(result);
  }
}
