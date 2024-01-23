package com.th.comedor.model.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.th.comedor.model.entity.Reserva;

public interface ReservaDao extends JpaRepository<Reserva, Long>{
    
    @Query(value = "select * from dias d left join reserva r on d.id_dia=r.id_dias " +
            "where d.id_dia=r.id_dias and d.fecha = ?1", nativeQuery = true)
    public List<Reserva> listaReservaPorDia(Date fecha);
}
