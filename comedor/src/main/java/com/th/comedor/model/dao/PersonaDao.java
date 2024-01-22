package com.th.comedor.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.th.comedor.model.entity.Persona;

public interface PersonaDao extends JpaRepository<Persona, Long>{
 
    @Query(value = "select * from persona p where p.ci ilike %?1%", nativeQuery = true)
    public List<Persona> listaPersonasPorCi(String ci);
}
