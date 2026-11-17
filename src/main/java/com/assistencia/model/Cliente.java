package com.assistencia.model;

// Importação do conversor responsável por criptografar e descriptografar o CPF
import com.assistencia.converter.CpfCryptoConverter;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

// Define a classe como entidade JPA vinculada à tabela "cliente"
@Entity
@Table(name = "cliente")
public class Cliente {

    // Identificador único gerado automaticamente pelo banco de dados
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome completo do cliente (obrigatório, máximo de 150 caracteres)
    @Column(nullable = false, length = 150)
    private String nome;

    // RN03: CPF armazenado de forma criptografada no banco de dados
    // Aplica automaticamente o CpfCryptoConverter ao salvar e ler este atributo
    @Convert(converter = CpfCryptoConverter.class)
    @Column(nullable = false, length = 255)
    private String cpf;

    // Telefone de contato do cliente
    @Column(length = 30)
    private String telefone;

    // Endereço de e-mail do cliente
    @Column(length = 100)
    private String email;

    // Endereço residencial ou comercial do cliente
    @Column(length = 255)
    private String endereco;

    // Relacionamento um-para-muitos: um cliente pode possuir vários equipamentos.
    // CascadeType.ALL propaga operações (salvar/excluir) e orphanRemoval remove equipamentos desvinculados
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Equipamento> equipamentos = new ArrayList<>();

    // Construtor padrão exigido pelo JPA
    public Cliente() {
    }

    // Construtor para inicialização dos dados cadastrais do cliente
    public Cliente(String nome, String cpf, String telefone, String email, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(List<Equipamento> equipamentos) {
        this.equipamentos = equipamentos;
    }
}