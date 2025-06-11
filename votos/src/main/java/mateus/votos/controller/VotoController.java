package mateus.votos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mateus.votos.dto.VotoDTO;
import mateus.votos.exception.AlreadyExistsException;
import mateus.votos.service.VotoService;

@RestController
@RequestMapping("/voto")
@Tag(name = "Votos", description = "Endpoints for managing votes")
public class VotoController {

    @Autowired
    private VotoService votoService;

    @PostMapping
    @Operation(summary = "Create a new vote", description = "Creates a new vote with the provided details")
    public ResponseEntity<?> createVoto(@RequestBody VotoDTO votoDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(votoService.createVoto(votoDTO));
        } catch (AlreadyExistsException _) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Voto já existe");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar voto: " + e.getMessage());
        }
    }

    // Pode ser enviado para a api um votoDTO sem voto, apenas para buscar o
    // status do voto para aquele cpf e pauta especificados
    // Talvez seria interessante nao requesitar o dto, mas apenas o cpf e o id da pauta
    @GetMapping
    @Operation(summary = "Get vote by CPF and Pauta ID", description = "Retrieves a vote by CPF and Pauta ID")
    public ResponseEntity<?> getVoto(@RequestBody VotoDTO votoDTO) {
        try {
            return ResponseEntity.ok(votoService.existsByCpfAndPauta(votoDTO.getCpf(), votoDTO.getIdPauta()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar votos: " + e.getMessage());
        }
    }

}
