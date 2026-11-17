package com.assistencia.controller;

// Importa os repositórios responsáveis pelo acesso aos dados do sistema
import com.assistencia.repository.AgendamentoRepository;
import com.assistencia.repository.ClienteRepository;
import com.assistencia.repository.EquipamentoRepository;
import com.assistencia.repository.UsuarioRepository;

// Importa recursos utilizados para identificar o usuário autenticado
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

// Importa as configurações necessárias para criar um controlador e enviar dados para as páginas
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// Indica que esta classe é responsável por controlar as requisições do painel principal
@Controller
public class DashboardController {

    // Declara os repositórios utilizados para consultar os dados do sistema
    private final ClienteRepository clienteRepository;
    private final EquipamentoRepository equipamentoRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final UsuarioRepository usuarioRepository;

    // Construtor responsável por receber os repositórios através da injeção de dependência
    public DashboardController(ClienteRepository clienteRepository,
                               EquipamentoRepository equipamentoRepository,
                               AgendamentoRepository agendamentoRepository,
                               UsuarioRepository usuarioRepository) {
        this.clienteRepository = clienteRepository;
        this.equipamentoRepository = equipamentoRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Define os endereços utilizados para acessar o painel principal
    @GetMapping({"/", "/dashboard"})
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        // RN04: Obtém os dados do usuário autenticado e prepara as informações gerais do painel

        // Verifica se existe um usuário autenticado e obtém seu login
        // Caso não exista, utiliza o texto "Usuário" como identificação
        String login = userDetails != null ? userDetails.getUsername() : "Usuário";

        // Busca o usuário no banco de dados utilizando seu login
        // Caso seja encontrado, obtém seu nome completo
        // Caso contrário, utiliza o próprio login
        String nomeCompleto = usuarioRepository.findByLogin(login)
                .map(u -> u.getNome())
                .orElse(login);

        // Envia o nome do usuário para ser exibido na página
        model.addAttribute("nomeUsuario", nomeCompleto);

        // Consulta e envia a quantidade total de clientes cadastrados
        model.addAttribute("totalClientes", clienteRepository.count());

        // Consulta e envia a quantidade total de equipamentos cadastrados
        model.addAttribute("totalEquipamentos", equipamentoRepository.count());

        // Consulta e envia a quantidade total de agendamentos registrados
        model.addAttribute("totalAgendamentos", agendamentoRepository.count());

        // Busca os agendamentos ordenados pela data e envia os resultados para o painel
        model.addAttribute("proximosAgendamentos", agendamentoRepository.findAllOrderByDataAgendamentoAsc());

        // Retorna a página principal do sistema
        return "dashboard";
    }
}
