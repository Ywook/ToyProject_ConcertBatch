package com.ywook.calendar.repository;

import com.ywook.calendar.domain.dto.base.ConcertBaseDto;
import com.ywook.calendar.domain.dto.enums.ConcertType;
import com.ywook.calendar.domain.entity.ConcertEntity;
import com.ywook.calendar.repository.mapper.ConcertMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConcertRepositoryTest {

    @Autowired
    ConcertRepository concertRepository;

    @Autowired
    ConcertMapper concertMapper;

    @Test
    @Transactional
    public void testConcert() throws Exception {
        //given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ConcertBaseDto concert = ConcertBaseDto
                .builder()
                .concertHall("콘서트홀")
                .concertName("코리안심포니 예술감독 다비트 라일란트 취임연주회 - 빛을 향해")
                .concertType(ConcertType.fromString("관현악"))
                .concertDate(LocalDate.parse("2022-01-05",DateTimeFormatter.ISO_DATE))
                .concertVenue("콘서트홀")
                .build();
        System.out.println(LocalDate.parse("2022-01-05", DateTimeFormatter.ISO_DATE));
        System.out.println(concertMapper.toEntity(concert).getConcertDate());
        //when
        ConcertEntity saveEntity = concertRepository.save(concertMapper.toEntity(concert));
        ConcertEntity findEntity = concertRepository.findById(1L).orElse(null);

        //then
        Assertions.assertThat(saveEntity.getId()).isEqualTo(findEntity.getId());
        Assertions.assertThat(saveEntity.getConcertName()).isEqualTo(findEntity.getConcertName());

    }

}