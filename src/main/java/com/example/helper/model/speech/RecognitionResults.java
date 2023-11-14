package com.example.helper.model.speech;

import lombok.Data;

@Data
public class RecognitionResults {
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
