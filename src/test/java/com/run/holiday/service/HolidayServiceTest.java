package com.run.holiday.service;

import com.run.holiday.dao.HolidayDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = HolidayService.class)
public class HolidayServiceTest {

  @Autowired private HolidayService sut;
  @MockBean private HolidayInfoClient holidayInfoClient;
  @MockBean private HolidayDao holidayDao;

  @Test
  public void demonstrateSuccessfulSynchroniseHolidays() {}
}
