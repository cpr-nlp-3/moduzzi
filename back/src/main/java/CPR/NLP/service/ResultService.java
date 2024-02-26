package CPR.NLP.service;

import CPR.NLP.domain.Result;
import CPR.NLP.dto.ResultRequestDTO;
import CPR.NLP.dto.ResultResponseDTO;
import CPR.NLP.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

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

    /*public void saveOrUpdateResult(ResultRequestDTO resultDTO) {
        Optional<Result> existingResult = resultRepository.findByCourse(resultDTO.getCourse());

        if (existingResult.isEmpty()) {
            Result newResult = resultDTO.toEntity();
            resultRepository.save(newResult);
        } else {
            Result existing = existingResult.get();
            Result updatedResult = Result.builder()
                    .resultId(existing.getResultId())
                    .course(existing.getCourse())
                    .data(resultDTO.getData())
                    .averageRating(resultDTO.getAverageRating())
                    .createdAt(existing.getCreatedAt()) // 기존 생성일 유지
                    .updatedAt(LocalDateTime.now()) // 현재 시간으로 업데이트
                    .build();

            resultRepository.save(updatedResult);
        }
    }*/
}
