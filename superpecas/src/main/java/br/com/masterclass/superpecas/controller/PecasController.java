package br.com.masterclass.superpecas.controller;

import br.com.masterclass.superpecas.model.Carro;
import br.com.masterclass.superpecas.model.DTO.CarroDTO;
import br.com.masterclass.superpecas.model.DTO.PecaDTO;
import br.com.masterclass.superpecas.model.DTO.TopCarroPecasDTO;
import br.com.masterclass.superpecas.model.DTO.TopFabricantesDTO;
import br.com.masterclass.superpecas.model.Peca;
import br.com.masterclass.superpecas.service.CarroService;
import br.com.masterclass.superpecas.service.PecaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/peca")
public class PecasController {

    @Autowired
    PecaService pecaService;

    @Autowired
    ModelMapper modelMapper;

    @Operation(summary = "Lista peça por id", description = "Lista peça pelo seu id")
    @ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PecaDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Peça não encontrada", content = @Content) })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PecaDTO> buscaPeca(@PathVariable int id) {
        Peca peca = pecaService.buscaPeca(id);

        if (peca == null){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }

        return new ResponseEntity<>(modelMapper.map(peca, PecaDTO.class), HttpStatus.OK);
    }

    @Operation(summary = "Lista todas as peças", description = "Lista todas as peças.")
    @ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PecaDTO[].class)) })})
    @RequestMapping(value = "/listaTodos", method = RequestMethod.GET)
    public ResponseEntity<List<PecaDTO>> listaPecas() {
        List<Peca> pecas = pecaService.listaPecas();

        List<PecaDTO> listaDTO = Arrays.asList(modelMapper.map(pecas, PecaDTO[].class));

        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }

    @Operation(summary = "Lista peças paginado", description = "Lista todas as peças por página.")
    @ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PecaDTO[].class)) })})
    @RequestMapping(value = "/listaTodosPaginado", method = RequestMethod.GET)
    public ResponseEntity<Page<PecaDTO>> listaPecasPaginado(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Peca> pecas = pecaService.listaPecasPaginado(paging);
        return new ResponseEntity<>(pecas.map(peca -> modelMapper.map(peca, PecaDTO.class)), HttpStatus.OK);
    }

    @Operation(summary = "Lista peças por nome ou número de série paginado", description = "Lista todas as peças por nome e/ou número de série por página.")
    @ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PecaDTO[].class)) })})
    @RequestMapping(value = {"/listaTodosPaginado/{termo}" }, method = RequestMethod.GET)
    public ResponseEntity<Page<PecaDTO>> listaPecasPorNomeEOuNumeroSeriePaginado(@PathVariable String termo, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Peca> pecas = pecaService.listaPecasPorNomeEOuNumeroSerie(termo, paging);
        return new ResponseEntity<>(pecas.map(peca -> modelMapper.map(peca, PecaDTO.class)), HttpStatus.OK);
    }

    @Operation(summary = "Grava peça", description = "Grava uma nova peça no sistema.")
    @ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PecaDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content) })
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PecaDTO> gravaPeca(@RequestBody PecaDTO data) {
        Peca peca = modelMapper.map(data, Peca.class);
        peca = pecaService.gravaPeca(peca);

        if (peca == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(modelMapper.map(peca, PecaDTO.class), HttpStatus.OK);
    }

    @Operation(summary = "Atualiza peça", description = "Atualiza dados de uma peça existente no sistema.")
    @ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PecaDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content) })
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<PecaDTO> editaPeca(@RequestBody PecaDTO data) {
        Peca peca = modelMapper.map(data, Peca.class);
        peca = pecaService.editaPeca(peca);

        if (peca == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(modelMapper.map(peca, PecaDTO.class), HttpStatus.OK);
    }

    @Operation(summary = "Excluir peça", description = "Exclui um peça do sistema.")
    @ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PecaDTO.class)) })})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity excluiPeca(@PathVariable int id) {
         pecaService.excluiPeca(id);
         return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Lista TOP 10 Carros com mais peças", description = "Lista TOP 10 Carros com mais peças.")
    @ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = TopFabricantesDTO[].class)) })})
    @RequestMapping(value = "/listaTop10CarroComMaisPecas", method = RequestMethod.GET)
    public ResponseEntity<List<TopCarroPecasDTO>> listaTop10CarroComMaisPecas() {
        List<TopCarroPecasDTO> carros = pecaService.listaTop10CarrosComMaisPecas();
        return new ResponseEntity<>(carros, HttpStatus.OK);
    }

}

//     private final PecaService pecaService;

//     @Autowired
//     public PecasController(PecaService pecaService) {
//         this.pecaService = pecaService;
//     }

