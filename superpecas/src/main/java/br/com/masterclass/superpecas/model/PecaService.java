package br.com.masterclass.superpecas.model;

import br.com.masterclass.superpecas.Carro;
import br.com.masterclass.superpecas.Peca;
import br.com.masterclass.superpecas.PecaJaExistenteException;
import br.com.masterclass.superpecas.PecaNaoEncontradaException;
import br.com.masterclass.superpecas.repository.PecaRepository;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PecaService {

    @Autowired
    private PecaRepository pecaRepository;

    public Peca cadastrarPeca(Peca peca) {
        if (pecaRepository.existsByNomeAndNumeroSerie(peca.getNome(), peca.getNumeroserie())) {
            throw new PecaJaExistenteException(peca.getNome(), peca.getNumeroserie());
        }
        return pecaRepository.save(peca);
    }

    public Peca buscarPeca(Long id) {
        return pecaRepository.findById(id)
                .orElseThrow(() -> new PecaNaoEncontradaException(id));
    }

    public Peca atualizarPeca(Long id, Peca peca) {
        return pecaRepository.save(peca);
    }

    public void deletarPeca(Long id) {
        pecaRepository.deleteById(id);
    }

    public List<Peca> listarTodasAsPecas() {
        return pecaRepository.findAll();
    }

    public Page<Peca> listarTodasAsPecasPaginado(Pageable pageable) {
        return (Page<Peca>) pecaRepository.findAll(pageable);
    }

    public Page<Peca> listarTodasAsPecasPaginadoComTermo(String termo, Pageable pageable) {
        return (Page<Peca>) pecaRepository.findByNomeContainingIgnoreCase(termo, pageable);
    }

    public List<Carro> listarTop10CarrosComMaisPecas() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Object[]> carrosPage = pecaRepository.findTop10ModelosCarroComMaisPecas(pageable);

        List<Carro> carros = carrosPage.getContent().stream()
                .map(objArray -> {
                    Carro carro = new Carro();
                    carro.setNomeModelo((String) objArray[0]);
                    return carro;
                })
                .collect(Collectors.toList());

        return carros;
    }
}
