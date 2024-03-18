package CPR.NLP.service;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.Map;

@Service
public class PythonServiceCaller {

    /*public String callSummarizeFunction(String inputText, String clientId, String clientSecret) {
        String output = "";
        try {
            InputStream inputStream = getClass().getResourceAsStream("/api_summarize.py");
            if (inputStream == null) {
                throw new FileNotFoundException("api_summarize.py 파일을 찾을 수 없습니다.");
            }

            File tempFile = File.createTempFile("api_summarize", ".py");
            try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } finally {
                inputStream.close();
            }

            // Python 스크립트 파일의 임시 경로
            String pythonScriptPath = tempFile.getAbsolutePath();*/

            // Python 스크립트 실행을 위한 ProcessBuilder 인스턴스 생성
            /*ClassPathResource resource = new ClassPathResource("api_summarize.py");
            String pythonScriptPath = resource.getFile().getAbsolutePath();*/
            /*ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath, inputText);

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
            process.destroy();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("output = " + output);
        return output;
    }*/

    public String callSummarizeFunction(String inputText, String clientId, String clientSecret) {
        String output = "";
        try {
            // Python 스크립트를 실행할 때 사용할 리소스 파일을 복사하여 임시 디렉토리에 저장
            InputStream resourceStream = getClass().getResourceAsStream("/api_summarize.py");
            File tempScriptFile = File.createTempFile("api_summarize", ".py");
            FileUtils.copyInputStreamToFile(resourceStream, tempScriptFile);

            // Python 스크립트 실행을 위한 ProcessBuilder 인스턴스 생성
            ProcessBuilder processBuilder = new ProcessBuilder("python", tempScriptFile.getAbsolutePath(), inputText);

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

            // 임시 스크립트 파일 삭제
            tempScriptFile.delete();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return output;
    }

    /*public String callSummarizeFunction(String inputText, String clientId, String clientSecret) {
        String output = "";
        try {
            // Python 스크립트 파일을 클래스 패스 상에서 가져옴
            ClassPathResource resource = new ClassPathResource("api_summarize.py");

            // 파일을 임시 디렉토리에 복사
            Path tempFile = Files.createTempFile("api_summarize", ".py");
            Files.copy(resource.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

            // Python 스크립트 실행을 위한 ProcessBuilder 인스턴스 생성
            ProcessBuilder processBuilder = new ProcessBuilder("python", tempFile.toString(), inputText);

            // 환경 변수 설정
            processBuilder.environment().put("CLOVA_CLIENT_ID", clientId);
            processBuilder.environment().put("CLOVA_CLIENT_SECRET", clientSecret);

            Process process = processBuilder.start();

            // 프로세스 출력을 읽어서 결과 문자열에 추가
            try (InputStream inputStream = process.getInputStream()) {
                output = new String(FileCopyUtils.copyToByteArray(inputStream));
            }

            // 프로세스 종료 및 리소스 해제
            process.waitFor();
            process.destroy();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("output = " + output);
        return output;
    }*/

    public String callSentimentFunction(String inputText, String clientId, String clientSecret) {
        String output = "";
        try {
            // Python 스크립트를 실행할 때 사용할 리소스 파일을 복사하여 임시 디렉토리에 저장
            InputStream resourceStream = getClass().getResourceAsStream("/api_sentiment.py");
            File tempScriptFile = File.createTempFile("api_sentiment", ".py");
            FileUtils.copyInputStreamToFile(resourceStream, tempScriptFile);

            // Python 스크립트 실행을 위한 ProcessBuilder 인스턴스 생성
            ProcessBuilder processBuilder = new ProcessBuilder("python", tempScriptFile.getAbsolutePath(), inputText);

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

            // 임시 스크립트 파일 삭제
            tempScriptFile.delete();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return output;
    }
}