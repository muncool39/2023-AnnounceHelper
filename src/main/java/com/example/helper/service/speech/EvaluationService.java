package com.example.helper.service.speech;


import com.example.helper.model.speech.EvaluationResults;
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
public class EvaluationService {
    @Value("${api.key}")
    private String accessKey;
    private static final String OPEN_API_URL_ENG = "http://aiopen.etri.re.kr:8000/WiseASR/Pronunciation";
    private static final String OPEN_API_URL_KOR = "http://aiopen.etri.re.kr:8000/WiseASR/PronunciationKor";

    public String getPronunciationAnalysisResults(Path audioPath, String language, String script) {

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
        argument.put("script", script);
        argument.put("audio", audioContents);
        request.put("argument", argument);
        URL url;
        Integer responseCode = null;
        String responBody = null;
        String evaluationScore = null;
        try {
            url = getOpenApiUrl(language);
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
            responBody = new String(buffer);

            EvaluationResults result = gson.fromJson(responBody, EvaluationResults.class);
            evaluationScore = result.getReturn_object().getScore();
            System.out.println("[responseCode] " + responseCode);
            System.out.println("[responBody]");
            System.out.println(responBody);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return evaluationScore;
    }

    private URL getOpenApiUrl(String language) throws MalformedURLException {
        if(language.equals("korean")) return new URL(OPEN_API_URL_KOR);
        return new URL(OPEN_API_URL_ENG);
    }
}
