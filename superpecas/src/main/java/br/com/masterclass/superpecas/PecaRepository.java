package br.com.masterclass.superpecas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PecaRepository extends JpaRepository<Peca, Long> {

    boolean existeByNomeAndNumeroSerie(String nome, String descricao, String numeroserie, String fabricante, String modelocarro);


    void deleteById(Long id);

   }
