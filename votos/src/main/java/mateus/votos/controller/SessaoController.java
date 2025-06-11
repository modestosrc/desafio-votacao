package mateus.votos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mateus.votos.dto.SessaoDTO;
import mateus.votos.model.Sessao;
import mateus.votos.service.SessaoService;

@RestController
@RequestMapping("/sessao")
@Tag(name = "Sessões", description = "Endpoints for managing voting sessions")
public class SessaoController {

    @Autowired
    private SessaoService sessaoService;

    @PostMapping
    @Operation(summary = "Create a new session", description = "Creates a new voting session with the provided details")
    public ResponseEntity<?> createSessao(@RequestBody SessaoDTO sessaoDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(sessaoService.createSessao(sessaoDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating session: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get session by ID", description = "Retrieves a voting session by its ID")
    public ResponseEntity<?> getSessaoById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(sessaoService.getSessaoById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Session not found: " + e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Get all sessions", description = "Retrieves a list of all voting sessions")
    public ResponseEntity<?> getAllSessoes() {
        try {
            return ResponseEntity.ok(sessaoService.getSessaoAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching sessions: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update session by ID", description = "Updates a voting session with the provided details")
    public ResponseEntity<?> updateSessao(@PathVariable Long id, @RequestBody SessaoDTO sessaoDTO) {
        try {
            Sessao updatedSessao = sessaoService
                    .updateSessao(
                            new Sessao(id, sessaoDTO.getIdPauta(), sessaoDTO.getDataInicio(), sessaoDTO.getDataFim()));
            return ResponseEntity.ok(updatedSessao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating session: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete session by ID", description = "Deletes a voting session by its ID")
    public ResponseEntity<?> deleteSessao(@PathVariable Long id) {
        try {
            sessaoService.deleteSessao(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting session: " + e.getMessage());
        }
    }
}
