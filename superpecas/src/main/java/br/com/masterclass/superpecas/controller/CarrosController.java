package br.com.masterclass.superpecas.controller;

import br.com.masterclass.superpecas.Carro;
import br.com.masterclass.superpecas.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/carro")
public class CarrosController {

    @Autowired
    private CarroService carroService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Carro> buscaCarro() {  // Atualizado para retornar uma lista de Carros
        return carroService.buscarCarros();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Carro cadastraCarro(@RequestBody Carro carro) {
        return carroService.cadastrarCarro(carro);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public Carro atualizaCarro(@RequestBody Carro carro) {  // Atualizado para receber e retornar Carro
        return carroService.atualizarCarro(carro);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public void deletaCarro(@PathVariable Long id) {  // Atualizado para receber id e não retornar nada (void)
        carroService.deletarCarro(id);
    }
}
