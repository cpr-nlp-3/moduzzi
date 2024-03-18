package CPR.NLP.controller;

import CPR.NLP.dto.ResultResponseDTO;
import CPR.NLP.repository.ResultRepository;
import CPR.NLP.service.ResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:3000", "https://www.moduzzi.site/" }, allowCredentials = "true")
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
