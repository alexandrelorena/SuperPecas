package br.com.masterclass.superpecas.repository;

import br.com.masterclass.superpecas.model.Carro;
import br.com.masterclass.superpecas.model.DTO.TopCarroPecasDTO;
import br.com.masterclass.superpecas.model.DTO.TopFabricantesDTO;
import br.com.masterclass.superpecas.model.Peca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PecaRepository extends JpaRepository<Peca, Integer> {

    Page<Peca> findAll(Pageable pageable);

    @Query(value = "SELECT c FROM PecaModel c WHERE lower(c.nome) like lower(concat('%', ?1,'%')) AND lower(c.numeroSerie) like lower(concat('%', ?1,'%'))")
    Peca findByNomeAndNumeroSerie(String nome, String numeroSerie);


    @Query(value = "SELECT c FROM PecaModel c WHERE lower(c.nome) like lower(concat('%', ?1,'%')) OR lower(c.numeroSerie) like lower(concat('%', ?1,'%')) OR lower(c.fabricante) like lower(concat('%', ?1,'%')) OR lower(c.modeloCarro) like lower(concat('%', ?1,'%'))")
    Page<Peca> findByNomeOrNumeroSerieOrFabricanteOrModeloCarro(String termo, Pageable pageable);

    List<Peca> findByCarroId(int carroId);

    @Query(nativeQuery = true, value = "SELECT Count(pe.PecaId) 'quantidade', CONCAT(ca.NomeModelo, '/', ca.Fabricante) 'carro' FROM Pecas pe "
        + "INNER JOIN Carros ca ON ca.CarroId = pe.CarroId "
        + "GROUP BY ca.NomeModelo, ca.Fabricante ORDER BY Count(pe.PecaId) DESC LIMIT 10")
    List<TopCarroPecasDTO> findTop10CarrosComMaisPecas();
}


// @Repository
// public interface PecaRepository extends JpaRepository<Peca, > {

//     boolean existsByNomeAndNumeroSerie(String nome, String numeroSerie);

//     @Modifying
//     void delete(Peca peca);

//     List<Peca> findByCarro_CarroID(Long carroID);

//     @Query(value = "SELECT COUNT(*), ModeloCarro FROM Pecas GROUP BY ModeloCarro ORDER BY COUNT(*) DESC LIMIT 10", nativeQuery = true)
//     List<Object[]> findTop10FabricantesComMaisPecas();

//     Page<Peca> findByModeloCarroContainingIgnoreCaseOrFabricanteContainingIgnoreCaseOrNomeContainingIgnoreCase(
//             String termo, String termo1, String termo2, Pageable pageable);

//     Page<Peca> findAll(Pageable pageable);

//     void deleteById(Long ID);

//     @Query(value = "SELECT p.modeloCarro, COUNT(p.modeloCarro) AS numPecas FROM Pecas p GROUP BY p.modeloCarro ORDER BY numPecas DESC LIMIT 10", nativeQuery = true)
//     List<Object[]> findTop10ModelosCarroComMaisPecas();

//     @Query("SELECT p FROM Peca p WHERE p.modeloCarro = :modeloCarro")
//     List<Peca> findByModeloCarro(@Param("modeloCarro") String modeloCarro);

//     boolean existsByNomeAndNumeroSerieAndPecaIDNot(String nome, String numeroSerie, Long ID);
// }
