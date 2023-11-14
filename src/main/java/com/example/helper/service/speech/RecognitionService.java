package com.example.helper.service.speech;


import com.example.helper.model.speech.RecognitionResults;
import com.google.gson.Gson;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RecognitionService {

    @Value("${api.key}")
    private String accessKey;
    private static final String OPEN_API_URL = "http://aiopen.etri.re.kr:8000/WiseASR/Recognition";

    public String getAudioResult(Path audioPath, String language) {
        String audioContents = null;

        Gson gson = new Gson();
        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();

        try {
            byte[] audioBytes = Files.readAllBytes(audioPath);
            audioContents = Base64.getEncoder().encodeToString(audioBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        argument.put("language_code", language);
        argument.put("audio", audioContents);
        request.put("argument", argument);

        URL url;
        Integer responseCode = null;
        String responseBody = null;

        String analysisResult = null;
        try {
            url = new URL(OPEN_API_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", accessKey);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(gson.toJson(request).getBytes("UTF-8"));
            wr.flush();
            wr.close();

            responseCode = con.getResponseCode();
            InputStream is = con.getInputStream();
            byte[] buffer = new byte[is.available()];

            int byteRead = is.read(buffer);
            responseBody = new String(buffer);
            RecognitionResults result = gson.fromJson(responseBody, RecognitionResults.class);
            analysisResult = result.getReturn_object().getRecognized();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return analysisResult;
    }
}
