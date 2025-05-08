package com.example.memo.controller;

import com.example.memo.dto.MemoRequestDto;
import com.example.memo.dto.MemoResponseDto;
import com.example.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController //Json 형태
@RequestMapping("/memos")
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();

    // 메모를 생성하는 메서드
    @PostMapping
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto dto){
        // 식별자가 1씩 증가 하도록 만들어줘야한다.
        // memoList가 비어있다면 memoId에 1을 넣고 아니면 memoList에 있는 키값 중 제일 큰 값에 +1을 한 값을 넣는다.
        Long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1;

        // 요청받은 데이터로 Memo 객체 생성
        Memo memo = new Memo(memoId, dto.getTitle(), dto.getContents());

        // Inmemory DB에 Memo 메모
        memoList.put(memoId, memo);

        return new MemoResponseDto(memo);
    }

    // 메모를 조회하는 메서드
    @GetMapping("/{id}")
    public MemoResponseDto findMemoById(@PathVariable Long id){

        Memo memo = memoList.get(id);

        return new MemoResponseDto(memo);
    }

    // 메모를 수정하는 메서드
    @PutMapping("/{id}")
    public MemoResponseDto updateMemoById(@PathVariable Long id, @RequestBody MemoRequestDto dto){
        Memo memo = memoList.get(id);

        memo.update(dto);

        return new MemoResponseDto(memo);
    }

    // 메모를 삭제하는 메서드
    @DeleteMapping("/{id}")
    public void deleteMemo(@PathVariable Long id){

        memoList.remove(id);
    }
}
