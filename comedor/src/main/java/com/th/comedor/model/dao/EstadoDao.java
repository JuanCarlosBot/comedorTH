package com.th.comedor.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.th.comedor.model.entity.Estados;

public interface EstadoDao extends JpaRepository<Estados, Long>{
    
}
