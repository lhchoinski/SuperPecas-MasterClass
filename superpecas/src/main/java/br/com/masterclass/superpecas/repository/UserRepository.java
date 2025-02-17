package br.com.masterclass.superpecas.repository;


import br.com.masterclass.superpecas.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {
    UserDetails findByLogin(String login);
}
