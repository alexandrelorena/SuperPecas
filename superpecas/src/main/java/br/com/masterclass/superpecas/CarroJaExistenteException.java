package br.com.masterclass.superpecas;

public class CarroJaExistenteException extends RuntimeException {

    // Construtor que recebe os campos que definem a unicidade do carro
    public CarroJaExistenteException(String nomeModelo, String fabricante, String codigoUnico) {
        super("Já existe um carro com estes atributos: '" + nomeModelo + "'" + fabricante + "'" + codigoUnico + "'");
    }
}
