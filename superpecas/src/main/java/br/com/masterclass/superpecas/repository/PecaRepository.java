package br.com.masterclass.superpecas.repository;

import br.com.masterclass.superpecas.Peca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PecaRepository extends JpaRepository<Peca, Long> {

    boolean existsByNomeAndNumeroSerie(String nome, String numeroserie);

    @Modifying
    void delete(Peca peca);

    Page<Peca> findByNomeContainingIgnoreCase(String termo, Pageable pageable);

    void deleteById(Long id);

    @Query(value = "SELECT modeloCarro, COUNT(*) AS numPecas FROM Pecas GROUP BY modeloCarro ORDER BY numPecas DESC", nativeQuery = true)
    Page<Object[]> findTop10ModelosCarroComMaisPecas(Pageable pageable);
}
