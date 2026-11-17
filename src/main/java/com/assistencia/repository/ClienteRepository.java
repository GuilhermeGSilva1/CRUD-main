package com.assistencia.repository;

// Importações da entidade Cliente, anotações de consulta do Spring Data JPA e da coleção List
import com.assistencia.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// Marca a interface como repositório gerenciado pelo Spring
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // RN05: Busca de clientes por termo (nome, telefone ou email)
    // Consulta JPQL que pesquisa correspondências parciais (LIKE) ignorando maiúsculas/minúsculas (LOWER)
    // no nome e no e-mail (caso não seja nulo), além de buscar pelo número de telefone
    @Query("SELECT c FROM Cliente c WHERE " +
           "LOWER(c.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "(c.email IS NOT NULL AND LOWER(c.email) LIKE LOWER(CONCAT('%', :termo, '%'))) OR " +
           "(c.telefone IS NOT NULL AND c.telefone LIKE CONCAT('%', :termo, '%'))")
    List<Cliente> buscarPorTermo(@Param("termo") String termo);

    // Consulta derivada (Query Method): o Spring Data gera automaticamente o SQL equivalente a
    // "WHERE LOWER(nome) LIKE LOWER('%nome%')" com base na assinatura do método
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
}