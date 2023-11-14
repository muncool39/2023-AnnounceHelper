package com.example.helper.model;

import lombok.Data;

@Data
public class AudioAnalysisResult {
    private int result;
    private String return_type;
    private ReturnObject return_object;

    public static class ReturnObject{
        private String recognized;
        public String getRecognized(){
            return recognized;
        }
    }
}
