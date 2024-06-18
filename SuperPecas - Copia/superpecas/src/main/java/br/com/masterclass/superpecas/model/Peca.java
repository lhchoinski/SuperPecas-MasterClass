package br.com.masterclass.superpecas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "pecas")
public class Peca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String descricao;

    private String serialNumber;

    private String fabricante;

    @ManyToOne
    @JoinColumn(name = "id_carro")
    private Carro carro;
}
