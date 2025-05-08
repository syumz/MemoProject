package com.example.memo.controller;

import com.example.memo.dto.MemoRequestDto;
import com.example.memo.dto.MemoResponseDto;
import com.example.memo.entity.Memo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController //Json 형태
@RequestMapping("/memos")
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();

    // 메모를 생성하는 메서드
    @PostMapping
    public ResponseEntity<MemoResponseDto> createMemo(@RequestBody MemoRequestDto dto){

        // 식별자가 1씩 증가 하도록 만들어줘야한다.
        // memoList가 비어있다면 memoId에 1을 넣고 아니면 memoList에 있는 키값 중 제일 큰 값에 +1을 한 값을 넣는다.
        Long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1;

        // 요청받은 데이터로 Memo 객체 생성
        Memo memo = new Memo(memoId, dto.getTitle(), dto.getContents());

        // Inmemory DB에 Memo 메모
        memoList.put(memoId, memo);

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.CREATED);
    }

    @GetMapping
    public List<MemoResponseDto> findAllMemos(){
        // 리스트 초기화
        List<MemoResponseDto> responseList = new ArrayList<>();

        // HashMap<Memo> -> List<MemoResponseDto>
        for(Memo memo : memoList.values()){
            MemoResponseDto responseDto = new MemoResponseDto(memo);
            responseList.add(responseDto);
        }
        // Map To List 위와 같은 코드
//        responseList = memoList.values().stream().map(MemoResponseDto::new).toList();

        return responseList;
    }

    // 메모를 조회하는 메서드
    @GetMapping("/{id}")
    public ResponseEntity<MemoResponseDto> findMemoById(@PathVariable Long id){

        Memo memo = memoList.get(id);

        if(memo == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK);
    }

    // 메모를 수정하는 메서드
    @PutMapping("/{id}")
    public ResponseEntity<MemoResponseDto> updateMemoById(@PathVariable Long id, @RequestBody MemoRequestDto dto){
        Memo memo = memoList.get(id);

        if(memo == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(dto.getTitle() == null || dto.getContents() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        memo.update(dto);

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MemoResponseDto> updateTitle(@PathVariable Long id, @RequestBody MemoRequestDto dto){
        Memo memo = memoList.get(id);

        // NPE 방지
        if(memo == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(dto.getTitle() == null || dto.getContents() != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        memo.updateTitle(dto);

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK);
    }

    // 메모를 삭제하는 메서드
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemo(@PathVariable Long id){

        // memoList에 Key값에 id를 포함하고 있다면
        if(memoList.containsKey(id)){
            memoList.remove(id);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
