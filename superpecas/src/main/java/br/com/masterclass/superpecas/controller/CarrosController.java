package br.com.masterclass.superpecas.controller;

import br.com.masterclass.superpecas.CarroNaoEncontradoException;
import br.com.masterclass.superpecas.model.DTO.CarroDTO;
import br.com.masterclass.superpecas.service.CarroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carro")
public class CarrosController {

    private final CarroService carroService;

    private final Logger logger = LoggerFactory.getLogger(CarrosController.class);

    @Autowired
    public CarrosController(CarroService carroService) {
        this.carroService = carroService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarroDTO> buscaCarro(@PathVariable Long id) {
        try {
            CarroDTO carroDTO = this.carroService.buscarCarro(id);
            return ResponseEntity.ok(carroDTO);
        } catch (CarroNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Erro buscando carro com id: " + id, e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/listaTodos")
    public ResponseEntity<List<CarroDTO>> listarTodosOsCarros() {
        List<CarroDTO> carrosDTO = carroService.listarTodosOsCarros();
        return ResponseEntity.ok(carrosDTO);
    }

    // Método para listar todos os carros paginados e retornar como DTOs
    @GetMapping("/listaTodosPaginado")
    public ResponseEntity<Page<CarroDTO>> listarTodosOsCarrosPaginado(Pageable pageable) {
        Page<CarroDTO> carros = carroService.listarTodosOsCarrosPaginado(pageable);
        return ResponseEntity.ok(carros);
    }

    @GetMapping("/listaTodosPaginado/{termo}")
    public ResponseEntity<Page<CarroDTO>> listarTodosOsCarrosPaginadoComTermo(@PathVariable String termo, Pageable pageable) {
        Page<CarroDTO> carros = carroService.listarTodosOsCarrosPaginadoComTermo(termo, pageable);
        return ResponseEntity.ok(carros);
    }

    @GetMapping("/listaTodosFabricantes")
    public ResponseEntity<List<String>> listarTodosOsFabricantes() {
        List<String> fabricantes = carroService.listarTodosOsFabricantes();
        return ResponseEntity.ok(fabricantes);
    }

    @GetMapping("/listaTop10Fabricantes")
    public ResponseEntity<List<String>> listarTop10Fabricantes() {
        List<String> topFabricantes = carroService.listarTop10Fabricantes();
        return ResponseEntity.ok(topFabricantes);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<CarroDTO> cadastraCarro(@RequestBody CarroDTO carroDTO) {
        CarroDTO carroSalvoDTO = carroService.cadastrarCarro(carroDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(carroSalvoDTO);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<CarroDTO> atualizaCarro(@PathVariable Long id, @RequestBody CarroDTO carroDTO) {
        CarroDTO carroAtualizadoDTO = carroService.atualizarCarro(id, carroDTO);
        return ResponseEntity.ok(carroAtualizadoDTO);
    }

    @DeleteMapping("/deletar/{id}")
    public void deletaCarro(@PathVariable Long id) {
        carroService.deletarCarro(id);
    }
}
