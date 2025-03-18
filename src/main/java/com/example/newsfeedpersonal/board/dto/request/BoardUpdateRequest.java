package com.example.newsfeedpersonal.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardUpdateRequest {

    @NotBlank
    @Size(min = 1, max = 30, message = "게시글 제목은 최소 1자부터 최대 30자입니다.")
    private String title;

    @NotBlank
    @Size(min = 1, max = 1000, message = "게시글 내용은 최소 1자부터 최대 1000자입니다.")
    private String content;
}
