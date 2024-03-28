package br.com.masterclass.superpecas.controller;

import br.com.masterclass.superpecas.model.DTO.CarroDTO;
import br.com.masterclass.superpecas.service.CarroService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carro")
public class CarrosController {

    private final CarroService carroService;

    private final Logger logger = LoggerFactory.getLogger(CarrosController.class);

    @Autowired
    public CarrosController(CarroService carroService) {
        this.carroService = carroService;
    }

    @ApiOperation(value = "Busca um carro pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Carro encontrado"),
            @ApiResponse(code = 404, message = "Carro não encontrado")
    })
//    @GetMapping("/{id}")
//    public ResponseEntity<CarroDTO> buscaCarro(@PathVariable Long id) {
//        try {
//            CarroDTO carroDTO = this.carroService.buscarCarro(id);
//            return ResponseEntity.ok(carroDTO);
//        } catch (CarroNaoEncontradoException e) {
//            throw e;
//        } catch (Exception e) {
//            logger.error("Erro buscando carro com id: " + id, e);
//            return ResponseEntity.notFound().build();
//        }
//    }
    @GetMapping("/{id}")
    public ResponseEntity<CarroDTO> buscaCarro(@PathVariable Long id) {
        CarroDTO carroDTO = this.carroService.buscarCarro(id);
        return ResponseEntity.ok(carroDTO);
    }

    @ApiOperation(value = "Lista todos os carros")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de carros retornada com sucesso")
    })
    @GetMapping("/listaTodos")
    public ResponseEntity<List<CarroDTO>> listarTodosOsCarros() {
        List<CarroDTO> carrosDTO = carroService.listarTodosOsCarros();
        return ResponseEntity.ok(carrosDTO);
    }

    // Método para listar todos os carros paginados e retornar como DTOs
    @ApiOperation(value = "Lista todos os carros paginados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de carros paginada retornada com sucesso")
    })
    @GetMapping("/listaTodosPaginado")
    public ResponseEntity<Page<CarroDTO>> listaCarrosPaginado(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        Page<CarroDTO> carros = carroService.listarTodosOsCarrosPaginado(page, size);
        return ResponseEntity.ok(carros);
    }

    @ApiOperation(value = "Lista todos os carros paginados com base em um termo de pesquisa")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de carros paginada com base no termo de pesquisa retornada com sucesso")
    })
    @GetMapping("/listaTodosPaginado/{termo}")
    public ResponseEntity<Page<CarroDTO>> listarTodosOsCarrosPaginadoComTermo(@PathVariable String termo, Pageable pageable) {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        Page<CarroDTO> carros = carroService.listarTodosOsCarrosPaginadoComTermo(termo, page, size);
        return ResponseEntity.ok(carros);
    }

    @ApiOperation(value = "Lista todos os fabricantes de carros")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de fabricantes retornada com sucesso")
    })
    @GetMapping("/listaTodosFabricantes")
    public ResponseEntity<List<String>> listarTodosOsFabricantes() {
        List<String> fabricantes = carroService.listarTodosOsFabricantes();
        return ResponseEntity.ok(fabricantes);
    }

    @ApiOperation(value = "Lista os top 10 fabricantes de carros")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista dos top 10 fabricantes retornada com sucesso")
    })
    @GetMapping("/listaTop10Fabricantes")
    public ResponseEntity<List<String>> listarTop10Fabricantes() {
        List<String> topFabricantes = carroService.listarTop10Fabricantes();
        return ResponseEntity.ok(topFabricantes);
    }

    @ApiOperation(value = "Cadastra um novo carro")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Carro cadastrado com sucesso"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 500, message = "Erro interno ao processar a requisição")
    })
    @PostMapping("/")
    public ResponseEntity<CarroDTO> cadastraCarro(@RequestBody CarroDTO carroDTO) {
        CarroDTO carroSalvoDTO = carroService.cadastrarCarro(carroDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(carroSalvoDTO);
    }

    @ApiOperation(value = "Atualiza um carro existente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Carro atualizado com sucesso"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Carro não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno ao processar a requisição")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CarroDTO> atualizaCarro(@PathVariable Long id, @RequestBody CarroDTO carroDTO) {
        CarroDTO carroAtualizadoDTO = carroService.atualizarCarro(id, carroDTO);
        return ResponseEntity.ok(carroAtualizadoDTO);
    }

    @ApiOperation(value = "Deleta um carro pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Carro deletado com sucesso"),
            @ApiResponse(code = 404, message = "Carro não encontrado")
    })
    @DeleteMapping("/{id}")
    public void deletaCarro(@PathVariable Long id) {
        carroService.deletarCarro(id);
    }
}
