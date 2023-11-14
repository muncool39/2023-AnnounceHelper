package com.example.helper.controller;


import com.example.helper.controller.response.AudioAnalysisResponse;
import com.example.helper.service.speech.EvaluationService;
import com.example.helper.service.speech.RecognitionService;
import com.example.helper.service.speech.FileService;
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
    public String fileUpload(@RequestParam("file") MultipartFile file,
                             @RequestParam("language") String language,
                             RedirectAttributes redirectAttributes,
                             Model model){
        fileService.uploadAudioFile(file);
        Path path = fileService.getFilePath(file);
        redirectAttributes.addFlashAttribute("message", "파일 업로드에 성공했습니다!");
        redirectAttributes.addFlashAttribute("filePath",path);
        redirectAttributes.addFlashAttribute("language",language);
        return "redirect:/speech";
    }

    @PostMapping("/analysis")
    public String fileAnalysis(@RequestParam("filePath")Path path,
                               @RequestParam("language")String language,
                               RedirectAttributes redirectAttributes){
        String recognitionResult = recognitionService.getAudioResult(path, language);
        String evaluationResult = evaluationService.getPronunciationAnalysisResults(path, language, recognitionResult);
        AudioAnalysisResponse result = AudioAnalysisResponse.builder()
                .speechRecognitionResult(recognitionResult)
                .pronunciationEvaluationScore(evaluationResult)
                .build();
        redirectAttributes.addFlashAttribute("message", "파일 분석에 성공했습니다!");
        redirectAttributes.addFlashAttribute("result",result);
        return "redirect:/speech";
    }
}
