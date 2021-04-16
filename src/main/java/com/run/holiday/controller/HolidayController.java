package com.run.holiday.controller;

import com.run.holiday.dto.SyncResultDto;
import com.run.holiday.model.Holiday;
import com.run.holiday.model.HolidayType;
import com.run.holiday.model.NationalState;
import com.run.holiday.service.HolidayService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/holidays")
public class HolidayController {

  private final HolidayService holidayService;

  @GetMapping("/{uuid}")
  public Holiday getHoliday(@PathVariable UUID uuid) {
    log.info("Received request to get holiday with id {}", uuid);
    return holidayService.getHoliday(uuid);
  }

  @GetMapping
  public List<Holiday> findHolidays(
      @RequestParam(required = false) NationalState state,
      @Schema(type = "string") @RequestParam(required = false) Year year,
      @RequestParam(required = false) HolidayType type) {
    log.info(
        "Received request to find holidays for state {} in year {} with type {}",
        state,
        year,
        type);
    return holidayService.findHolidays(state, year, type);
  }

  @PostMapping("/actions/sync")
  public SyncResultDto synchroniseHolidays(@RequestParam NationalState state) {
    log.info("Received request to synchronise national holidays for state: {} ", state);
    return holidayService.synchroniseHolidays(state);
  }
}
