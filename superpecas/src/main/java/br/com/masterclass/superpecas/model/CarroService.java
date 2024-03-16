package br.com.masterclass.superpecas.model;

import br.com.masterclass.superpecas.Carro;
import br.com.masterclass.superpecas.CarroJaExistenteException;
import br.com.masterclass.superpecas.CarroNaoEncontradoException;
import br.com.masterclass.superpecas.repository.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;

    public Carro cadastrarCarro(@org.jetbrains.annotations.NotNull Carro carro) {
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

    public List<Carro> listarTodosOsCarros() {
        return carroRepository.findAll();
    }

    public Page<Carro> listarTodosOsCarrosPaginado(Pageable pageable) {
        return carroRepository.findAll(pageable);
    }

    public Page<Carro> listarTodosOsCarrosPaginadoComTermo(String termo, Pageable pageable) {
        return carroRepository.findAllByNomeModeloContainingIgnoreCaseOrFabricanteContainingIgnoreCase(termo, termo, pageable);
    }

    public List<String> listarTodosOsFabricantes() {
        return carroRepository.findDistinctFabricante();
    }

    public List<Carro> listarTop10Fabricantes() {
        return carroRepository.findTop10ByOrderByFabricanteAsc();
    }

    public Carro atualizarCarro(Carro carro) {
        // Verifica se o carro existe antes de atualizar
        if (!carroRepository.existsById(carro.getCarroId())) {
            throw new CarroNaoEncontradoException(carro.getCarroId());
        }
        return carroRepository.save(carro);
    }

    public void deletarCarro(Long id) {
        // Verifica se o carro existe antes de deletar
        if (!carroRepository.existsById(id)) {
            throw new CarroNaoEncontradoException(id);
        }
        carroRepository.deleteById(id);
    }
}
