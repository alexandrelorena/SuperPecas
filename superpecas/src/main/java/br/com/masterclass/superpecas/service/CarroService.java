package br.com.masterclass.superpecas.service;

import br.com.masterclass.superpecas.CarroJaExistenteException;
import br.com.masterclass.superpecas.CarroNaoEncontradoException;
import br.com.masterclass.superpecas.model.Carro;
import br.com.masterclass.superpecas.model.DTO.CarroDTO;
import br.com.masterclass.superpecas.model.Peca;
import br.com.masterclass.superpecas.repository.CarroRepository;
import br.com.masterclass.superpecas.repository.PecaRepository;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

// Classe de serviço para lidar com operações relacionadas a carros
@Component
public class CarroService {

    // Injeção de dependência do repositório de carros
    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private PecaRepository pecaRepository; // Injeção de dependência para o PecaRepository



    // Método para cadastrar um novo carro
    public Carro cadastrarCarro(Carro carro) {
        // Valida se o carro já existe no sistema
        if (carroJaExiste(carro)) {
            throw new CarroJaExistenteException(carro.getNomeModelo(), carro.getFabricante(), carro.getCodigoUnico());
        }
        // Salva o novo carro no repositório
        return carroRepository.save(carro);
    }

    // Validar se um carro já existe no sistema
    private boolean carroJaExiste(@NotNull Carro carro) {
        String nomeModelo = carro.getNomeModelo();
        String fabricante = carro.getFabricante();
        String codigoUnico = carro.getCodigoUnico();
        return carroRepository.existsByNomeModeloAndFabricanteAndCodigoUnico(nomeModelo, fabricante, codigoUnico);
    }

    // Método para buscar um carro pelo ID
    public Carro buscarCarro(Long id) {
        return carroRepository.findById(id)
                .orElseThrow(() -> new CarroNaoEncontradoException(id));
    }

    // Método para listar todos os carros
    public List<Carro> listarTodosOsCarros() {
        return carroRepository.findAll();
    }

    // Método para listar todos os carros paginados
    public Page<Carro> listarTodosOsCarrosPaginado(Pageable pageable) {
        return carroRepository.findAll(pageable);
    }

    // Método para listar todos os carros paginados com um termo de pesquisa
    public Page<Carro> listarTodosOsCarrosPaginadoComTermo(String termo, Pageable pageable) {
        return carroRepository.findAllByNomeModeloContainingIgnoreCaseOrFabricanteContainingIgnoreCase(termo, termo, pageable);
    }

    // Método para listar todos os fabricantes de carros
    public List<String> listarTodosOsFabricantes() {
        return carroRepository.findDistinctFabricante();
    }

    // Método para listar os top 10 fabricantes de carros
    public List<Carro> listarTop10Fabricantes() {
        return carroRepository.findTop10ByOrderByFabricanteAsc();
    }

    // Método para atualizar um carro
    public Carro atualizarCarro(@NotNull Carro carro) {
        // Valida se o carro existe antes de atualizá-lo
        if (!carroRepository.existsById(carro.getCarroId())) {
            throw new CarroNaoEncontradoException(carro.getCarroId());
        }
        // Atualiza o carro no repositório
        return carroRepository.save(carro);
    }

    // Método para deletar um carro pelo ID
    public void deletarCarro(Long id) {
        // Verifica se o carro existe antes de tentar deletá-lo
        Carro carro = carroRepository.findById(id)
                .orElseThrow(() -> new CarroNaoEncontradoException(id));

        // Verifica se existem peças associadas a este carro pelo CarroID
        List<Peca> pecasAssociadas = pecaRepository.findByCarroId(carro.getCarroId());
        if (!pecasAssociadas.isEmpty()) {
            throw new IllegalStateException("Não é possível excluir o carro pois existem peças associadas a ele.");
        }

        // Deleta o carro do repositório
        carroRepository.deleteById(id);
    }

    // Injeção de dependência do ModelMapper para converter entidades em DTOs
    @Autowired
    private ModelMapper modelMapper;

    // Método para converter um objeto Carro em um objeto CarroDTO
    public CarroDTO convertToDto(Carro carro) {
        return modelMapper.map(carro, CarroDTO.class);
    }

    // Método para converter uma lista de objetos Carro em uma lista de objetos CarroDTO
    private List<CarroDTO> convertToDtoList(@NotNull List<Carro> carros) {
        return carros.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
