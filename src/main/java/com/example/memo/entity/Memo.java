package com.example.memo.entity;

import com.example.memo.dto.MemoRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter // 게터 생성
@AllArgsConstructor //매개변수로 받는 생성자 자동 생성
public class Memo {

    private Long id; // 식별자 (안전하게 다루기 위해 Long 타입으로 선언)
    private String title; // 제목
    private String contents; // 내용

    public void update(MemoRequestDto dto){
        this.title = dto.getTitle();
        this.contents = dto.getContents();
    }
}
