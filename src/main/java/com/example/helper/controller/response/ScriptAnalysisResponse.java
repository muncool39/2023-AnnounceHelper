package com.example.helper.controller.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ScriptAnalysisResponse {
    private String script;
    private String result;
}
