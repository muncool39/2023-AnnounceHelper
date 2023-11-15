package com.example.helper.controller;


import com.example.helper.controller.response.AudioAnalysisResponse;
import com.example.helper.controller.response.PathResponse;
import com.example.helper.model.speech.EvaluationResults;
import com.example.helper.service.speech.EvaluationService;
import com.example.helper.service.speech.RecognitionService;
import com.example.helper.service.FileService;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class SpeechController {

    private final FileService fileService;
    private final RecognitionService recognitionService;
    private final EvaluationService evaluationService;

    @PostMapping("/upload")
    public String fileUpload(@RequestParam("audioFile") MultipartFile audioFile,
                             @RequestParam("textFile") MultipartFile textFile,
                             @RequestParam("language") String language,
                             RedirectAttributes redirectAttributes,
                             Model model){
        Path audioPath = fileService.uploadAudioFile(audioFile);
        Path textPath = fileService.uploadTextFile(textFile);
        PathResponse filePath = PathResponse.builder()
                .audioPath(audioPath)
                .textPath(textPath)
                .build();
        redirectAttributes.addFlashAttribute("message", "파일 업로드에 성공했습니다!");
        redirectAttributes.addFlashAttribute("filePath",filePath);
        redirectAttributes.addFlashAttribute("language",language);
        return "redirect:/speech";
    }

    @PostMapping("/analysis")
    public String fileAnalysis(@RequestParam("audioPath")Path audioPath,
                               @RequestParam("textPath")Path textPath,
                               @RequestParam("language")String language,
                               RedirectAttributes redirectAttributes){
        String recognitionResult = recognitionService.getAudioResult(audioPath, language);
        String textContent = fileService.getTextFileContent(textPath);
        Double evaluationResult = evaluationService.getPronunciationAnalysisScore(audioPath, language, textContent);
        AudioAnalysisResponse result = AudioAnalysisResponse.builder()
                .originalScript(textContent)
                .speechRecognitionResult(recognitionResult)
                .pronunciationEvaluationScore(Math.round(evaluationResult*1000)/1000.0)
                .build();
        redirectAttributes.addFlashAttribute("message", "파일 분석에 성공했습니다!");
        redirectAttributes.addFlashAttribute("result",result);
        return "redirect:/speech";
    }
}
