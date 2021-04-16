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

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HolidayService {

  private final HolidayInfoClient holidayInfoClient;
  private final HolidayDao holidayDao;

  public Holiday getHoliday(UUID uuid) {
    return holidayDao
        .findOne(uuid)
        .orElseThrow(
            () ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No holiday records exist with uuid: " + uuid));
  }

  public List<Holiday> findHolidays(NationalState state, Year year, HolidayType type) {
    return holidayDao.findAll(
        state, Optional.ofNullable(year).map(Year::getValue).orElse(null), type);
  }

  public SyncResultDto synchroniseHolidays(NationalState state) {
    if (state != NationalState.WA) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Unsupported state to perform sync: " + state);
    }

    try {
      List<HolidayDto> holidayDtos = holidayInfoClient.retrieveHolidayData();

      log.debug("Listing fetched holidays");
      holidayDtos.forEach(h -> log.debug("{}", h));

      holidayDao.deleteAllByState(state);
      holidayDao.saveAll(
          holidayDtos.stream()
              .map(
                  dto ->
                      new Holiday()
                          .setUuid(UUID.randomUUID())
                          .setNationalState(state)
                          .setName(dto.getName())
                          .setType(HolidayType.resolveFromName(dto.getName()))
                          .setYear(Integer.parseInt(dto.getYear()))
                          .setDate(parseHolidayInfoApiDate(dto.getYear(), dto.getDate()))
                          .setAlternativeDate(
                              Optional.ofNullable(dto.getAlternativeDate())
                                  .map(altDate -> parseHolidayInfoApiDate(dto.getYear(), altDate))
                                  .orElse(null)))
              .collect(Collectors.toList()));

      int records = holidayDtos.size();
      return records == 0
          ? new SyncResultDto(records, "none")
          : new SyncResultDto(records, "success");

    } catch (RuntimeException e) {
      log.error("Error occurred during sync for {}", state, e);
      return new SyncResultDto(0, "error");
    }
  }

  private static final DateTimeFormatter HOLIDAY_INFO_API_DATE_FORMAT =
      DateTimeFormatter.ofPattern("yyyy dd MMM");

  private LocalDate parseHolidayInfoApiDate(String year, String date) {
    try {
      String[] dateParts = date.split("\\s+", 1);

      return LocalDate.parse(
          String.format("%s %s", year, dateParts[1]), HOLIDAY_INFO_API_DATE_FORMAT);
    } catch (Error e) {
      log.info(date);
      log.info(date.split("\\s+", 1)[0]);
      log.info(date.split("\\s+", 1)[1]);
      throw e;
    }
  }
}
