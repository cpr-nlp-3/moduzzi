package CPR.NLP.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class PythonServiceCaller {

    public String callSummarizeFunction(String inputText, String clientId, String clientSecret) {
        String output = "";
        try {
            // Python 스크립트 실행을 위한 ProcessBuilder 인스턴스 생성
            ClassPathResource resource = new ClassPathResource("api_summarize.py");
            String pythonScriptPath = resource.getFile().getAbsolutePath();
            ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath, inputText);
            //ProcessBuilder processBuilder = new ProcessBuilder("python", "C:/Users/dlthd/Desktop/웹_프로젝트/NLP-3/moduzzi/back/src/main/resources/api_summarize.py", inputText);

            // 환경 변수 설정
            Map<String, String> env = processBuilder.environment();
            env.put("CLOVA_CLIENT_ID", clientId);
            env.put("CLOVA_CLIENT_SECRET", clientSecret);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output += line + "\n";
            }

            // 프로세스 종료 및 리소스 해제
            process.waitFor();
            /*BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.out.println(errorLine);
            }*/
            process.destroy();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return output;
    }

    public String callSentimentFunction(String inputText, String clientId, String clientSecret) {
        String output = "";
        try {
            // Python 스크립트 실행을 위한 ProcessBuilder 인스턴스 생성
            ClassPathResource resource = new ClassPathResource("api_sentiment.py");
            String pythonScriptPath = resource.getFile().getAbsolutePath();
            ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath, inputText);
            //ProcessBuilder processBuilder = new ProcessBuilder("python", "C:/Users/dlthd/Desktop/웹_프로젝트/NLP-3/moduzzi/back/src/main/resources/api_sentiment.py", inputText);

            // 환경 변수 설정
            Map<String, String> env = processBuilder.environment();
            env.put("CLOVA_CLIENT_ID", clientId);
            env.put("CLOVA_CLIENT_SECRET", clientSecret);

            Process process = processBuilder.start();

            // 프로세스의 출력을 읽기 위한 BufferedReader 생성
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output += line + "\n";
            }

            // 프로세스 종료 및 리소스 해제
            process.waitFor();
            process.destroy();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return output;
    }
}