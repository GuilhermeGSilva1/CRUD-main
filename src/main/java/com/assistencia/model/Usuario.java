package com.assistencia.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// Define a classe como entidade JPA vinculada à tabela "usuario"
@Entity
@Table(name = "usuario")
public class Usuario {

    // Chave primária auto-incremental do usuário
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome de exibição do usuário no sistema
    @Column(nullable = false, length = 100)
    private String nome;

    // Nome de usuário utilizado para autenticação (deve ser único no banco de dados)
    @Column(nullable = false, unique = true, length = 50)
    private String login;

    // Senha armazenada em formato criptografado (hash BCrypt)
    @Column(name = "senha_hash", nullable = false, length = 255)
    private String senhaHash;

    // Registra a data e hora do último login realizado pelo usuário
    @Column(name = "ultimo_acesso")
    private LocalDateTime ultimoAcesso;

    // Construtor padrão exigido pelo JPA
    public Usuario() {
    }

    // Construtor para criação de um novo usuário com suas credenciais
    public Usuario(String nome, String login, String senhaHash) {
        this.nome = nome;
        this.login = login;
        this.senhaHash = senhaHash;
    }

    // --- Métodos Getters e Setters para acesso e modificação dos atributos ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public LocalDateTime getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(LocalDateTime ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }
}