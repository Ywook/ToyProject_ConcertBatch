package com.ywook.calendar.domain.dto.enums;

public enum ConcertType {
    CLASSIC("클래식"),
    SYMPHONY("교향곡"),
    SOLO("솔로"),
    VOCAL("성악"),
    COMPLEX("복합 장르"),
    EVENT("이벤트 콘서트"),
    CHORUS("합창"),
    ETC("기타");

    private final String message;

    ConcertType(String message) {
        this.message = message;
    }
}
