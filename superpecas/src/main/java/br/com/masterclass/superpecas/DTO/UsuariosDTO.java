package br.com.masterclass.superpecas.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UsuariosDTO {
    private UUID id;
    private String nome;
    private String email;
    private String login;
    private String password;

}
