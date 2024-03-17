package br.com.masterclass.superpecas.controller;

import br.com.masterclass.superpecas.model.DTO.CarroDTO;
import br.com.masterclass.superpecas.model.DTO.PecaDTO;
import br.com.masterclass.superpecas.service.PecaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/peca")

public class PecasController {

    private final PecaService pecaService;

    @Autowired
    public PecasController(PecaService pecaService) {
        this.pecaService = pecaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PecaDTO> buscarPeca(@PathVariable Long id) {
        // Chama o serviço para buscar a peça pelo ID
        PecaDTO pecaDTO = pecaService.buscarPeca(id);
        // Retorna a peça encontrada como resposta
        return ResponseEntity.ok(pecaDTO);
    }

    // Método para listar todas as peças
    @GetMapping("/listaTodos")
    public ResponseEntity<List<PecaDTO>> listarTodasAsPecas() {
        List<PecaDTO> pecasDTO = pecaService.listarTodasAsPecas().stream()
                .map(peca -> modelMapper.map(peca, PecaDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(pecasDTO);
    }

    // Método para listar todas as peças paginadas
    @GetMapping("/listaTodosPaginado")
    public ResponseEntity<Page<PecaDTO>> listarTodasAsPecasPaginado(Pageable pageable) {
        Page<PecaDTO> pecas = pecaService.listarTodasAsPecasPaginado(pageable);
        return ResponseEntity.ok(pecas);
    }
    // Método para listar todas as peças paginadas com um termo de pesquisa
    @GetMapping("/listaTodosPaginado/{termo}")
    public ResponseEntity<Page<PecaDTO>> listarTodasAsPecasPaginadoComTermo(@PathVariable String termo, Pageable pageable) {
        Page<PecaDTO> pecas = pecaService.listarTodasAsPecasPaginadoComTermo(termo, pageable);
        return ResponseEntity.ok(pecas);
    }

    // Método para listar os top 10 carros com mais peças
    @GetMapping("/listaTop10CarroComMaisPecas")
    public ResponseEntity<List<CarroDTO>> listarTop10CarrosComMaisPecas() {
        List<CarroDTO> topCarros = pecaService.listarTop10CarrosComMaisPecas();
        return ResponseEntity.ok(topCarros);
    }

    @Autowired
    private ModelMapper modelMapper; // Injeção de dependência do ModelMapper

    @PostMapping("/pecas")
    public ResponseEntity<PecaDTO> cadastrarPeca(@RequestBody PecaDTO pecaDTO) {
        // Cadastra a peça usando o serviço, passando o PecaDTO
        PecaDTO pecaSalvaDTO = pecaService.cadastrarPeca(pecaDTO);
        // Retorna a peça cadastrada como resposta
        return ResponseEntity.status(HttpStatus.CREATED).body(pecaSalvaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PecaDTO> atualizarPeca(@PathVariable Long id, @RequestBody PecaDTO pecaDTO) {
        // Chama o serviço para atualizar a peça pelo ID
        PecaDTO pecaAtualizadaDTO = pecaService.atualizarPeca(id, pecaDTO);
        // Retorna a peça atualizada como resposta
        return ResponseEntity.ok(pecaAtualizadaDTO);
    }

    @DeleteMapping("/peca/{id}")
    public ResponseEntity<Void> deletarPeca(@PathVariable Long id) {
        // Chama o serviço para deletar a peça pelo ID
        pecaService.deletarPeca(id);
        // Retorna uma resposta vazia com status OK
        return ResponseEntity.ok().build();
    }
}
