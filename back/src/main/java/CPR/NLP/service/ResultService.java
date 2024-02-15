package CPR.NLP.service;

import CPR.NLP.domain.Course;
import CPR.NLP.domain.Result;
import CPR.NLP.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ResultService {

    private final ResultRepository resultRepository;

    public Result findResultByCourse(Course course) { //프론트에서 course 리스트 중 하나를 선택하고 해당 course를 parameter로 보내줌
        Result result =  resultRepository.findByCourse(course)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의에 대한 분석 결과물이 존재하지 않습니다."));

        return result;
    }
}
