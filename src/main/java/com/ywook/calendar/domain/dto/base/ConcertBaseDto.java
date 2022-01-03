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
@ToString
public class ConcertBaseDto {
    private String concertVenue;     //공연,전시구분
    private ConcertType concertType; //장르
    private String concertName;      //공연,전시명
    private LocalDateTime concertDate;//기간
    private String concertHall;     //콘서트 홀
}
