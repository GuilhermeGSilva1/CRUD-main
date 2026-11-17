package com.assistencia.model;

// Importações das anotações de persistência (JPA) e da API de data/hora
import jakarta.persistence.*;
import java.time.LocalDateTime;

// Indica que esta classe é uma entidade JPA mapeada para uma tabela no banco de dados
@Entity
// Define o nome da tabela correspondente no banco de dados
@Table(name = "agendamento")
public class Agendamento {

    // Chave primária da tabela com geração automática incremental (AUTO_INCREMENT / IDENTITY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RN06: Exibindo todas as informações relacionadas ao cliente e ao equipamento
    // Relacionamento muitos-para-um obrigatório com carregamento imediato (EAGER) dos dados do cliente
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // Relacionamento muitos-para-um obrigatório com carregamento imediato (EAGER) dos dados do equipamento
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "equipamento_id", nullable = false)
    private Equipamento equipamento;

    // Data e hora marcadas para o atendimento (campo obrigatório)
    @Column(name = "data_agendamento", nullable = false)
    private LocalDateTime dataAgendamento;

    // Situação atual do agendamento (ex.: AGENDADO, CONCLUIDO, CANCELADO), limitado a 30 caracteres
    @Column(nullable = false, length = 30)
    private String status;

    // Campo de texto longo para anotações adicionais sobre o agendamento
    @Column(columnDefinition = "TEXT")
    private String observacoes;

    // Construtor padrão exigido pelo JPA
    public Agendamento() {
    }

    // Construtor completo para facilitar a criação de novos agendamentos
    public Agendamento(Cliente cliente, Equipamento equipamento, LocalDateTime dataAgendamento, String status, String observacoes) {
        this.cliente = cliente;
        this.equipamento = equipamento;
        this.dataAgendamento = dataAgendamento;
        this.status = status;
        this.observacoes = observacoes;
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

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public LocalDateTime getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(LocalDateTime dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}