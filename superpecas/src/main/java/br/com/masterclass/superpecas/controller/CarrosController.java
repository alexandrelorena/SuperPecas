package br.com.masterclass.superpecas.controller;

import br.com.masterclass.superpecas.Carro;
import br.com.masterclass.superpecas.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carro")
public class CarrosController {


    private CarroService carroService;

    @PostMapping("/")
    public Carro cadastraCarro(@RequestBody Carro carro) {
        return carroService.cadastrarCarro(carro);
    }

    @GetMapping("/")
    public Carro buscaCarroPorId(@PathVariable Long id) {
        return carroService.buscarCarro(id);
    }

    @PutMapping("/")
    public Carro atualizaCarro(@RequestBody Carro carro) {
        return carroService.atualizarCarro(carro);
    }

    @DeleteMapping("/")
    public void deletaCarro(@RequestBody Carro carro) {
        carroService.deletarCarro(carro);
    }
}
