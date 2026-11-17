package com.assistencia.service;

// Importações da entidade Usuario, do repositório e das classes de autenticação do Spring Security
import com.assistencia.model.Usuario;
import com.assistencia.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

// Registra a classe como um serviço gerenciado pelo Spring, implementando a interface padrão
// do Spring Security para carregamento de dados de autenticação do usuário
@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Repositório utilizado para consultar e atualizar os dados do usuário no banco
    private final UsuarioRepository usuarioRepository;

    // Injeta a dependência do UsuarioRepository via construtor
    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Método invocado automaticamente pelo Spring Security durante a tentativa de login
    // @Transactional garante que a atualização do último acesso ocorra dentro de uma transação
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuário pelo login informado; lança exceção caso não seja encontrado
        Usuario usuario = usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        try {
            // Registra a data e hora atuais como último acesso do usuário
            usuario.setUltimoAcesso(LocalDateTime.now());
            usuarioRepository.save(usuario);
        } catch (Exception ignored) {
            // Não impede a autenticação caso a atualização de auditoria falhe
        }

        // Constrói e retorna o objeto UserDetails reconhecido pelo Spring Security,
        // contendo o login, a senha criptografada (hash) e a permissão padrão ROLE_USER
        return new User(
                usuario.getLogin(),
                usuario.getSenhaHash(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}