package br.com.masterclass.superpecas.service;

import br.com.masterclass.superpecas.PecaJaExistenteException;
import br.com.masterclass.superpecas.PecaNaoEncontradaException;
import br.com.masterclass.superpecas.model.DTO.CarroDTO;
import br.com.masterclass.superpecas.model.DTO.PecaDTO;
import br.com.masterclass.superpecas.model.Peca;
import br.com.masterclass.superpecas.repository.PecaRepository;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// Classe de serviço para operações relacionadas a peças
@Service
public class PecaService {

    @Autowired
    private PecaRepository pecaRepository;

    @Autowired
    private ModelMapper modelMapper; // Injeção de dependência do ModelMapper

    // Método para cadastrar uma nova peça
    public PecaDTO cadastrarPeca(@NotNull PecaDTO pecaDTO) {
        // Valida se a peça já existe no sistema
        if (pecaRepository.existsByNomeAndNumeroSerie(pecaDTO.getNome(), pecaDTO.getNumeroSerie())) {
            throw new PecaJaExistenteException(pecaDTO.getNome(), pecaDTO.getNumeroSerie());
        }
        // Mapeia o PecaDTO para uma instância de Peca
        Peca peca = modelMapper.map(pecaDTO, Peca.class);
        // Salva a nova peça no repositório
        peca = pecaRepository.save(peca);
        // Mapeia a peça salva de volta para um PecaDTO
        return modelMapper.map(peca, PecaDTO.class);
    }

    // Método para buscar uma peça pelo ID
    public PecaDTO buscarPeca(Long id) {
        Peca peca = pecaRepository.findById(id)
                .orElseThrow(() -> new PecaNaoEncontradaException(id));
        // Mapeia a entidade Peca para um DTO PecaDTO
        return modelMapper.map(peca, PecaDTO.class);
    }

    // Método para atualizar uma peça pelo ID
    public PecaDTO atualizarPeca(Long id, @NotNull PecaDTO pecaDTO) {
        // Verifica se a peça existe na base de dados pelo ID
        Peca pecaExistente = pecaRepository.findById(id)
                .orElseThrow(() -> new PecaNaoEncontradaException(id));

        // Verifica se já existe outra peça com o mesmo nome e número de série
        if (pecaRepository.existsByNomeAndNumeroSerieAndPecaIDNot(pecaDTO.getNome(), pecaDTO.getNumeroSerie(), id)) {
            throw new PecaJaExistenteException(pecaDTO.getNome(), pecaDTO.getNumeroSerie());
        }

        // Mapeia o DTO para uma instância de Peca
        Peca peca = modelMapper.map(pecaDTO, Peca.class);

        // Atualiza os atributos da peça existente com os novos valores
        pecaExistente.setNome(peca.getNome());
        pecaExistente.setDescricao(peca.getDescricao());
        pecaExistente.setNumeroSerie(peca.getNumeroSerie());
        pecaExistente.setFabricante(peca.getFabricante());
        pecaExistente.setModeloCarro(peca.getModeloCarro());

        // Salva a peça atualizada no repositório
        pecaExistente = pecaRepository.save(pecaExistente);

        // Mapeia a peça atualizada de volta para um DTO PecaDTO
        return modelMapper.map(pecaExistente, PecaDTO.class);
    }

    // Método para deletar uma peça pelo ID
    public void deletarPeca(Long id) {
        pecaRepository.deleteById(id);
    }

    // Método para listar todas as peças
    public List<PecaDTO> listarTodasAsPecas() {
        List<Peca> pecas = pecaRepository.findAll();
        return pecas.stream()
                .map(peca -> modelMapper.map(peca, PecaDTO.class))
                .collect(Collectors.toList());
    }

    // Método para listar todas as peças paginadas
    public Page<PecaDTO> listarTodasAsPecasPaginado(Pageable pageable) {
        Page<Peca> pecasPage = pecaRepository.findAll(pageable);
        return pecasPage.map(peca -> modelMapper.map(peca, PecaDTO.class));
    }

    // Método para listar todas as peças paginadas com um termo de pesquisa
    public Page<PecaDTO> listarTodasAsPecasPaginadoComTermo(String termo, Pageable pageable) {
        Page<Peca> pecasPage = pecaRepository.findByNomeContainingIgnoreCase(termo, pageable);
        return pecasPage.map(peca -> modelMapper.map(peca, PecaDTO.class));
    }

    // Método para listar os top 10 carros com mais peças
    public List<CarroDTO> listarTop10CarrosComMaisPecas() {
        List<Object[]> carros = pecaRepository.findTop10ModelosCarroComMaisPecas();

        return carros.stream()
                .map(objArray -> {
                    CarroDTO carroDTO = new CarroDTO();
                    carroDTO.setNomeModelo((String) objArray[0]);
                    return carroDTO;
                })
                .collect(Collectors.toList());
    }
}
