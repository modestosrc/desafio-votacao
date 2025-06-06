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

import mateus.votos.dto.PautaDTO;
import mateus.votos.model.Pauta;
import mateus.votos.service.PautaService;

@RestController
@RequestMapping("/pautas")
public class PautaController {

    @Autowired
    private PautaService pautaService;

    @PostMapping
    public ResponseEntity<?> createPauta(@RequestBody PautaDTO pautaDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(pautaService.createPauta(pautaDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating pauta: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPautaById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pautaService.getPautaById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pauta not found: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllPautas() {
        try {
            return ResponseEntity.ok(pautaService.getPautaAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching pautas: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePauta(@PathVariable Long id, @RequestBody PautaDTO pautaDTO) {
        try {
            Pauta updatedPauta = pautaService
                    .updatePauta(new Pauta(id, pautaDTO.getName(), pautaDTO.getConteudo(), pautaDTO.getStatus()));
            return ResponseEntity.ok(updatedPauta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating pauta: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePauta(@PathVariable Long id) {
        try {
            pautaService.deletePauta(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting pauta: " + e.getMessage());
        }
    }
}
