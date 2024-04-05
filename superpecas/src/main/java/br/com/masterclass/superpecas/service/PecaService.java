package br.com.masterclass.superpecas.service;

import br.com.masterclass.superpecas.model.Carro;
import br.com.masterclass.superpecas.model.DTO.TopCarroPecasDTO;
import br.com.masterclass.superpecas.model.DTO.TopFabricantesDTO;
import br.com.masterclass.superpecas.model.Peca;
import br.com.masterclass.superpecas.repository.CarroRepository;
import br.com.masterclass.superpecas.repository.PecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PecaService {

    @Autowired
    PecaRepository pecaRepository;

    public Peca buscaPeca(int id){
        return pecaRepository.findById(id).orElse(null);
    }

    public List<Peca> listaPecas(){
        return  pecaRepository.findAll();
    }

    public Page<Peca> listaPecasPaginado(Pageable pageable){
        return pecaRepository.findAll(pageable);
    }

    public Page<Peca> listaPecasPorNomeEOuNumeroSerie(String termo, Pageable pageable){
        return pecaRepository.findByNomeOrNumeroSerieOrFabricanteOrModeloCarro(termo, pageable);
    }

    public Peca gravaPeca(Peca novaPeca){
        Peca peca = pecaRepository.findByNomeAndNumeroSerie(novaPeca.getNome(), novaPeca.getNumeroSerie());

        if (peca == null){
            return pecaRepository.save(novaPeca);
        }

        return null;
    }

    public Peca editaPeca(Peca pecaPut){
        Peca peca = pecaRepository.findById(pecaPut.getPecaId()).orElse(null);

        if (peca == null){
            return null;
        }

        Peca existeMesmoNome = pecaRepository.findByNomeAndNumeroSerie(pecaPut.getNome(), pecaPut.getNumeroSerie());

        if (existeMesmoNome != null && existeMesmoNome.getPecaId()!= pecaPut.getPecaId()){
            return null;
        }

        return pecaRepository.save(pecaPut);
    }

    public void excluiPeca(int id){
        Peca peca = pecaRepository.findById(id).orElse(null);
        if (peca != null)
            pecaRepository.delete(peca);
    }

    public List<Peca> listaPecasPorCarroId(int carroId){
        return pecaRepository.findByCarro_CarroId(carroId);
    }

    public List<TopCarroPecasDTO> listaTop10CarrosComMaisPecas(){
        return pecaRepository.findTop10CarrosComMaisPecas();
    }

}
