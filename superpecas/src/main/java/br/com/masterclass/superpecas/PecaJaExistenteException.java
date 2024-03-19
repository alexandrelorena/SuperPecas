package br.com.masterclass.superpecas;

public class PecaJaExistenteException extends RuntimeException {

    public PecaJaExistenteException(String nome, String numeroserie) {
        super("Já existe um carro com estes atributos: '" + nome + "'" + numeroserie + "'");
    }
}
