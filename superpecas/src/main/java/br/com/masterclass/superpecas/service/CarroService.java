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

// package br.com.masterclass.superpecas.service;

// import br.com.masterclass.superpecas.CarroJaExistenteException;
// import br.com.masterclass.superpecas.CarroNaoEncontradoException;
// import br.com.masterclass.superpecas.model.Carro;
// import br.com.masterclass.superpecas.model.DTO.CarroDTO;
// import br.com.masterclass.superpecas.model.Peca;
// import br.com.masterclass.superpecas.repository.CarroRepository;
// import br.com.masterclass.superpecas.repository.PecaRepository;
// import org.jetbrains.annotations.NotNull;
// import org.modelmapper.ModelMapper;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Pageable;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.stream.Collectors;

// @Service
// public class CarroService {

//     @Autowired
//     private CarroRepository carroRepository;

//     @Autowired
//     private ModelMapper modelMapper;

//     @Autowired
//     private PecaRepository pecaRepository;

//     public CarroDTO cadastrarCarro(CarroDTO carroDTO) {
//         if (carroJaExiste(carroDTO)) {
//             throw new CarroJaExistenteException(carroDTO.getNomeModelo(), carroDTO.getFabricante(), carroDTO.getCodigoUnico());
//         }
//         Carro carro = modelMapper.map(carroDTO, Carro.class);
//         return modelMapper.map(carroRepository.save(carro), CarroDTO.class);
//     }

//     private boolean carroJaExiste(@NotNull CarroDTO carroDTO) {
//         String nomeModelo = carroDTO.getNomeModelo();
//         String fabricante = carroDTO.getFabricante();
//         String codigoUnico = carroDTO.getCodigoUnico();
//         return carroRepository.existsByNomeModeloAndFabricanteAndCodigoUnico(carroDTO.getNomeModelo(), carroDTO.getFabricante(), carroDTO.getCodigoUnico());
//     }

//     public CarroDTO buscarCarro(Long id) {
//         Carro carro = carroRepository.findById(id)
//                 .orElseThrow(() -> new CarroNaoEncontradoException(id));
//         return modelMapper.map(carro, CarroDTO.class);
//     }

//     public List<CarroDTO> listarTodosOsCarros() {
//         List<Carro> carros = carroRepository.findAll();
//         return carros.stream()
//                 .map(carro -> modelMapper.map(carro, CarroDTO.class))
//                 .collect(Collectors.toList());
//     }

//     public Page<CarroDTO> listarTodosOsCarrosPaginado(int page, int size) {
//         Pageable pageable = PageRequest.of(page, size);
//         Page<Carro> carrosPage = carroRepository.findAll(pageable);
//         return carrosPage.map(carro -> modelMapper.map(carro, CarroDTO.class));
//     }

//     public Page<CarroDTO> listarTodosOsCarrosPaginadoComTermo(String termo, int page, int size) {
//         Pageable pageable = PageRequest.of(page, size);
//         Page<Carro> carrosPage = carroRepository.findAllByNomeModeloContainingIgnoreCaseOrFabricanteContainingIgnoreCase(termo, termo, pageable);
//         return carrosPage.map(carro -> modelMapper.map(carro, CarroDTO.class));
//     }

//     public List<String> listarTodosOsFabricantes() {
//         return carroRepository.findDistinctFabricante();
//     }

//     public List<String> listarTop10Fabricantes() {
//         return carroRepository.findTop10DistinctFabricanteByOrderByFabricanteAsc();
//     }

//     public CarroDTO atualizarCarro(Long id, @NotNull CarroDTO carroDTO) {
//         Carro carroExistente = carroRepository.findById(id)
//                 .orElseThrow(() -> new CarroNaoEncontradoException(id));

//         carroExistente.setNomeModelo(carroDTO.getNomeModelo());
//         carroExistente.setFabricante(carroDTO.getFabricante());
//         carroExistente.setCodigoUnico(carroDTO.getCodigoUnico());

//         carroExistente = carroRepository.save(carroExistente);

//         return modelMapper.map(carroExistente, CarroDTO.class);
//     }

//     public void deletarCarro(Long id) {
//         Carro carro = carroRepository.findById(id)
//                 .orElseThrow(() -> new CarroNaoEncontradoException(id));

//         List<Peca> pecasAssociadas = pecaRepository.findByCarro_CarroID(carro.getCarroID());
//         if (!pecasAssociadas.isEmpty()) {
//             throw new IllegalStateException("Não é possível excluir o carro pois existem peças associadas a ele.");
//         }

//         carroRepository.deleteById(id);
//     }

//     public CarroDTO convertToDto(Carro carro) {
//         return modelMapper.map(carro, CarroDTO.class);
//     }

//     private List<CarroDTO> convertToDtoList(@NotNull List<Carro> carros) {
//         return carros.stream()
//                 .map(this::convertToDto)
//                 .collect(Collectors.toList());
//     }
// }
