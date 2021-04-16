package com.run.holiday.service;

import com.run.holiday.dto.HolidayDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
      Element table = doc.select("table").get(2);
      Elements headerColumns = table.select("thead > tr > th");

      String year1 = normalise(headerColumns.get(1));
      String year2 = normalise(headerColumns.get(2));
      String year3 = normalise(headerColumns.get(3));

      log.trace("holiday years: {} {} {}", year1, year2, year3);

      Elements rows = table.select("tbody > tr");
      log.trace("holiday table rows: {}", rows);

      for (int i = 0; i < rows.size(); i++) {
        Element row = rows.get(i);
        Element nameElement = row.selectFirst("th");
        Elements cols = row.select("td");

        String name = normalise(nameElement);
        var date1 = singleOrSplit(normalise(cols.get(0)));
        var date2 = singleOrSplit(normalise(cols.get(1)));
        var date3 = singleOrSplit(normalise(cols.get(2)));

        log.trace("row {} name {}", i, name);
        log.trace("col {} data {} {} {}", i, date1, date2, date3);

        holidayDtos.add(new HolidayDto(year1, name, date1.getLeft(), date1.getRight()));
        holidayDtos.add(new HolidayDto(year2, name, date2.getLeft(), date2.getRight()));
        holidayDtos.add(new HolidayDto(year3, name, date3.getLeft(), date3.getRight()));
      }
    } catch (IOException ioe) {
      throw new UncheckedIOException(ioe);
    }
    return holidayDtos;
  }

  private String normalise(Element element) {
    return element.text().replaceAll("#", "").replaceAll("\u00a0", "").trim();
  }

  private Pair<String, String> singleOrSplit(String date) {
    Pair<String, String> result;
    if (date.contains("&")) {
      String[] parts = date.split("&");
      result = Pair.of(parts[0].trim(), parts[1].trim());
    } else {
      return Pair.of(date, null);
    }

    return result;
  }
}
