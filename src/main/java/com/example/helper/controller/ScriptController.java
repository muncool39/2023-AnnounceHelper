package com.example.helper.controller;


import com.example.helper.controller.response.ScriptAnalysisResponse;
import com.example.helper.service.FileService;
import com.example.helper.service.script.AnalysisService;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ScriptController {

    private final FileService fileService;
    private final AnalysisService analysisService;

    @PostMapping("/script/text")
    public String makeScriptFile(@RequestParam("textContent") String text,
                                 RedirectAttributes redirectAttributes){
        Path path = fileService.writeTextFile(text);
        redirectAttributes.addFlashAttribute("message", "대본 생성에 성공했습니다!");
        redirectAttributes.addFlashAttribute("filePath",path);
        return "redirect:/script";
    }

    @PostMapping("/script/upload")
    public String fileUpload(@RequestParam("file") MultipartFile file,
                             RedirectAttributes redirectAttributes){

        Path path = fileService.uploadTextFile(file);
        redirectAttributes.addFlashAttribute("message", "파일 업로드에 성공했습니다!");
        redirectAttributes.addFlashAttribute("filePath",path);
        return "redirect:/script";
    }

    @PostMapping("/script/analysis")
    public String scriptAnalysis(@RequestParam("filePath")Path path,
                                 RedirectAttributes redirectAttributes ){
        ScriptAnalysisResponse scriptAnalysisResponse = ScriptAnalysisResponse.builder()
                .script(fileService.getTextFileContent(path))
                .result(analysisService.getScriptAnalysisResult(path))
                .build();
        redirectAttributes.addFlashAttribute("result", scriptAnalysisResponse);
        System.out.println(path);
        return "redirect:/script";
    }
}
