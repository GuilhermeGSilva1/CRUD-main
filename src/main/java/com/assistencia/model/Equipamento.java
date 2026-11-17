package com.assistencia.model;

import jakarta.persistence.*;

// Define a classe como entidade JPA vinculada à tabela "equipamento"
@Entity
@Table(name = "equipamento")
public class Equipamento {

    // Chave primária auto-incremental do equipamento
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RN07: Obrigatoriamente associado a um cliente previamente cadastrado
    // Chave estrangeira "cliente_id" não nula com carregamento imediato do proprietário
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // Categoria ou tipo do aparelho (ex.: Notebook, Smartphone, Impressora)
    @Column(nullable = false, length = 50)
    private String tipo;

    // Fabricante do equipamento (ex.: Dell, Samsung, Apple)
    @Column(nullable = false, length = 50)
    private String marca;

    // Modelo específico do aparelho
    @Column(nullable = false, length = 100)
    private String modelo;

    // Número de série para identificação individual do hardware (opcional)
    @Column(name = "numero_serie", length = 100)
    private String numeroSerie;

    // Descrição detalhada do problema relatado pelo cliente
    @Column(name = "defeito_declarado", columnDefinition = "TEXT")
    private String defeitoDeclarado;

    // Construtor padrão exigido pelo JPA
    public Equipamento() {
    }

    // Construtor completo para cadastrar um equipamento vinculado a um cliente
    public Equipamento(Cliente cliente, String tipo, String marca, String modelo, String numeroSerie, String defeitoDeclarado) {
        this.cliente = cliente;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.numeroSerie = numeroSerie;
        this.defeitoDeclarado = defeitoDeclarado;
    }

    // --- Métodos Getters e Setters para acesso e modificação dos atributos ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getDefeitoDeclarado() {
        return defeitoDeclarado;
    }

    public void setDefeitoDeclarado(String defeitoDeclarado) {
        this.defeitoDeclarado = defeitoDeclarado;
    }
}