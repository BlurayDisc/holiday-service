package com.run.holiday.dto;

import lombok.Value;

@Value
public class HolidayDto {
  String year;
  String name;
  String date;
  String alternativeDate;
}
