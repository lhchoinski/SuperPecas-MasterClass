package br.com.masterclass.superpecas.DTO;

import br.com.masterclass.superpecas.enums.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
