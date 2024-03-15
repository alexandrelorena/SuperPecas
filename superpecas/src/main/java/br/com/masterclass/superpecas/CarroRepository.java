package br.com.masterclass.superpecas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {

    boolean existsByNomeModeloAndFabricanteAndCodigoUnico(String nomeModelo, String fabricante, String codigoUnico);

    void deleteById(Long id);
}
