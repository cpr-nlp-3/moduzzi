package CPR.NLP.controller;

import CPR.NLP.dto.ResultResponseDTO;
import CPR.NLP.repository.ResultRepository;
import CPR.NLP.service.ResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("result")
public class ResultController {

    private final ResultService resultService;

    @GetMapping
    public ResponseEntity<ResultResponseDTO> findResult(@RequestParam String name, @RequestParam String professor) {
        try {
            ResultResponseDTO resultDTO = resultService.findResultByNameAndProfessor(name, professor);
            return ResponseEntity.ok(resultDTO);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
