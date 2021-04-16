package com.run.holiday.mapper;

import com.run.holiday.model.Holiday;
import com.run.holiday.model.HolidayType;
import com.run.holiday.model.NationalState;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class HolidayMapper implements RowMapper<Holiday> {

  @Override
  public Holiday map(ResultSet rs, StatementContext ctx) throws SQLException {

    return new Holiday()
        .setUuid(UUID.fromString(rs.getString("uuid")))
        .setNationalState(NationalState.valueOf(rs.getString("state")))
        .setType(HolidayType.valueOf(rs.getString("type")))
        .setName(HolidayType.valueOf(rs.getString("type")).getName())
        .setYear(rs.getInt("year"))
        .setDate(rs.getDate("date").toLocalDate())
        .setAlternativeDate(
            Optional.ofNullable(rs.getDate("alternative_date"))
                .map(Date::toLocalDate)
                .orElse(null));
  }
}
