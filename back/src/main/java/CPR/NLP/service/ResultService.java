package CPR.NLP.service;

import CPR.NLP.domain.Course;
import CPR.NLP.domain.Result;
import CPR.NLP.dto.ResultResponseDTO;
import CPR.NLP.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ResultService {

    private final ResultRepository resultRepository;

    public ResultResponseDTO findResultByCourse(Course course) { //프론트에서 course 리스트 중 하나를 선택하고 해당 course를 parameter로 보내줌
        Result result =  resultRepository.findByCourse(course)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의에 대한 분석 결과물이 존재하지 않습니다."));

        return new ResultResponseDTO(result);
    }

    public ResultResponseDTO findResultByNameAndProfessor(String name, String professor) {
        Result result = resultRepository.findByCourse_NameAndCourse_Professor(name, professor)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의에 대한 분석 결과물이 존재하지 않습니다."));

        return new ResultResponseDTO(result);
    }
}
