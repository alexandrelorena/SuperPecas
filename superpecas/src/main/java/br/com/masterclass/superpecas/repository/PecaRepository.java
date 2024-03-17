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

    List<Peca> findByCarroId(Long carroId);

    Page<Peca> findByNomeContainingIgnoreCase(String termo, Pageable pageable);

    void deleteById(Long id);

    @Query(value = "SELECT modeloCarro, COUNT(*) AS numPecas FROM Pecas GROUP BY modeloCarro ORDER BY numPecas DESC", nativeQuery = true)
    Page<Object[]> findTop10ModelosCarroComMaisPecas(Pageable pageable);

    @Query("SELECT p FROM Peca p WHERE p.modeloCarro = :modeloCarro")
    List<Peca> findByModeloCarro(@Param("modeloCarro") String modeloCarro);

    boolean existsByNomeAndNumeroSerieAndIdNot(String nome, String numeroSerie, Long id);
}
