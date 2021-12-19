package com.ywook.calendar.domain.entity;

import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="CONECRT_CALENDAR")
public class ConcertEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ID")
        private Long id;

        @Column(name = "CONCERT_TYPE")
        private String concertType;

        @Column(name = "CONCERT_NAME")
        private String concertName;

        @Column(name = "CONCERT_VENUE")
        private String concertVenue;

        @Column(name = "CONCERT_HALL")
        private String concertHall;

        @Column(name = "CONCERT_DATE")
        private LocalDateTime CONCERT_DATE;

        @Version
        @Column(name = "ENTITY_VERSION")
        private Long version;

        @Column(name = "CREATED_AT")
        private LocalDateTime createAt;

        @Column(name = "UPDATED_AT")
        private LocalDateTime updatedAt;

}
