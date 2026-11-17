package com.assistencia.repository;

// Importações da entidade Agendamento, dos recursos do Spring Data JPA e da coleção List
import com.assistencia.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// Indica ao Spring que esta interface é um componente de acesso a dados (DAO / Repository)
@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    // RN06: Listagem ordenada por data de agendamento exibindo todas as informações de cliente e equipamento
    // Consulta JPQL personalizada usando LEFT JOIN FETCH para carregar cliente e equipamento em uma única query
    // e ordenar os resultados pela data do agendamento em ordem crescente (do mais antigo para o mais recente)
    @Query("SELECT a FROM Agendamento a " +
           "LEFT JOIN FETCH a.cliente c " +
           "LEFT JOIN FETCH a.equipamento e " +
           "ORDER BY a.dataAgendamento ASC")
    List<Agendamento> findAllOrderByDataAgendamentoAsc();
}