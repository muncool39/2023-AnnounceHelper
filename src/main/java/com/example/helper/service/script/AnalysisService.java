package com.example.helper.service.script;


import java.nio.file.Path;
import org.springframework.stereotype.Service;

@Service
public class AnalysisService {

    public String getScriptAnalysisResult(Path testPath){
        //유료 API 사용해 분석
        return "유료 API 사용하면 분석 결과를 얻을 수 있습니다~";
    }
}
