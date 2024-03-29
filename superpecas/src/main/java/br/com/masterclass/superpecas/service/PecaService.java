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
        Peca peca = pecaRepository.findById(pecaPut.getPecaID()).orElse(null);

        if (peca == null){
            return null;
        }

        Peca existeMesmoNome = pecaRepository.findByNomeAndNumeroSerie(pecaPut.getNome(), pecaPut.getNumeroSerie());

        if (existeMesmoNome != null && existeMesmoNome.getPecaID()!= pecaPut.getPecaID()){
            return null;
        }

        return pecaRepository.save(peca);
    }

    public void excluiPeca(int id){
        Peca peca = pecaRepository.findById(id).orElse(null);
        if (peca != null)
            pecaRepository.delete(peca);
    }

    public List<Peca> listaPecasPorCarroId(int carroId){
        return pecaRepository.findByCarroId(carroId);
    }

    public List<TopCarroPecasDTO> listaTop10CarrosComMaisPecas(){
        return pecaRepository.findTop10CarrosComMaisPecas();
    }

}


// @Service
// public class PecaService {

//     @Autowired
//     private PecaRepository pecaRepository;

//     @Autowired
//     public PecaService(PecaRepository pecaRepository) {
//         this.pecaRepository = pecaRepository;
//     }

//     public Page<Peca> listarPecasPaginado(int page, int size) {
//         Pageable pageable = PageRequest.of(page, size);
//         return pecaRepository.findAll(pageable);
//     }

//     public Page<PecaDTO> buscarPecasPorTermo(String termo, int page, int size) {
//         Pageable pageable = PageRequest.of(page, size);
//         Page<Peca> pecasPage = pecaRepository.findAll(pageable);
//         return pecasPage.map(peca -> modelMapper.map(peca, PecaDTO.class));

//     }
//     @Autowired
//     private ModelMapper modelMapper; 

//     public PecaDTO cadastrarPeca(PecaDTO pecaDTO) {
//         if (pecaRepository.existsByNomeAndNumeroSerie(pecaDTO.getNome(), pecaDTO.getNumeroSerie())) {
//             throw new PecaJaExistenteException(pecaDTO.getNome(), pecaDTO.getNumeroSerie());
//         }
//         Peca peca = modelMapper.map(pecaDTO, Peca.class);
//         peca = pecaRepository.save(peca);
//         return modelMapper.map(peca, PecaDTO.class);
//     }

//     public PecaDTO buscarPeca(Long id) {
//         Peca peca = pecaRepository.findById(id)
//                 .orElseThrow(() -> new PecaNaoEncontradaException(id));
//         return modelMapper.map(peca, PecaDTO.class);
//     }

//     public PecaDTO atualizarPeca(Long id, @NotNull PecaDTO pecaDTO) {
//         Peca pecaExistente = pecaRepository.findById(id)
//                 .orElseThrow(() -> new PecaNaoEncontradaException(id));

//         if (pecaRepository.existsByNomeAndNumeroSerieAndPecaIDNot(pecaDTO.getNome(), pecaDTO.getNumeroSerie(), id)) {
//             throw new PecaJaExistenteException(pecaDTO.getNome(), pecaDTO.getNumeroSerie());
//         }

//         Peca peca = modelMapper.map(pecaDTO, Peca.class);

//         pecaExistente.setNome(peca.getNome());
//         pecaExistente.setDescricao(peca.getDescricao());
//         pecaExistente.setNumeroSerie(peca.getNumeroSerie());
//         pecaExistente.setFabricante(peca.getFabricante());
//         pecaExistente.setModeloCarro(peca.getModeloCarro());

//         pecaExistente = pecaRepository.save(pecaExistente);

//         return modelMapper.map(pecaExistente, PecaDTO.class);
//     }

//     public void deletarPeca(Long id) {
//         pecaRepository.deleteById(id);
//     }

//     public List<PecaDTO> listarTodasAsPecas() {
//         List<Peca> pecas = pecaRepository.findAll();
//         return pecas.stream()
//                 .map(peca -> modelMapper.map(peca, PecaDTO.class))
//                 .collect(Collectors.toList());
//     }

//     public Page<PecaDTO> listarTodasAsPecasPaginado(int page, int size) {
//         Pageable pageable = PageRequest.of(page, size);
//         Page<Peca> pecasPage = pecaRepository.findAll(pageable);
//         return pecasPage.map(peca -> modelMapper.map(peca, PecaDTO.class));
//     }

//     public Page<PecaDTO> listarTodasAsPecasPaginadoComTermo(String termo, int page, int size) {
//         Pageable pageable = PageRequest.of(page, size);
//         Page<Peca> pecasPage = pecaRepository.findByModeloCarroContainingIgnoreCaseOrFabricanteContainingIgnoreCaseOrNomeContainingIgnoreCase(termo, termo, termo, pageable);
//         return pecasPage.map(peca -> modelMapper.map(peca, PecaDTO.class));
//     }


//     public List<TopFabricantesDTO> listarTop10FabricantesComMaisPecas() {
//         List<Object[]> fabricantes = pecaRepository.findTop10FabricantesComMaisPecas();

//         List<TopFabricantesDTO> fabricantesDTO = new ArrayList<>();

//         for (Object[] objArray : fabricantes) {
//             TopFabricantesDTO fabricanteDTO = new TopFabricantesDTO() {
//                 @Override
//                 public Integer getQuantidade() {
//                     return ((Long) objArray[0]).intValue();
//                 }

//                 @Override
//                 public String getFabricante() {
//                     return (String) objArray[1];
//                 }
//             };
//             fabricantesDTO.add(fabricanteDTO);
//         }

//         return fabricantesDTO;
//     }

// }
