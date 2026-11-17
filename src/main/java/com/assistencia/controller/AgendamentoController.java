package com.assistencia.controller;

// Importações das classes utilizadas pelo controlador
import com.assistencia.model.Agendamento;
import com.assistencia.model.Cliente;
import com.assistencia.model.Equipamento;
import com.assistencia.repository.AgendamentoRepository;
import com.assistencia.repository.ClienteRepository;
import com.assistencia.repository.EquipamentoRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

// Indica que esta classe é responsável por controlar as requisições da aplicação
@Controller

// Define o endereço principal utilizado para acessar os agendamentos
@RequestMapping("/agendamentos")
public class AgendamentoController {

    // Repositórios responsáveis pela comunicação com o banco de dados
    private final AgendamentoRepository agendamentoRepository;
    private final ClienteRepository clienteRepository;
    private final EquipamentoRepository equipamentoRepository;

    // Construtor responsável por receber os repositórios através da injeção de dependência
    public AgendamentoController(AgendamentoRepository agendamentoRepository,
                                 ClienteRepository clienteRepository,
                                 EquipamentoRepository equipamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.clienteRepository = clienteRepository;
        this.equipamentoRepository = equipamentoRepository;
    }

    // RN06: Lista os agendamentos em ordem de data e carrega os clientes e equipamentos disponíveis
    @GetMapping
    public String listar(Model model) {

        // Busca todos os agendamentos ordenados pela data de agendamento
        model.addAttribute("agendamentos", agendamentoRepository.findAllOrderByDataAgendamentoAsc());

        // Busca todos os clientes cadastrados
        model.addAttribute("clientes", clienteRepository.findAll());

        // Busca todos os equipamentos cadastrados
        model.addAttribute("equipamentos", equipamentoRepository.findAll());

        // Retorna a página responsável por exibir os agendamentos
        return "agendamentos";
    }

    // Cadastra um novo agendamento no sistema
    @PostMapping("/salvar")
    public String salvar(
            // Recebe o identificador do cliente selecionado no formulário
            @RequestParam("clienteId") Long clienteId,

            // Recebe o identificador do equipamento selecionado no formulário
            @RequestParam("equipamentoId") Long equipamentoId,

            // Recebe a data e o horário do agendamento no formato ISO
            @RequestParam("dataAgendamento")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime dataAgendamento,

            // Recebe o status do agendamento, utilizando AGENDADO como valor padrão
            @RequestParam(value = "status", defaultValue = "AGENDADO") String status,

            // Recebe as observações, caso tenham sido preenchidas
            @RequestParam(value = "observacoes", required = false) String observacoes) {

        // Busca o cliente pelo identificador informado
        // Caso não exista, lança uma exceção
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente inválido: " + clienteId));

        // Busca o equipamento pelo identificador informado
        // Caso não exista, lança uma exceção
        Equipamento equipamento = equipamentoRepository.findById(equipamentoId)
                .orElseThrow(() -> new IllegalArgumentException("Equipamento inválido: " + equipamentoId));

        // Cria um novo agendamento com os dados recebidos
        Agendamento agendamento = new Agendamento(cliente, equipamento, dataAgendamento, status, observacoes);

        // Salva o novo agendamento no banco de dados
        agendamentoRepository.save(agendamento);

        // Redireciona para a página de agendamentos com uma mensagem de sucesso
        return "redirect:/agendamentos?sucesso=cadastrado";
    }

    // RN06: Atualiza a data e o horário de um agendamento existente
    @PostMapping("/reagendar")
    public String reagendar(
            // Recebe o identificador do agendamento que será alterado
            @RequestParam("id") Long id,

            // Recebe a nova data e horário informados pelo usuário
            @RequestParam("novaData")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime novaData) {

        // Busca o agendamento pelo identificador informado
        // Caso não seja encontrado, lança uma exceção
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado: " + id));

        // Atualiza a data do agendamento
        agendamento.setDataAgendamento(novaData);

        // Salva as alterações no banco de dados
        agendamentoRepository.save(agendamento);

        // Redireciona para a página de agendamentos com uma mensagem de sucesso
        return "redirect:/agendamentos?sucesso=reagendado";
    }
}
