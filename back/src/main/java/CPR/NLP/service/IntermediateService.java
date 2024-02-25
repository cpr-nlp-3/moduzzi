package CPR.NLP.service;

import CPR.NLP.domain.Course;
import CPR.NLP.repository.IntermediateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class IntermediateService {

    private final IntermediateRepository intermediateRepository;

    void deleteCourseIntermediate(int courseId){
        intermediateRepository.deleteByCourseCourseId(courseId);
    }
}
