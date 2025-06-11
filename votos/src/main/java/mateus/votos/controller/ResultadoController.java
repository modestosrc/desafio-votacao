package mateus.votos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mateus.votos.service.ResultadoService;

@RestController
@RequestMapping("/resultado")
@Tag(name = "Resultados", description = "Endpoints for managing resultados")
public class ResultadoController {

    @Autowired
    private ResultadoService resultadoService;

    @GetMapping("/{id}")
    @Operation(summary = "Get resultado by ID", description = "Retrieves a resultado by its ID")
    public ResponseEntity<?> getResultadoById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(resultadoService.getResultadoByIdPauta(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resultado não encontrado: " + e.getMessage());
        }
    }
}
