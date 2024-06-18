package br.com.masterclass.superpecas.DTO;

import br.com.masterclass.superpecas.model.Carro;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PecaDTO {

     Integer id;

     String nome;

     String descricao;

     String serial_number;

     String fabricante;

     Carro carro;
}
