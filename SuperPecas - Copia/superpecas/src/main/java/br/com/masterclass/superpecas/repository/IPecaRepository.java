package br.com.masterclass.superpecas.repository;

import br.com.masterclass.superpecas.model.Peca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPecaRepository extends JpaRepository<Peca, Integer> {
    boolean existsByCarroId(Integer id);
}
