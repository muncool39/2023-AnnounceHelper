package com.example.helper.service.speech;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

    private static final String UPLOADED_FOLDER = "uploads/";

    public Path getFilePath(MultipartFile file){
        return Paths.get(UPLOADED_FOLDER, file.getOriginalFilename());
    }

    public void uploadAudioFile(MultipartFile file){
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER, file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
