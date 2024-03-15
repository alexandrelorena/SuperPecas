package br.com.masterclass.superpecas.controller;

import br.com.masterclass.superpecas.Peca;
import br.com.masterclass.superpecas.PecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/peca")
public class PecasController {


    private PecaService pecaService;

    @PostMapping("/")
    public Peca cadastraPeca(@RequestBody Peca peca) {
        return pecaService.cadastrarPeca(peca);
    }

    @GetMapping("/")
    public Peca buscaPecaPorId(@PathVariable Long id) {
        return pecaService.buscarPeca(id);
    }

    @PutMapping("/")
    public Peca atualizaPeca(@RequestBody Peca peca) {
        return pecaService.atualizarPeca(peca);
    }

    @DeleteMapping("/")
    public void deletaPeca(@RequestBody Peca peca) {
        pecaService.deletarPeca(peca);
    }
}
