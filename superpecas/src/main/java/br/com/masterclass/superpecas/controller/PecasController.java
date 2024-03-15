package br.com.masterclass.superpecas.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController

@RequestMapping("/peca")

public class PecasController {

    @RequestMapping(value = "/", method = RequestMethod.GET)

    public String buscaPeca() {

        return "teste";

    }


    @RequestMapping(value = "/", method = RequestMethod.POST)

    public String cadastraPeca() {

        return "teste";

    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)

    public String atualizaPeca() {

        return "teste";

    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)

    public String deletaPeca() {

        return "teste";

    }
}