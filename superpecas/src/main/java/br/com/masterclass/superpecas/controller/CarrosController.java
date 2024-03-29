package br.com.masterclass.superpecas.controller;

import br.com.masterclass.superpecas.model.Carro;
import br.com.masterclass.superpecas.model.DTO.CarroDTO;
import br.com.masterclass.superpecas.model.DTO.TopFabricantesDTO;
import br.com.masterclass.superpecas.service.CarroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carro")
public class CarrosController {

    @Autowired
    CarroService carroService;

    @Autowired
    ModelMapper modelMapper;

    @Operation(summary = "Busca carro", description = "Busca os dados de um carro pelo seu Id.")
    @ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CarroDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Carro não encontrado", content = @Content) })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CarroDTO> buscarCarro(@Parameter(description = "Id do Carro", required = true) @PathVariable int id) {
       Carro carro = carroService.buscaCarro(id);

       if (carro == null){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }

       return new ResponseEntity<>(modelMapper.map(carro, CarroDTO.class), HttpStatus.OK);
    }

    @Operation(summary = "Lista carros", description = "Lista todos os carros.")
    @ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CarroDTO[].class)) })})
    @RequestMapping(value = "/listaTodos", method = RequestMethod.GET)
    public ResponseEntity<List<CarroDTO>> listaCarros() {
        List<Carro> carros = carroService.listaCarros();
        List<CarroDTO> listaDTO = Arrays.asList(modelMapper.map(carros, CarroDTO[].class));

        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }

    @Operation(summary = "Lista carros paginado", description = "Lista todos os carros por página.")
    @ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CarroDTO[].class)) })})
    @RequestMapping(value = "/listaTodosPaginado", method = RequestMethod.GET)
    public ResponseEntity<Page<CarroDTO>> listaCarros(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Carro> carros = carroService.listaCarrosPaginado(paging);
        return new ResponseEntity<>(carros.map(carro -> modelMapper.map(carro, CarroDTO.class)), HttpStatus.OK);
    }

    @Operation(summary = "Lista carros por nome e/ou fabricante paginado", description = "Lista todos os carros por nome e/ou fabricante por página.")
    @ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CarroDTO[].class)) })})
    @RequestMapping(value = {"/listaTodosPaginado/{termo}"}, method = RequestMethod.GET)
    public ResponseEntity<Page<CarroDTO>> listaCarros(@PathVariable String termo, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Carro> carros = carroService.listaCarrosPorNomeEOuFabricantePaginado(termo, paging);
        return new ResponseEntity<>(carros.map(carro -> modelMapper.map(carro, CarroDTO.class)), HttpStatus.OK);
    }

    @Operation(summary = "Lista fabricantes", description = "Lista todos os fabricantes.")
    @ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = String[].class)) })})
        @RequestMapping(value = "/listaTodosFabricantes", method = RequestMethod.GET)
    public ResponseEntity<List<String>> listaFabricantes() {
        List<String> fabricantes = carroService.listaFabricantes();
        return new ResponseEntity<>(fabricantes, HttpStatus.OK);
    }
    @Operation(summary = "Grava carro", description = "Grava um novo carro no sistema.")
    @ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CarroDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content) })
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity gravaCarro(@Parameter(description = "Dados do Carro", required = true) @RequestBody CarroDTO data) {
        Carro carro = modelMapper.map(data, Carro.class);
        carro = carroService.gravaCarro(carro);

        if (carro == null){
            return new ResponseEntity<>("Erro ao gravar carro", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(modelMapper.map(carro, CarroDTO.class), HttpStatus.OK);
    }

    @Operation(summary = "Atualiza carro", description = "Atualiza dados de um carro existente no sistema.")
    @ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CarroDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content) })
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity editaCarro(@Parameter(description = "Dados do Carro", required = true) @RequestBody CarroDTO data) {
        Carro carro = modelMapper.map(data, Carro.class);
        carro = carroService.editaCarro(carro);

        if (carro == null){
            return new ResponseEntity<>("Erro ao atualizar carro", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(modelMapper.map(carro, CarroDTO.class), HttpStatus.OK);
    }

    @Operation(summary = "Excluir carro", description = "Exclui um carro do sistema.")
    @ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CarroDTO.class)) })})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity excluiCarro(@Parameter(description = "Id do Carro", required = true)  @PathVariable int id) {
        boolean retorno = carroService.excluiCarro(id);
        if (retorno)
            return new ResponseEntity<>(HttpStatus.OK);
        else {
            return new ResponseEntity<>("Carro possui peças, não é possível remover", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Lista TOP 10 fabricantes", description = "Lista TOP 10 fabricantes.")
    @ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = TopFabricantesDTO[].class)) })})
    @RequestMapping(value = "/listaTop10Fabricantes", method = RequestMethod.GET)
    public ResponseEntity<List<TopFabricantesDTO>> listaTop10Fabricantes() {
        List<TopFabricantesDTO> fabricantes = carroService.listaTop10Fabricantes();
        return new ResponseEntity<>(fabricantes, HttpStatus.OK);
    }
}

// public class CarrosController {

//     private final CarroService carroService;

//     private final Logger logger = LoggerFactory.getLogger(CarrosController.class);

//     @Autowired
//     public CarrosController(CarroService carroService) {
//         this.carroService = carroService;
//     }

