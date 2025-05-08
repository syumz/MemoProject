package com.example.memo.dto;

import com.example.memo.entity.Memo;
import lombok.Getter;

@Getter
public class MemoResponseDto { // 요청받은 데이터

    // 1. 속성
    private Long id; // 식별자 (안전하게 다루기 위해 Long 타입으로 선언)
    private String title; // 제목
    private String contents; // 내용

    // 2. 생성자
    public MemoResponseDto(Memo memo){
        this.id = memo.getId();
        this.title = memo.getTitle();
        this.contents = memo.getContents();
    }
}
