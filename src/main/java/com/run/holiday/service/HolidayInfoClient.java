package com.run.holiday.service;

import com.run.holiday.dto.HolidayDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class HolidayInfoClient {

  @Value("${holiday-info-service.base-url}")
  private String holidayInfoBaseUrl;

  public List<HolidayDto> retrieveHolidayData() {
    List<HolidayDto> holidayDtos = new ArrayList<>();
    try {
      Document doc = Jsoup.connect(holidayInfoBaseUrl).get();

      log.debug("Sync result: {}", holidayDtos);
    } catch (IOException ioe) {
      throw new UncheckedIOException(ioe);
    }
    return holidayDtos;
  }
}
