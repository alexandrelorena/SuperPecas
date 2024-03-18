package br.com.masterclass.superpecas.repository;

import br.com.masterclass.superpecas.model.Peca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PecaRepository extends JpaRepository<Peca, Long> {

    boolean existsByNomeAndNumeroSerie(String nome, String numeroSerie);

    @Modifying
    void delete(Peca peca);

    List<Peca> findByCarro_CarroID(Long carroID);

    @Query(value = "SELECT COUNT(p), p.fabricante FROM Peca p GROUP BY p.fabricante ORDER BY COUNT(p) DESC LIMIT 10", nativeQuery = true)
    List<Object[]> findTop10FabricantesComMaisPecas();

    Page<Peca> findByNomeContainingIgnoreCase(String termo, Pageable pageable);

    void deleteById(Long ID);

//    @Query(value = "SELECT modeloCarro, COUNT(*) AS numPecas FROM Pecas GROUP BY modeloCarro ORDER BY numPecas DESC LIMIT 10", nativeQuery = true)
//    List<Object[]> findTop10ModelosCarroComMaisPecas();

//    @Query("SELECT p.modeloCarro, COUNT(p.modeloCarro) AS numPecas FROM Pecas p GROUP BY p.modeloCarro ORDER BY numPecas DESC LIMIT 10")
//    List<Object[]> findTop10CarrosComMaisPecas();

    @Query(value = "SELECT p.modeloCarro, COUNT(p.modeloCarro) AS numPecas FROM Pecas p GROUP BY p.modeloCarro ORDER BY numPecas DESC LIMIT 10", nativeQuery = true)
    List<Object[]> findTop10ModelosCarroComMaisPecas();


    @Query("SELECT p FROM Peca p WHERE p.modeloCarro = :modeloCarro")
    List<Peca> findByModeloCarro(@Param("modeloCarro") String modeloCarro);

    boolean existsByNomeAndNumeroSerieAndPecaIDNot(String nome, String numeroSerie, Long ID);
}
