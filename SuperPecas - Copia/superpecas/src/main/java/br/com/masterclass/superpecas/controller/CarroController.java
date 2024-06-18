package br.com.masterclass.superpecas.controller;

import br.com.masterclass.superpecas.Components.Pageable.CPageable;
import br.com.masterclass.superpecas.DTO.CarroDTO;
import br.com.masterclass.superpecas.projections.CarroProjection;
import br.com.masterclass.superpecas.projections.PecaProjection;
import br.com.masterclass.superpecas.service.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carros")
public class CarroController {

    @Autowired
    private CarroService carroService;

    @PostMapping
    public ResponseEntity<Page<CarroDTO>> findAll(@RequestBody CPageable pageable) {
        return ResponseEntity.ok(carroService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarroDTO> getCarroById(@PathVariable Integer id) {
        CarroDTO carroDTO = carroService.findById(id);
        return ResponseEntity.ok(carroDTO);
    }

    @PostMapping("/salvar")
    public ResponseEntity<CarroDTO> createCarro(@RequestBody CarroDTO carroDTO) {
        CarroDTO savedCarro = carroService.save(carroDTO);
        return ResponseEntity.ok(savedCarro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarroDTO> updateCarro(@PathVariable Integer id, @RequestBody CarroDTO carroDTO) {
        CarroDTO updatedCarro = carroService.update(id, carroDTO);
        return ResponseEntity.ok(updatedCarro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarro(@PathVariable Integer id) {
        carroService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/nome")
    public ResponseEntity<List<CarroDTO>> findByNameContaining(@RequestParam String nome) {
        List<CarroDTO> carros = carroService.findByNameContaining(nome);
        return new ResponseEntity<>(carros, HttpStatus.OK);
    }

    @GetMapping("/projections/carro/{id}")
    public ResponseEntity<List<CarroProjection>> findProjectionsById(@PathVariable Integer id) {
        List<CarroProjection> carros = carroService.findProjectionsById(id);
        return new ResponseEntity<>(carros, HttpStatus.OK);
    }
}