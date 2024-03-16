package br.com.masterclass.superpecas.controller;

import br.com.masterclass.superpecas.Carro;
import br.com.masterclass.superpecas.Peca;
import br.com.masterclass.superpecas.model.PecaService;
//import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/peca")

public class PecasController {

    private final PecaService pecaService;

    @Autowired
    public PecasController(PecaService pecaService) {
        this.pecaService = pecaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Peca> buscarPeca(@PathVariable Long id) {
        Peca peca = pecaService.buscarPeca(id);
        return ResponseEntity.ok(peca);
    }

    @GetMapping("/listaTodos")
    public ResponseEntity<List<Peca>> listarTodasAsPecas() {
        List<Peca> pecas = pecaService.listarTodasAsPecas();
        return ResponseEntity.ok(pecas);
    }

    @GetMapping("/listaTodosPaginado")
    public ResponseEntity<Page<Peca>> listarTodasAsPecasPaginado(Pageable pageable) {
        Page<Peca> pecas = (Page<Peca>) pecaService.listarTodasAsPecasPaginado(pageable);
        return ResponseEntity.ok(pecas);
    }

    @GetMapping("/listaTodosPaginado/{termo}")
    public ResponseEntity<Page<Peca>> listarTodasAsPecasPaginadoComTermo(@PathVariable String termo, Pageable pageable) {
        Page<Peca> pecas = (Page<Peca>) pecaService.listarTodasAsPecasPaginadoComTermo(termo, pageable);
        return ResponseEntity.ok(pecas);
    }

    @GetMapping("/listaTop10CarroComMaisPecas")
    public ResponseEntity<List<Carro>> listarTop10CarrosComMaisPecas() {
        List<Carro> topCarros = pecaService.listarTop10CarrosComMaisPecas();
        return ResponseEntity.ok(topCarros);
    }

    @PostMapping
    public ResponseEntity<Peca> cadastrarPeca(@RequestBody Peca peca) {
        Peca pecaSalva = pecaService.cadastrarPeca(peca);
        return ResponseEntity.status(HttpStatus.CREATED).body(pecaSalva);
    }

    @PutMapping("/peca")
    public ResponseEntity<Peca> atualizarPeca(@RequestBody Peca peca) {
        Peca pecaAtualizada = pecaService.atualizarPeca(peca.getPecaId(), peca);
        return ResponseEntity.ok(pecaAtualizada);
    }

    @DeleteMapping("/peca/{id}")
    public void deletaPeca(@PathVariable Long id) {
        pecaService.deletarPeca(id);
    }
}
