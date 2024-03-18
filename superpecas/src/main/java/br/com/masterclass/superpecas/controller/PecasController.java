package br.com.masterclass.superpecas.controller;

import br.com.masterclass.superpecas.model.DTO.PecaDTO;
import br.com.masterclass.superpecas.model.DTO.TopFabricantesDTO;
import br.com.masterclass.superpecas.service.PecaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/peca")
public class PecasController {

    private final PecaService pecaService;

    @Autowired
    public PecasController(PecaService pecaService) {
        this.pecaService = pecaService;
    }

    @ApiOperation(value = "Busca uma peça pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Peça encontrada"),
            @ApiResponse(code = 404, message = "Peça não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PecaDTO> buscarPeca(@PathVariable Long id) {
        PecaDTO pecaDTO = pecaService.buscarPeca(id);
        return ResponseEntity.ok(pecaDTO);
    }

    @ApiOperation(value = "Lista todas as peças")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de peças retornada com sucesso")
    })
    @GetMapping("/listaTodos")
    public ResponseEntity<List<PecaDTO>> listarTodasAsPecas() {
        List<PecaDTO> pecasDTO = pecaService.listarTodasAsPecas();
        return ResponseEntity.ok(pecasDTO);
    }

    @ApiOperation(value = "Lista todas as peças paginadas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de peças paginada retornada com sucesso")
    })
    @GetMapping("/listaTodosPaginado")
    public ResponseEntity<Page<PecaDTO>> listarTodasAsPecasPaginado(Pageable pageable) {
        Page<PecaDTO> pecas = pecaService.listarTodasAsPecasPaginado(pageable);
        return ResponseEntity.ok(pecas);
    }

    @ApiOperation(value = "Lista todas as peças paginadas com base em um termo de pesquisa")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de peças paginada com base no termo de pesquisa retornada com sucesso")
    })
    @GetMapping("/listaTodosPaginado/{termo}")
    public ResponseEntity<Page<PecaDTO>> listarTodasAsPecasPaginadoComTermo(@PathVariable String termo, Pageable pageable) {
        Page<PecaDTO> pecas = pecaService.listarTodasAsPecasPaginadoComTermo(termo, pageable);
        return ResponseEntity.ok(pecas);
    }



    @ApiOperation(value = "Lista os top 10 fabricantes com mais peças")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista dos top 10 fabricantes com mais peças retornada com sucesso")
    })
    @GetMapping("/listaTop10FabricantesComMaisPecas")
    public ResponseEntity<List<TopFabricantesDTO>> listarTop10FabricantesComMaisPecas() {
        List<TopFabricantesDTO> topFabricantes = pecaService.listarTop10FabricantesComMaisPecas();
        return ResponseEntity.ok(topFabricantes);
    }

    @ApiOperation(value = "Cadastra uma nova peça")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Peça cadastrada com sucesso"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 500, message = "Erro interno ao processar a requisição")
    })
    @PostMapping("/cadastrar")
    public ResponseEntity<PecaDTO> cadastrarPeca(@RequestBody PecaDTO pecaDTO) {
        PecaDTO pecaSalvaDTO = pecaService.cadastrarPeca(pecaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pecaSalvaDTO);
    }

    @ApiOperation(value = "Atualiza uma peça existente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Peça atualizada com sucesso"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Peça não encontrada"),
            @ApiResponse(code = 500, message = "Erro interno ao processar a requisição")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PecaDTO> atualizarPeca(@PathVariable Long id, @RequestBody PecaDTO pecaDTO) {
        PecaDTO pecaAtualizadaDTO = pecaService.atualizarPeca(id, pecaDTO);
        return ResponseEntity.ok(pecaAtualizadaDTO);
    }

    @ApiOperation(value = "Deleta uma peça pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Peça deletada com sucesso"),
            @ApiResponse(code = 404, message = "Peça não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPeca(@PathVariable Long id) {
        pecaService.deletarPeca(id);
        return ResponseEntity.ok().build();
    }
}
