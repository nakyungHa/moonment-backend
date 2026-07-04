package com.moonment.enums;

public enum Goal {
    기록_습관화("기록 습관화"),
    주5회_이상_기록("주 5회 이상 기록"),
    감정_이해("감정 이해"),
    자기_성찰("자기 성찰"),
    스트레스_관리("스트레스 관리"),
    일상_기록("일상 기록");

    private final String value;

    Goal(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