//     @Operation(value = "Busca um carro pelo ID")
//     @ApiResponses(value = {
//             @ApiResponse(code = 200, message = "Carro encontrado"),
//             @ApiResponse(code = 404, message = "Carro não encontrado")
//     })
// //    @GetMapping("/{id}")
// //    public ResponseEntity<CarroDTO> buscaCarro(@PathVariable Long id) {
// //        try {
// //            CarroDTO carroDTO = this.carroService.buscarCarro(id);
// //            return ResponseEntity.ok(carroDTO);
// //        } catch (CarroNaoEncontradoException e) {
// //            throw e;
// //        } catch (Exception e) {
// //            logger.error("Erro buscando carro com id: " + id, e);
// //            return ResponseEntity.notFound().build();
// //        }
// //    }
//     @GetMapping("/{id}")
//     public ResponseEntity<CarroDTO> buscaCarro(@PathVariable Long id) {
//         CarroDTO carroDTO = this.carroService.buscarCarro(id);
//         return ResponseEntity.ok(carroDTO);
//     }

//     @Operation(value = "Lista todos os carros")
//     @ApiResponses(value = {
//             @ApiResponse(code = 200, message = "Lista de carros retornada com sucesso")
//     })
//     @GetMapping("/listaTodos")
//     public ResponseEntity<List<CarroDTO>> listarTodosOsCarros() {
//         List<CarroDTO> carrosDTO = carroService.listarTodosOsCarros();
//         return ResponseEntity.ok(carrosDTO);
//     }

//     // Método para listar todos os carros paginados e retornar como DTOs
//     @Operation(value = "Lista todos os carros paginados")
//     @ApiResponses(value = {
//             @ApiResponse(code = 200, message = "Lista de carros paginada retornada com sucesso")
//     })
//     @GetMapping("/listaTodosPaginado")
//     public ResponseEntity<Page<CarroDTO>> listaCarrosPaginado(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
//         Page<CarroDTO> carros = carroService.listarTodosOsCarrosPaginado(page, size);
//         return ResponseEntity.ok(carros);
//     }

//     @Operation(value = "Lista todos os carros paginados com base em um termo de pesquisa")
//     @ApiResponses(value = {
//             @ApiResponse(code = 200, message = "Lista de carros paginada com base no termo de pesquisa retornada com sucesso")
//     })
//     @GetMapping("/listaTodosPaginado/{termo}")
//     public ResponseEntity<Page<CarroDTO>> listarTodosOsCarrosPaginadoComTermo(@PathVariable String termo, Pageable pageable) {
//         int page = pageable.getPageNumber();
//         int size = pageable.getPageSize();
//         Page<CarroDTO> carros = carroService.listarTodosOsCarrosPaginadoComTermo(termo, page, size);
//         return ResponseEntity.ok(carros);
//     }

//     @Operation(value = "Lista todos os fabricantes de carros")
//     @ApiResponses(value = {
//             @ApiResponse(code = 200, message = "Lista de fabricantes retornada com sucesso")
//     })
//     @GetMapping("/listaTodosFabricantes")
//     public ResponseEntity<List<String>> listarTodosOsFabricantes() {
//         List<String> fabricantes = carroService.listarTodosOsFabricantes();
//         return ResponseEntity.ok(fabricantes);
//     }

//     @Operation(value = "Lista os top 10 fabricantes de carros")
//     @ApiResponses(value = {
//             @ApiResponse(code = 200, message = "Lista dos top 10 fabricantes retornada com sucesso")
//     })
//     @GetMapping("/listaTop10Fabricantes")
//     public ResponseEntity<List<String>> listarTop10Fabricantes() {
//         List<String> topFabricantes = carroService.listarTop10Fabricantes();
//         return ResponseEntity.ok(topFabricantes);
//     }

//     @Operation(value = "Cadastra um novo carro")
//     @ApiResponses(value = {
//             @ApiResponse(code = 201, message = "Carro cadastrado com sucesso"),
//             @ApiResponse(code = 400, message = "Requisição inválida"),
//             @ApiResponse(code = 500, message = "Erro interno ao processar a requisição")
//     })
//     @PostMapping("/")
//     public ResponseEntity<CarroDTO> cadastraCarro(@RequestBody CarroDTO carroDTO) {
//         CarroDTO carroSalvoDTO = carroService.cadastrarCarro(carroDTO);
//         return ResponseEntity.status(HttpStatus.CREATED).body(carroSalvoDTO);
//     }

//     @Operation(value = "Atualiza um carro existente")
//     @ApiResponses(value = {
//             @ApiResponse(code = 200, message = "Carro atualizado com sucesso"),
//             @ApiResponse(code = 400, message = "Requisição inválida"),
//             @ApiResponse(code = 404, message = "Carro não encontrado"),
//             @ApiResponse(code = 500, message = "Erro interno ao processar a requisição")
//     })
//     @PutMapping("/{id}")
//     public ResponseEntity<CarroDTO> atualizaCarro(@PathVariable Long id, @RequestBody CarroDTO carroDTO) {
//         CarroDTO carroAtualizadoDTO = carroService.atualizarCarro(id, carroDTO);
//         return ResponseEntity.ok(carroAtualizadoDTO);
//     }

//     @Operation(value = "Deleta um carro pelo ID")
//     @ApiResponses(value = {
//             @ApiResponse(code = 200, message = "Carro deletado com sucesso"),
//             @ApiResponse(code = 404, message = "Carro não encontrado")
//     })
//     @DeleteMapping("/{id}")
//     public void deletaCarro(@PathVariable Long id) {
//         carroService.deletarCarro(id);
//     }
// }
