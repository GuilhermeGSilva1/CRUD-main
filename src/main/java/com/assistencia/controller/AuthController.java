package com.assistencia.controller;

// Importa a anotação que identifica uma classe como controladora do Spring MVC
import org.springframework.stereotype.Controller;

// Importa a anotação utilizada para mapear requisições HTTP do tipo GET
import org.springframework.web.bind.annotation.GetMapping;

// Indica que esta classe controla as requisições relacionadas à autenticação
@Controller
public class AuthController {

    // Define o endereço /login para acessar a página de login
    @GetMapping("/login")
    public String login() {

        // Retorna o nome do arquivo HTML que representa a tela de login
        return "login";
    }
}
