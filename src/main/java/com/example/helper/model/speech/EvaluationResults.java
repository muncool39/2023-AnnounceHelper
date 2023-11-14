package com.example.helper.model.speech;

import lombok.Data;

@Data
public class EvaluationResults {
    private int result;
    private String return_type;
    private ReturnObject return_object;
    public static class ReturnObject{
        private String recognized;
        private String score;
        public String getRecognized(){
            return recognized;
        }
        public String getScore(){
            return score;
        }
    }
}
