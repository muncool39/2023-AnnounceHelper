package com.example.helper.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

    private String UPLOADED_FOLDER = "uploads/";
    private final String AUDIO_UPLOADED_FOLDER = UPLOADED_FOLDER+"audio/";
    private final String TEXT_UPLOADED_FOLDER = UPLOADED_FOLDER+"text/";

    public Path getFilePath(MultipartFile file){
        return Paths.get(UPLOADED_FOLDER, file.getOriginalFilename());
    }

    public String getTextFileContent(Path textPath){
        String content = "";
        try {
            byte[] fileBytes = Files.readAllBytes(textPath);
            content = new String(fileBytes);
        }catch (IOException e){
            e.printStackTrace();
        }
        return content;
    }

    public Path writeTextFile(String content){
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String fileName = currentTime + ".txt";
        try {
            Path filePath = Path.of(TEXT_UPLOADED_FOLDER, fileName);
            Files.write(filePath, content.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Paths.get(TEXT_UPLOADED_FOLDER, fileName);
    }

    public Path uploadAudioFile(MultipartFile file){
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(AUDIO_UPLOADED_FOLDER, file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Paths.get(AUDIO_UPLOADED_FOLDER, file.getOriginalFilename());
    }

    public Path uploadTextFile(MultipartFile file){
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(TEXT_UPLOADED_FOLDER, file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Paths.get(TEXT_UPLOADED_FOLDER, file.getOriginalFilename());
    }
}
