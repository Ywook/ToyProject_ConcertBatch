package com.ywook.calendar.domain.dto.base;

import com.ywook.calendar.domain.dto.enums.ConcertType;
import lombok.*;

import java.time.LocalDateTime;

/*
    Entity는 Repository에서만
    BaseDto는 Service에서만 사용 하도록 구현
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConcertBaseDto {
    private ConcertType concertType;
    private String concertName;
    private String concertVenue;
    private String concertHall;
    private LocalDateTime conecrtDate;
}
