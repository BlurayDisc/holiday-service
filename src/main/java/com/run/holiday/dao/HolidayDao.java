package com.run.holiday.dao;

import com.run.holiday.model.Holiday;
import com.run.holiday.model.HolidayType;
import com.run.holiday.model.NationalState;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.BatchChunkSize;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RegisterBeanMapper(Holiday.class)
public interface HolidayDao {

  @SqlQuery("select * from holiday where uuid = :uuid")
  Optional<Holiday> findOne(UUID uuid);

  @SqlQuery(
      "select * from holiday "
          + "where (:state is null or state = :state) "
          + "and (:year is null or year = :year) "
          + "and (:type is null or type = :type) ")
  List<Holiday> findAll(NationalState state, Integer year, HolidayType type);

  @SqlUpdate(
      "insert into holiday(uuid, state, type, year, date, alternative_date) "
          + "values (:uid, :nationalState, :type, :year, :date, :alternativeDate)")
  void save(@BindBean Holiday holiday);

  @SqlBatch(
      "insert into holiday(uuid, state, type, year, date, alternative_date) "
          + "values (:uuid, :nationalState, :type, :year, :date, :alternativeDate)")
  @BatchChunkSize(10)
  void saveAll(@BindBean List<Holiday> meters);

  @SqlUpdate("delete from holiday where state = :state")
  void deleteAllByState(NationalState state);
}
