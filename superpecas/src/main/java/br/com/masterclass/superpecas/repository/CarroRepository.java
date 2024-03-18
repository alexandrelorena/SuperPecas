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

    // Método para buscar carros por modelo contendo o termo especificado
    Page<Carro> findByNomeModeloContainingIgnoreCase(String termo, Pageable pageable);

    @Query("SELECT DISTINCT c.fabricante FROM Carro c")
    List<String> findDistinctFabricante();

    @Query("SELECT c.fabricante FROM Carro c GROUP BY c.fabricante ORDER BY COUNT(c) DESC LIMIT 10")
    List<String> findTop10DistinctFabricanteByOrderByFabricanteAsc();
}
