package com.assistencia.repository;

// Importações da entidade Equipamento, do JpaRepository e da coleção List
import com.assistencia.model.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Registra a interface como componente de repositório no contexto do Spring
@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {

    // Consulta derivada que retorna todos os equipamentos vinculados ao ID de um cliente específico
    List<Equipamento> findByClienteId(Long clienteId);
}