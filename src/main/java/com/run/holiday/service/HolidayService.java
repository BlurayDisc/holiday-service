package com.run.holiday.service;

import com.run.holiday.dao.HolidayDao;
import com.run.holiday.dto.HolidayDto;
import com.run.holiday.dto.SyncResultDto;
import com.run.holiday.model.Holiday;
import com.run.holiday.model.HolidayType;
import com.run.holiday.model.NationalState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class HolidayService {

  private final HolidayInfoClient holidayInfoClient;

  private final HolidayDao meterDao;

  public Holiday getHoliday(UUID uuid) {
    return meterDao
        .findOne(uuid)
        .orElseThrow(
            () ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No holiday records exist with uuid: " + uuid));
  }

  public List<Holiday> findHolidays(NationalState state, Year year, HolidayType type) {
    return meterDao.findAll(
        state, Optional.ofNullable(year).map(Year::getValue).orElse(null), type);
  }

  public SyncResultDto synchroniseHolidays(NationalState state) {
    if (state != NationalState.WA) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Unsupported state to perform sync: " + state);
    }

    int records = 0;
    List<Holiday> saved = findHolidays(state, null, null);

    log.debug("Listing existing holidays");
    saved.forEach(h -> log.debug("{}", h));

    try {
      List<HolidayDto> holidayDtos = holidayInfoClient.retrieveHolidayData();

    } catch (RuntimeException e) {
      log.error("Error occurred during sync for {}", state, e);
      return new SyncResultDto(records, "error");
    }

    return records == 0
        ? new SyncResultDto(records, "none")
        : new SyncResultDto(records, "success");
  }
}
