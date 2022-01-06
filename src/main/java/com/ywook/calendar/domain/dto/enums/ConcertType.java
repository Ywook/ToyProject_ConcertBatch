package com.ywook.calendar.domain.dto.enums;

public enum ConcertType {
    CHAMBER("실내악"),
    CHORUS("합창"),
    CLASSIC("클래식"),
    COMPLEX("복합장르"),
    ETC("기타"),
    EVENT("이벤트 콘서트"),
    ORCHESTRA("관현악"),
    SOLO("독주"),
    SYMPHONY("교향곡"),
    VOCAL("성악");

    private final String message;

    ConcertType(String message) {
        this.message = message;
    }

    public String getText() {
        return this.message;
    }

    public static ConcertType fromString(String text) {
        for (ConcertType type : ConcertType.values()) {
            if(type.message.equalsIgnoreCase(text))
            return type;
        }
        throw new IllegalArgumentException("No constant with text : " + text);
    }
}
