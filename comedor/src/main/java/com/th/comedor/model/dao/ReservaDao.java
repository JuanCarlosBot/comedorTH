package com.th.comedor.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.th.comedor.model.entity.Reserva;

public interface ReservaDao extends JpaRepository<Reserva, Long>{
    
}
