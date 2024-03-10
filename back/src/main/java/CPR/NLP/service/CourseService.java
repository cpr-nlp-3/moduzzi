package CPR.NLP.service;

import CPR.NLP.domain.Course;
import CPR.NLP.dto.CourseRequestDTO;
import CPR.NLP.dto.CourseResponseDTO;
import CPR.NLP.repository.CourseRepository;
import lombok.RequiredArgsConstructor;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    /*
     ClassPathResource resource = new ClassPathResource("개설교과목정보.xlsx");
     String pythonScriptPath = resource.getFile().getAbsolutePath();
     */

    //private String excelFilePath = "C:\\Users\\dlthd\\Desktop\\웹_프로젝트\\NLP-3\\moduzzi\\back\\src\\main\\resources\\개설교과목정보.xlsx";
    private Resource resource = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader())
            .getResource("classpath*:개설교과목정보.xlsx");
    private String lastHash = null;

    @Scheduled(cron = "0 12 0 * * MON") //매주 월요일 오후 12시에 실행 //0 12 0 * * MON
    public void checkAndUpdateExcel() {
        try {
            String currentHash = calculateMD5(resource.getInputStream());

            if (!currentHash.equals(lastHash)) {
                // 파일이 업데이트되었음 -> 크롤링
                crawlExcelFile(resource);
                lastHash = currentHash;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String calculateMD5(InputStream inputStream) throws IOException {
        return DigestUtils.md5Hex(inputStream);
    }

    private void crawlExcelFile(Resource resource) {
        try {
            //FileInputStream inputStream = new FileInputStream(new File(filePath));
            InputStream inputStream = resource.getInputStream();
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0); // 첫 번째 시트를 가져옴

            // 각 행을 순회하면서 열 값을 가져옴
            for (int row = 1; row < sheet.getPhysicalNumberOfRows(); row++) {
                XSSFRow currentRow = sheet.getRow(row);
                String cell5 = currentRow.getCell(4).getStringCellValue(); // 5번째 열 (5-1=4)
                String cell7 = currentRow.getCell(6).getStringCellValue();
                String cell9 = currentRow.getCell(8).getStringCellValue();
                String cell11 = currentRow.getCell(10).getStringCellValue();

                Optional<Course> course = courseRepository.findByNameAndProfessor(cell5, cell11);
                if (course.isPresent()) {
                    Course existing = course.get();
                    long daysDifference = ChronoUnit.DAYS.between(existing.getUpdatedAt(), LocalDateTime.now());
                    long daysDifference2 = ChronoUnit.DAYS.between(existing.getCreatedAt(), LocalDateTime.now());

                    // 업데이트 조건: updatedAt와 현재 시간의 차이, 혹은 createdAt과 현재 시간의 차이가 하루 이내인 경우: 즉 같은 과목과 교수님이 여러 분반을 가르칠 경우
                    if (daysDifference <= 1 || daysDifference2 <= 1) {
                        Course updatedCourse = Course.builder()
                                .courseId(existing.getCourseId())
                                .code(cell5)
                                .name(cell7)
                                .professor(cell11)
                                .location(existing.getLocation())
                                .time(existing.getTime() + " / " + cell9)
                                .createdAt(existing.getCreatedAt())
                                //.updatedAt(LocalDateTime.now())
                                .build();

                        courseRepository.save(updatedCourse);
                    } else {
                        Course updatedCourse = Course.builder()
                                .courseId(existing.getCourseId())
                                .code(cell5)
                                .name(cell7)
                                .professor(cell11)
                                .location(existing.getLocation())
                                .time(cell9)
                                .createdAt(existing.getCreatedAt())
                                //.updatedAt(LocalDateTime.now())
                                .build();

                        courseRepository.save(updatedCourse);
                    }
                } else {
                    Course newCourse = Course.builder()
                            .code(cell5)
                            .name(cell7)
                            .professor(cell11)
                            .time(cell9)
                            //.createdAt(LocalDateTime.now())
                            .build();

                    courseRepository.save(newCourse);
                }
            }

            workbook.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<CourseResponseDTO> findAll(){
        List<Course> courses = courseRepository.findAll();
        if (courses.isEmpty()) {
            throw new IllegalArgumentException("아무런 과목 정보가 존재하지 않습니다.");
        }

        return courses.stream().map(CourseResponseDTO::new).collect(Collectors.toList());
    }

    public List<CourseResponseDTO> findByName(String name){
        List<Course> courses = courseRepository.findByName(name);
        if (courses.isEmpty()) {
            throw new IllegalArgumentException("해당 이름을 가지는 과목이 존재하지 않습니다.");
        }

        return courses.stream().map(CourseResponseDTO::new).collect(Collectors.toList());
    }

    public List<CourseResponseDTO> findByProfessor(String professor){
        List<Course> courses = courseRepository.findByProfessor(professor);
        if (courses.isEmpty()) {
            throw new IllegalArgumentException("해당 교수님이 강의하시는 과목이 존재하지 않습니다.");
        }

        return courses.stream().map(CourseResponseDTO::new).collect(Collectors.toList());
    }
}