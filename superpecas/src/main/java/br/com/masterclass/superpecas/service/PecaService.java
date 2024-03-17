package br.com.masterclass.superpecas.service;

import br.com.masterclass.superpecas.PecaJaExistenteException;
import br.com.masterclass.superpecas.PecaNaoEncontradaException;
import br.com.masterclass.superpecas.model.Carro;
import br.com.masterclass.superpecas.model.Peca;
import br.com.masterclass.superpecas.repository.PecaRepository;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
    public Peca cadastrarPeca(@NotNull Peca peca) {
        // Valida se a peça já existe no sistema
        if (pecaRepository.existsByNomeAndNumeroSerie(peca.getNome(), peca.getNumeroSerie())) {
            throw new PecaJaExistenteException(peca.getNome(), peca.getNumeroSerie());
        }
        // Salva a nova peça no repositório
        return pecaRepository.save(peca);
    }

    // Método para buscar uma peça pelo ID
    public Peca buscarPeca(Long id) {
        return pecaRepository.findById(id)
                .orElseThrow(() -> new PecaNaoEncontradaException(id));
    }

    // Método para atualizar uma peça pelo ID
    public Peca atualizarPeca(Long id, @NotNull Peca peca) {
        // Verifica se a peça existe na base de dados pelo ID
        Peca pecaExistente = pecaRepository.findById(id)
                .orElseThrow(() -> new PecaNaoEncontradaException(id));

        // Verifica se já existe outra peça com o mesmo nome e número de série
        if (pecaRepository.existsByNomeAndNumeroSerieAndIdNot(peca.getNome(), peca.getNumeroSerie(), id)) {
            throw new PecaJaExistenteException(peca.getNome(), peca.getNumeroSerie());
        }

        // Atualiza os atributos da peça existente com os novos valores
        pecaExistente.setNome(peca.getNome());
        pecaExistente.setDescricao(peca.getDescricao());
        pecaExistente.setNumeroSerie(peca.getNumeroSerie());
        pecaExistente.setFabricante(peca.getFabricante());
        pecaExistente.setModeloCarro(peca.getModeloCarro());
        // Continue com os outros atributos, se houver

        // Salva a peça atualizada no repositório
        return pecaRepository.save(pecaExistente);
    }

    // Método para deletar uma peça pelo ID
    public void deletarPeca(Long id) {
        pecaRepository.deleteById(id);
    }

    // Método para listar todas as peças
    public List<Peca> listarTodasAsPecas() {
        return pecaRepository.findAll();
    }

    // Método para listar todas as peças paginadas
    public Page<Peca> listarTodasAsPecasPaginado(Pageable pageable) {
        return (Page<Peca>) pecaRepository.findAll(pageable);
    }

    // Método para listar todas as peças paginadas com um termo de pesquisa
    public Page<Peca> listarTodasAsPecasPaginadoComTermo(String termo, Pageable pageable) {
        return (Page<Peca>) pecaRepository.findByNomeContainingIgnoreCase(termo, pageable);
    }

    // Método para listar os top 10 carros com mais peças
    public List<Carro> listarTop10CarrosComMaisPecas() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Object[]> carrosPage = pecaRepository.findTop10ModelosCarroComMaisPecas(pageable);

        return carrosPage.getContent().stream()
                .map(objArray -> {
                    Carro carro = new Carro();
                    carro.setNomeModelo((String) objArray[0]);
                    return carro;
                })
                .collect(Collectors.toList());
    }
}
