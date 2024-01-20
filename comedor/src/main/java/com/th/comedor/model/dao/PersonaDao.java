package com.th.comedor.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.th.comedor.model.entity.Persona;

public interface PersonaDao extends JpaRepository<Persona, Long>{
    
}
