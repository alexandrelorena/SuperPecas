package br.com.masterclass.superpecas.controller;

import br.com.masterclass.superpecas.model.Carro;
import br.com.masterclass.superpecas.CarroNaoEncontradoException;
import br.com.masterclass.superpecas.service.CarroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public Optional<Carro> buscaCarro(@PathVariable Long id) {
        try {
            return Optional.ofNullable(this.carroService.buscarCarro(id));
        } catch (CarroNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Erro buscando carro com id: " + id, e);
            return Optional.empty();
        }
    }

    @GetMapping("/listaTodos")
    public ResponseEntity<List<Carro>> listarTodosOsCarros() {
        List<Carro> carros = carroService.listarTodosOsCarros();
        return ResponseEntity.ok(carros);
    }

    @GetMapping("/listaTodosPaginado")
    public ResponseEntity<Page<Carro>> listarTodosOsCarrosPaginado(Pageable pageable) {
        Page<Carro> carros = carroService.listarTodosOsCarrosPaginado(pageable);
        return ResponseEntity.ok(carros);
    }

    @GetMapping("/listaTodosPaginado/{termo}")
    public ResponseEntity<Page<Carro>> listarTodosOsCarrosPaginadoComTermo(@PathVariable String termo, Pageable pageable) {
        Page<Carro> carros = carroService.listarTodosOsCarrosPaginadoComTermo(termo, pageable);
        return ResponseEntity.ok(carros);
    }

    @GetMapping("/listaTodosFabricantes")
    public ResponseEntity<List<String>> listarTodosOsFabricantes() {
        List<String> fabricantes = carroService.listarTodosOsFabricantes();
        return ResponseEntity.ok(fabricantes);
    }

    @GetMapping("/listaTop10Fabricantes")
    public ResponseEntity<List<Carro>> listarTop10Fabricantes() {
        List<Carro> topFabricantes = carroService.listarTop10Fabricantes();
        return ResponseEntity.ok(topFabricantes);
    }

    @PostMapping("/cadastrar")
    public Carro cadastraCarro(@RequestBody Carro carro) {
        return carroService.cadastrarCarro(carro);
    }

    @PutMapping("/atualizar")
    public Carro atualizaCarro(@RequestBody Carro carro) {
        return carroService.atualizarCarro(carro);
    }

    @DeleteMapping("/deletar/{id}")
    public void deletaCarro(@PathVariable Long id) {
        carroService.deletarCarro(id);
    }
}
