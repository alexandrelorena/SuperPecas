package br.com.masterclass.superpecas.service;

import br.com.masterclass.superpecas.model.Carro;
import br.com.masterclass.superpecas.model.DTO.TopFabricantesDTO;
import br.com.masterclass.superpecas.model.Peca;
import br.com.masterclass.superpecas.repository.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarroService {

    @Autowired
    CarroRepository carroRepository;

    @Autowired
    PecaService pecaService;

    public Carro buscaCarro(int id){
        return carroRepository.findById(id).orElse(null);
    }

    public List<Carro> listaCarros(){
        return (List<Carro>) carroRepository.findAll();
    }

    public Page<Carro> listaCarrosPaginado(Pageable pageable){
        return carroRepository.findAll(pageable);
    }

    public Page<Carro> listaCarrosPorNomeEOuFabricantePaginado(String nome, Pageable pageable){
        return carroRepository.findByNomeModeloEOuFabricante(nome, pageable);
    }

    public List<String> listaFabricantes(){
        return carroRepository.findAllFabricantes();
    }

    public Carro gravaCarro(Carro novoCarro){
        Carro carro = carroRepository.findByNomeModeloOrCodigoUnico(novoCarro.getNomeModelo(), novoCarro.getCodigoUnico());

        if (carro == null){
            return carroRepository.save(novoCarro);
        }

        return null;
    }

    public Carro editaCarro(Carro carroPut){
        Carro carro = carroRepository.findById(carroPut.getCarroID()).orElse(null);

        if (carro == null){
            return null;
        }

        Carro existeMesmoNome = carroRepository.findByNomeModeloOrCodigoUnico(carroPut.getNomeModelo(), carroPut.getCodigoUnico());

        if (existeMesmoNome != null && existeMesmoNome.getCarroID()!= carroPut.getCarroID()){
            return null;
        }

        return carroRepository.save(carroPut);
    }

    public boolean excluiCarro(int id){
        Carro carro = carroRepository.findById(id).orElse(null);

        if (carro == null){
            return false;
        }

        List<Peca> existePeca = pecaService.listaPecasPorCarroId(carro.getCarroID());

        if (existePeca.isEmpty()){
            carroRepository.delete(carro);
            return true;
        } else {
            return false;
        }
    }

    public List<TopFabricantesDTO> listaTop10Fabricantes(){
        return carroRepository.findTop10Fabricantes();
    }
}