//     @ApiOperation(value = "Busca uma peça pelo ID")
//     @ApiResponses(value = {
//             @ApiResponse(code = 200, message = "Peça encontrada"),
//             @ApiResponse(code = 404, message = "Peça não encontrada")
//     })
//     @GetMapping("/{id}")
//     public ResponseEntity<PecaDTO> buscarPeca(@PathVariable Long id) {
//         PecaDTO pecaDTO = pecaService.buscarPeca(id);
//         return ResponseEntity.ok(pecaDTO);
//     }

//     @ApiOperation(value = "Lista todas as peças")
//     @ApiResponses(value = {
//             @ApiResponse(code = 200, message = "Lista de peças retornada com sucesso")
//     })
//     @GetMapping("/listaTodos")
//     public ResponseEntity<List<PecaDTO>> listarTodasAsPecas() {
//         List<PecaDTO> pecasDTO = pecaService.listarTodasAsPecas();
//         return ResponseEntity.ok(pecasDTO);
//     }

//     @ApiOperation(value = "Lista todas as peças paginadas")
//     @ApiResponses(value = {
//             @ApiResponse(code = 200, message = "Lista de peças paginada retornada com sucesso")
//     })

//     @GetMapping("/listaTodosPaginado")
//     public ResponseEntity<Page<PecaDTO>> listarPecasPaginado(Pageable pageable) {
//         int page = pageable.getPageNumber();
//         int size = 10;
//         Page<PecaDTO> pecas = pecaService.listarTodasAsPecasPaginado(page, size);
//         return ResponseEntity.ok(pecas);
//     }

//     @ApiOperation(value = "Lista todas as peças paginadas com base em um termo de pesquisa")
//     @ApiResponses(value = {
//             @ApiResponse(code = 200, message = "Lista de peças paginada com base no termo de pesquisa retornada com sucesso")
//     })
//     @GetMapping("/listaTodosPaginado/{termo}")
//     public ResponseEntity<Page<PecaDTO>> listarTodasAsPecasPaginadoComTermo(@PathVariable String termo, Pageable pageable) {
//         int page = pageable.getPageNumber();
//         int size = 10;
//         Page<PecaDTO> pecas = pecaService.listarTodasAsPecasPaginadoComTermo(termo, page, size);
//         return ResponseEntity.ok(pecas);
//     }

//     @ApiOperation(value = "Lista os top 10 fabricantes com mais peças")
//     @ApiResponses(value = {
//             @ApiResponse(code = 200, message = "Lista dos top 10 fabricantes com mais peças retornada com sucesso")
//     })
//     @GetMapping("/listaTop10CarroComMaisPeças")
//     public ResponseEntity<List<TopFabricantesDTO>> listarTop10FabricantesComMaisPecas() {
//         List<TopFabricantesDTO> topFabricantes = pecaService.listarTop10FabricantesComMaisPecas();
//         return ResponseEntity.ok(topFabricantes);
//     }

//     @ApiOperation(value = "Cadastra uma nova peça")
//     @ApiResponses(value = {
//             @ApiResponse(code = 201, message = "Peça cadastrada com sucesso"),
//             @ApiResponse(code = 400, message = "Requisição inválida"),
//             @ApiResponse(code = 500, message = "Erro interno ao processar a requisição")
//     })
//     @PostMapping("/")
//     public ResponseEntity<PecaDTO> cadastrarPeca(@RequestBody PecaDTO pecaDTO) {
//         PecaDTO pecaSalvaDTO = pecaService.cadastrarPeca(pecaDTO);
//         return ResponseEntity.status(HttpStatus.CREATED).body(pecaSalvaDTO);
//     }

//     @ApiOperation(value = "Atualiza uma peça existente")
//     @ApiResponses(value = {
//             @ApiResponse(code = 200, message = "Peça atualizada com sucesso"),
//             @ApiResponse(code = 400, message = "Requisição inválida"),
//             @ApiResponse(code = 404, message = "Peça não encontrada"),
//             @ApiResponse(code = 500, message = "Erro interno ao processar a requisição")
//     })
//     @PutMapping("/{id}")
//     public ResponseEntity<PecaDTO> atualizarPeca(@PathVariable Long id, @RequestBody PecaDTO pecaDTO) {
//         PecaDTO pecaAtualizadaDTO = pecaService.atualizarPeca(id, pecaDTO);
//         return ResponseEntity.ok(pecaAtualizadaDTO);
//     }

//     @ApiOperation(value = "Deleta uma peça pelo ID")
//     @ApiResponses(value = {
//             @ApiResponse(code = 200, message = "Peça deletada com sucesso"),
//             @ApiResponse(code = 404, message = "Peça não encontrada")
//     })
//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deletarPeca(@PathVariable Long id) {
//         pecaService.deletarPeca(id);
//         return ResponseEntity.ok().build();
//     }
// }
