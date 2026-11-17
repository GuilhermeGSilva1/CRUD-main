package com.assistencia.controller;

// Importa a classe responsável por representar os clientes
import com.assistencia.model.Cliente;

// Importa o repositório responsável pelas operações com clientes no banco de dados
import com.assistencia.repository.ClienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Indica que esta classe é responsável por controlar as requisições da aplicação
@Controller

// Define o endereço principal utilizado para acessar os clientes
@RequestMapping("/clientes")
public class ClienteController {

    // Declara o repositório responsável por acessar os dados dos clientes
    private final ClienteRepository clienteRepository;

    // Construtor responsável por receber o repositório através da injeção de dependência
    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // RN05: Realiza a busca de clientes por um termo informado e apresenta os resultados
    @GetMapping
    public String listar(@RequestParam(value = "termo", required = false) String termo, Model model) {

        // Declara uma lista para armazenar os clientes encontrados
        List<Cliente> clientes;

        // Verifica se o usuário informou um termo de pesquisa válido
        if (termo != null && !termo.trim().isEmpty()) {

            // Busca os clientes que correspondem ao termo informado
            clientes = clienteRepository.buscarPorTermo(termo.trim());

        } else {

            // Caso nenhum termo seja informado, busca todos os clientes cadastrados
            clientes = clienteRepository.findAll();
        }

        // Envia a lista de clientes para a página HTML
        model.addAttribute("clientes", clientes);

        // Envia o termo pesquisado para a página
        model.addAttribute("termoBusca", termo);

        // Retorna a página responsável por exibir os clientes
        return "clientes";
    }

    // Cadastra um novo cliente no sistema
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Cliente cliente) {

        // Salva os dados do cliente recebidos do formulário no banco de dados
        clienteRepository.save(cliente);

        // Redireciona para a página de clientes com uma mensagem de sucesso
        return "redirect:/clientes?sucesso=cadastrado";
    }

    // RN05: Edita os dados de um cliente já cadastrado
    @PostMapping("/editar")
    public String editar(
            // Recebe o identificador do cliente que será editado
            @RequestParam("id") Long id,

            // Recebe o novo nome do cliente
            @RequestParam("nome") String nome,

            // Recebe o novo telefone do cliente
            @RequestParam("telefone") String telefone,

            // Recebe o novo e-mail do cliente
            @RequestParam("email") String email,

            // Recebe o novo endereço, caso seja informado
            @RequestParam(value = "endereco", required = false) String endereco) {

        // Busca o cliente pelo identificador informado
        // Caso não seja encontrado, lança uma exceção
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));

        // Atualiza o nome do cliente
        cliente.setNome(nome);

        // Atualiza o telefone do cliente
        cliente.setTelefone(telefone);

        // Atualiza o e-mail do cliente
        cliente.setEmail(email);

        // Verifica se um novo endereço foi informado
        if (endereco != null) {

            // Atualiza o endereço do cliente
            cliente.setEndereco(endereco);
        }

        // Salva os dados atualizados no banco de dados
        clienteRepository.save(cliente);

        // Redireciona para a página de clientes com uma mensagem de sucesso
        return "redirect:/clientes?sucesso=editado";
    }
}
