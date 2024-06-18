package br.com.masterclass.superpecas.repository;

import br.com.masterclass.superpecas.model.Peca;
import br.com.masterclass.superpecas.projections.PecaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPecaRepository extends JpaRepository<Peca, Integer> {

    boolean existsByCarroId(Integer id);

    @Query("SELECT p FROM Peca p WHERE p.nome LIKE %:nome%")
    List<Peca> findByNameContaining(@Param("nome") String nome);

    @Query("SELECT p.nome AS nome, p.descricao AS descricao FROM Peca p WHERE p.id = :id")
    List<PecaProjection> findProjectionsById(@Param("id") Integer id);
}
