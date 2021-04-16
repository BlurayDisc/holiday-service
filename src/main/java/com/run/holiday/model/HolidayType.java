package com.run.holiday.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public enum HolidayType {
  NEW_YEARS_DAY("New Year's Day"),
  AUSTRALIA_DAY("Australia Day"),
  LABOUR_DAY("Labour Day"),
  GOOD_FRIDAY("Good Friday"),
  EASTER_MONDAY("Easter Monday"),
  ANZAC_DAY("Anzac Day"),
  WESTERN_AUSTRALIA_DAY("Western Australia Day"),
  QUEENS_BIRTHDAY("Queen's Birthday"),
  CHRISTMAS_DAY("Christmas Day"),
  BOXING_DAY("Boxing Day");

  @Getter private final String name;

  public static HolidayType resolveFromName(String name) {
    for (var t : HolidayType.values()) {
      if (Objects.equals(t.name, name)) return t;
    }
    throw new IllegalStateException("Unable to resolve name: " + name);
  }
}
