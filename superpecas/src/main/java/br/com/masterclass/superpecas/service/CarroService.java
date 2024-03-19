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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// Classe de serviço para lidar com operações relacionadas a carros
@Service
public class CarroService {

    // Injeção de dependência do repositório de carros
    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PecaRepository pecaRepository; // Injeção de dependência para o PecaRepository

    // Método para cadastrar um novo carro
    public CarroDTO cadastrarCarro(CarroDTO carroDTO) {
        if (carroJaExiste(carroDTO)) {
            throw new CarroJaExistenteException(carroDTO.getNomeModelo(), carroDTO.getFabricante(), carroDTO.getCodigoUnico());
        }
        Carro carro = modelMapper.map(carroDTO, Carro.class);
        return modelMapper.map(carroRepository.save(carro), CarroDTO.class);
    }

    // Validar se um carro já existe no sistema
    private boolean carroJaExiste(@NotNull CarroDTO carroDTO) {
        String nomeModelo = carroDTO.getNomeModelo();
        String fabricante = carroDTO.getFabricante();
        String codigoUnico = carroDTO.getCodigoUnico();
        return carroRepository.existsByNomeModeloAndFabricanteAndCodigoUnico(carroDTO.getNomeModelo(), carroDTO.getFabricante(), carroDTO.getCodigoUnico());
    }

    // Método para buscar um carro pelo ID e retornar um DTO
    public CarroDTO buscarCarro(Long id) {
        Carro carro = carroRepository.findById(id)
                .orElseThrow(() -> new CarroNaoEncontradoException(id));
        return modelMapper.map(carro, CarroDTO.class);
    }

    // Método para listar todos os carros e retornar como DTOs
    public List<CarroDTO> listarTodosOsCarros() {
        List<Carro> carros = carroRepository.findAll();
        return carros.stream()
                .map(carro -> modelMapper.map(carro, CarroDTO.class))
                .collect(Collectors.toList());
    }

    // Método para listar todos os carros paginados e retornar como DTOs
    public Page<CarroDTO> listarTodosOsCarrosPaginado(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Carro> carrosPage = carroRepository.findAll(pageable);
        return carrosPage.map(carro -> modelMapper.map(carro, CarroDTO.class));
    }

    // Método para listar todos os carros paginados com um termo de pesquisa
    public Page<CarroDTO> listarTodosOsCarrosPaginadoComTermo(String termo, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Carro> carrosPage = carroRepository.findAllByNomeModeloContainingIgnoreCaseOrFabricanteContainingIgnoreCase(termo, termo, pageable);
        return carrosPage.map(carro -> modelMapper.map(carro, CarroDTO.class));
    }

    // Método para listar todos os fabricantes de carros
    public List<String> listarTodosOsFabricantes() {
        return carroRepository.findDistinctFabricante();
    }

    // Método para listar os top 10 fabricantes de carros
    public List<String> listarTop10Fabricantes() {
        return carroRepository.findTop10DistinctFabricanteByOrderByFabricanteAsc();
    }

    // Método para atualizar um carro
    public CarroDTO atualizarCarro(Long id, @NotNull CarroDTO carroDTO) {
        // Verifica se o carro existe antes de atualizá-lo
        Carro carroExistente = carroRepository.findById(id)
                .orElseThrow(() -> new CarroNaoEncontradoException(id));

        // Atualiza os atributos do carro existente com os novos valores do DTO
        carroExistente.setNomeModelo(carroDTO.getNomeModelo());
        carroExistente.setFabricante(carroDTO.getFabricante());
        carroExistente.setCodigoUnico(carroDTO.getCodigoUnico());

        // Salva o carro atualizado no repositório
        carroExistente = carroRepository.save(carroExistente);

        // Converte o carro atualizado para um DTO e retorna
        return modelMapper.map(carroExistente, CarroDTO.class);
    }

    // Método para deletar um carro pelo ID
    public void deletarCarro(Long id) {
        // Verifica se o carro existe antes de tentar deletá-lo
        Carro carro = carroRepository.findById(id)
                .orElseThrow(() -> new CarroNaoEncontradoException(id));

        // Verifica se existem peças associadas a este carro pelo CarroID
        List<Peca> pecasAssociadas = pecaRepository.findByCarro_CarroID(carro.getCarroID());
        if (!pecasAssociadas.isEmpty()) {
            throw new IllegalStateException("Não é possível excluir o carro pois existem peças associadas a ele.");
        }

        // Deleta o carro do repositório
        carroRepository.deleteById(id);
    }

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
