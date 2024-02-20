package CPR.NLP.service;

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

    public ResultResponseDTO findResultByNameAndProfessor(String name, String professor) {
        Result result = resultRepository.findByCourseNameAndCourseProfessor(name, professor)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의에 대한 분석 결과물이 존재하지 않습니다."));

        return new ResultResponseDTO(result);
    }

    public ResultResponseDTO findResultByCourseId(int courseId) {
        Result result = resultRepository.findByCourseId(courseId)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의에 대한 분석 결과물이 존재하지 않습니다."));

        return new ResultResponseDTO(result);
    }
}
