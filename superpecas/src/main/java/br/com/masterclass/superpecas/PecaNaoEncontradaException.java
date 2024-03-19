package br.com.masterclass.superpecas;

public class PecaNaoEncontradaException extends RuntimeException {

    public PecaNaoEncontradaException(Long id) {
        super("A peça com o ID " + id + " não encontrada");
    }
}
