package br.com.masterclass.superpecas;

import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PecaService {

    @Autowired
    private PecaRepository pecaRepository;

    public Peca cadastrarPeca(Peca peca) {
        if (pecaRepository.existeByNomeAndNumeroSerie(
                peca.getNome(), peca.getDescricao(), peca.getNumeroserie(), peca.getFabricante(), peca.getModelocarro())) {
            throw new PecaJaExistenteException(peca.getNome(), peca.getNumeroserie());
        }
        return pecaRepository.save(peca);
    }

    public Peca buscarPeca(Long id) {
        return pecaRepository.findById(id)
                .orElseThrow(() -> new PecaNaoEncontradaException(id));
    }

    public Peca atualizarPeca(Peca peca) {
        return pecaRepository.save(peca);
    }

    public void deletarPeca(Peca peca) {
        pecaRepository.delete(peca);
    }
}
