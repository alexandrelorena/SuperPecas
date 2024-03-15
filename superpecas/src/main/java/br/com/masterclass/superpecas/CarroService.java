package br.com.masterclass.superpecas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;

    public Carro cadastrarCarro(Carro carro) {
        if (carroRepository.existsByNomeModeloAndFabricanteAndCodigoUnico(
                carro.getNomeModelo(), carro.getFabricante(), carro.getCodigoUnico())) {
            throw new CarroJaExistenteException(carro.getNomeModelo(), carro.getFabricante(), carro.getCodigoUnico());
        }
        return carroRepository.save(carro);
    }

    public Carro buscarCarro(Long id) {
        return carroRepository.findById(id)
                .orElseThrow(() -> new CarroNaoEncontradoException(id));
    }

    public Carro atualizarCarro(Carro carro) {
        return carroRepository.save(carro);
    }

    public void deletarCarro(Carro carro) {
        carroRepository.delete(carro);
    }
}
