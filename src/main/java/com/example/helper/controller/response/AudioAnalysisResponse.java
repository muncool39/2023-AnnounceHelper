package com.example.helper.controller.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AudioAnalysisResponse {
    private String speechRecognitionResult;
    private String pronunciationEvaluationScore;
}
