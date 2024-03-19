package br.com.masterclass.superpecas;

public class CarroNaoEncontradoException extends RuntimeException {

    public CarroNaoEncontradoException(Long id) {
        super("Carro com o ID " + id + " não encontrado");
    }
}
