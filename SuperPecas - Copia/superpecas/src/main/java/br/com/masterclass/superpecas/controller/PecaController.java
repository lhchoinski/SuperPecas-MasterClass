package br.com.masterclass.superpecas.controller;

import br.com.masterclass.superpecas.Components.Pageable.CPageable;
import br.com.masterclass.superpecas.DTO.PecaDTO;
import br.com.masterclass.superpecas.projections.PecaProjection;
import br.com.masterclass.superpecas.service.PecaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pecas")
@Tag(name = "Peças", description = "Gestão de Peças")
public class PecaController {

    @Autowired
    private PecaService pecaService;

    @PostMapping
    @Operation(summary = "Buscar peças")
    public ResponseEntity<Page<PecaDTO>> findAll(@RequestBody CPageable pageable) {
        return ResponseEntity.ok(pecaService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar peças por Id")
    public ResponseEntity<PecaDTO> getPecaById(@PathVariable Integer id) {
        PecaDTO pecaDTO = pecaService.findById(id);
        return ResponseEntity.ok(pecaDTO);
    }

    @PostMapping("/salvar")
    @Operation(summary = "Cadastrar novas peças")
    public ResponseEntity<PecaDTO> createPeca(@RequestBody PecaDTO pecaDTO) {
        PecaDTO savedPeca = pecaService.save(pecaDTO);
        return ResponseEntity.ok(savedPeca);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar peças")
    public ResponseEntity<PecaDTO> updatePeca(@PathVariable Integer id, @RequestBody PecaDTO pecaDTO) {
        PecaDTO updatedPeca = pecaService.update(id, pecaDTO);
        return ResponseEntity.ok(updatedPeca);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar peças")
    public ResponseEntity<Void> deletePeca(@PathVariable Integer id) {
        pecaService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/nome")
    @Operation(summary = "Buscar peças pelo nome")
    public ResponseEntity<List<PecaDTO>> findByNameContaining(@RequestParam String nome) {
        List<PecaDTO> pecas = pecaService.findByNameContaining(nome);
        return new ResponseEntity<>(pecas, HttpStatus.OK);
    }

    @GetMapping("/projections/pecas/{id}")
    @Operation(summary = "Buscar peças e retornar informacoes basicas")
    public ResponseEntity<List<PecaProjection>> findProjectionsById(@PathVariable Integer id) {
        List<PecaProjection> pecas = pecaService.findProjectionsById(id);
        return new ResponseEntity<>(pecas, HttpStatus.OK);
    }

}
