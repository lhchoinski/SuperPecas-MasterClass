package br.com.masterclass.superpecas.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuariosDTO {
    private int id;
    private String nome;
    private String email;
    private String login;
    private String password;

}
