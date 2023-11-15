package com.example.helper.controller.response;


import java.nio.file.Path;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PathResponse {
    Path audioPath;
    Path textPath;
}
