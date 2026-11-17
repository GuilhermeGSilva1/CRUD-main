package com.assistencia.controller;

// Importa as classes responsáveis por representar clientes e equipamentos
import com.assistencia.model.Cliente;
import com.assistencia.model.Equipamento;

// Importa os repositórios responsáveis pelo acesso aos dados no banco de dados
import com.assistencia.repository.ClienteRepository;
import com.assistencia.repository.EquipamentoRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Indica que esta classe é responsável por controlar as requisições relacionadas aos equipamentos
@Controller

// Define o endereço principal utilizado para acessar os equipamentos
@RequestMapping("/equipamentos")
public class EquipamentoController {

    // Declara os repositórios responsáveis pelas operações com equipamentos e clientes
    private final EquipamentoRepository equipamentoRepository;
    private final ClienteRepository clienteRepository;

    // Construtor responsável por receber os repositórios através da injeção de dependência
    public EquipamentoController(EquipamentoRepository equipamentoRepository, ClienteRepository clienteRepository) {
        this.equipamentoRepository = equipamentoRepository;
        this.clienteRepository = clienteRepository;
    }

    // Lista os equipamentos e os clientes cadastrados no sistema
    @GetMapping
    public String listar(Model model) {

        // Busca todos os equipamentos cadastrados no banco de dados
        model.addAttribute("equipamentos", equipamentoRepository.findAll());

        // Busca todos os clientes cadastrados para disponibilizá-los na página
        model.addAttribute("clientes", clienteRepository.findAll());

        // Retorna a página responsável por exibir os equipamentos
        return "equipamentos";
    }

    // RN07: Exige que todo equipamento cadastrado esteja associado a um cliente existente
    @PostMapping("/salvar")
    public String salvar(
            // Recebe o identificador do cliente selecionado no formulário
            @RequestParam("clienteId") Long clienteId,

            // Recebe o tipo do equipamento
            @RequestParam("tipo") String tipo,

            // Recebe a marca do equipamento
            @RequestParam("marca") String marca,

            // Recebe o modelo do equipamento
            @RequestParam("modelo") String modelo,

            // Recebe o número de série, caso seja informado
            @RequestParam(value = "numeroSerie", required = false) String numeroSerie,

            // Recebe a descrição do defeito declarado, caso seja informado
            @RequestParam(value = "defeitoDeclarado", required = false) String defeitoDeclarado) {

        // Busca o cliente pelo identificador recebido no formulário
        // Caso não seja encontrado, lança uma exceção
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente obrigatório não encontrado: " + clienteId));

        // Cria um novo equipamento associando-o ao cliente encontrado
        Equipamento equipamento = new Equipamento(cliente, tipo, marca, modelo, numeroSerie, defeitoDeclarado);

        // Salva o equipamento no banco de dados
        equipamentoRepository.save(equipamento);

        // Redireciona para a página de equipamentos após o cadastro
        return "redirect:/equipamentos?sucesso=true";
    }
}
