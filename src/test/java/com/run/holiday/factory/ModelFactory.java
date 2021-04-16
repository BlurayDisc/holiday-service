package com.run.holiday.factory;

import com.run.holiday.model.Holiday;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelFactory {

  public static Holiday createHoliday() {
    UUID uuid = UUID.randomUUID();
    Holiday holiday = new Holiday();
    holiday.setUuid(uuid);
    return holiday;
  }
}
