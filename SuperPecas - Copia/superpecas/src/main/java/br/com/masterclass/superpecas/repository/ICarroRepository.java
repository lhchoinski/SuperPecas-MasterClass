package br.com.masterclass.superpecas.repository;

import br.com.masterclass.superpecas.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICarroRepository extends JpaRepository<Carro, Integer> {

}
