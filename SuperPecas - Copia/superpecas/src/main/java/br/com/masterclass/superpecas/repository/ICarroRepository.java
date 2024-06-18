package br.com.masterclass.superpecas.repository;

import br.com.masterclass.superpecas.model.Carro;
import br.com.masterclass.superpecas.projections.CarroProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICarroRepository extends JpaRepository<Carro, Integer> {

    @Query("SELECT c FROM Carro c WHERE c.nome LIKE %:nome%")
    List<Carro> findByNameContaining(@Param("nome") String nome);

    @Query("SELECT p.nome AS nome, p.fabricante AS fabricante FROM Carro p WHERE p.id = :id")
    List<CarroProjection> findProjectionsById(@Param("id") Integer id);

}
