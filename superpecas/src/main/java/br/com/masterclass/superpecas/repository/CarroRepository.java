package br.com.masterclass.superpecas.repository;

import br.com.masterclass.superpecas.model.Carro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {

    boolean existsByNomeModeloAndFabricanteAndCodigoUnico(String NomeModelo, String Fabricante, String CodigoUnico);

//    void deleteById(Long id);

    void deleteById(Long id);

    Page<Carro> findAllByNomeModeloContainingIgnoreCaseOrFabricanteContainingIgnoreCase(String termo, String termo1, Pageable pageable);

    @Query("SELECT DISTINCT c.fabricante FROM Carro c")
    List<String> findDistinctFabricante();

    List<String> findTop10DistinctFabricanteByOrderByFabricanteAsc();


}
