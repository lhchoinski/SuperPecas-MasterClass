package br.com.masterclass.superpecas.controller;

import br.com.masterclass.superpecas.components.pageable.CPageable;
import br.com.masterclass.superpecas.DTO.CarroDTO;
import br.com.masterclass.superpecas.projections.CarroProjection;
import br.com.masterclass.superpecas.service.CarroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carros")
@Tag(name = "Carros", description = "Gestão de Carros")
public class CarroController {

    @Autowired
    private CarroService carroService;

    @PostMapping
    @Operation(summary = "Buscar carros")
    public ResponseEntity<Page<CarroDTO>> findAll(@RequestBody CPageable pageable) {
        return ResponseEntity.ok(carroService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar carros por Id")
    public ResponseEntity<CarroDTO> getCarroById(@PathVariable Integer id) {
        CarroDTO carroDTO = carroService.findById(id);
        return ResponseEntity.ok(carroDTO);
    }

    @PostMapping("/salvar")
    @Operation(summary = "Cadastrar novos carros")
    public ResponseEntity<CarroDTO> createCarro(@RequestBody CarroDTO carroDTO) {
        CarroDTO savedCarro = carroService.save(carroDTO);
        return ResponseEntity.ok(savedCarro);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar carros")
    public ResponseEntity<CarroDTO> updateCarro(@PathVariable Integer id, @RequestBody CarroDTO carroDTO) {
        CarroDTO updatedCarro = carroService.update(id, carroDTO);
        return ResponseEntity.ok(updatedCarro);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar carros")
    public ResponseEntity<Void> deleteCarro(@PathVariable Integer id) {
        carroService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/nome")
    @Operation(summary = "Buscar carros pelo nome")
    public ResponseEntity<List<CarroDTO>> findByNameContaining(@RequestParam String nome) {
        List<CarroDTO> carros = carroService.findByNameContaining(nome);
        return new ResponseEntity<>(carros, HttpStatus.OK);
    }

    @GetMapping("/projections/carro/{id}")
    @Operation(summary = "Buscar carros e retornar informacoes basicas")
    public ResponseEntity<List<CarroProjection>> findProjectionsById(@PathVariable Integer id) {
        List<CarroProjection> carros = carroService.findProjectionsById(id);
        return new ResponseEntity<>(carros, HttpStatus.OK);
    }
}