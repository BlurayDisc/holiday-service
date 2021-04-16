package com.run.holiday.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@Accessors(chain = true)
public class Holiday {

  @Schema(description = "Unique id (version 4 uuid)")
  @JsonProperty(value = "uuid", access = READ_ONLY)
  private UUID uuid;

  @Schema(description = "One of Australia's national state", example = "WA")
  @JsonProperty(value = "nationalState", access = READ_ONLY)
  private NationalState nationalState;

  @JsonIgnore private HolidayType type;

  @Schema(description = "The holiday's name", example = "New Year's Day")
  @JsonProperty(value = "name", access = READ_ONLY)
  private String name;

  @Schema(description = "The year category which this holiday falls into", example = "2021")
  @JsonProperty(value = "year", access = READ_ONLY)
  private int year;

  @Schema(description = "The actual date of the holiday (yyyy-MM-dd)", example = "2021-01-01")
  @JsonProperty(value = "date")
  private LocalDate date;

  @Schema(
      description = "The alternative date of the holiday, if there is a clash with others",
      example = "2021-01-02")
  @JsonProperty(value = "alternativeDate")
  private LocalDate alternativeDate;
}
