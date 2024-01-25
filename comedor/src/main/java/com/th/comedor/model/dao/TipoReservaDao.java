package com.th.comedor.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.th.comedor.model.entity.TipoReserva;

public interface TipoReservaDao extends JpaRepository<TipoReserva, Long>{
    
}
