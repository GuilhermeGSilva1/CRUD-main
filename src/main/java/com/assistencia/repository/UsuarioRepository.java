package com.assistencia.repository;

// Importações da entidade Usuario, do JpaRepository e da classe Optional
import com.assistencia.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Indica ao Spring que esta interface atua na camada de persistência de usuários
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Busca um usuário pelo seu identificador de login único, retornando um Optional
    // para evitar NullPointerException caso o login não exista no banco de dados
    Optional<Usuario> findByLogin(String login);
}