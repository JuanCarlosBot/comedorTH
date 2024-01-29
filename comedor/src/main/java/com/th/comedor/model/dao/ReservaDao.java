package com.th.comedor.model.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.th.comedor.model.entity.Reserva;

public interface ReservaDao extends JpaRepository<Reserva, Long>{
    
    @Query(value = "SELECT d.id_dia AS dia_id, d.fecha, r.* " +
        "FROM dias d " +
        "LEFT JOIN reserva r ON d.id_dia = r.id_dia " +
        "WHERE d.fecha = ?1 AND r.id_tipo_reserva = ?2", nativeQuery = true)
    public List<Reserva> listaReservaPorDia(Date fecha, Long id_tipo_reserva);
    
    @Query(value = "select * from reserva r "+
    "where r.id_dia = ?1 and r.id_tipo_reserva = ?2", nativeQuery = true)
    public List<Reserva> reservasPorTipoYDia(Long id_dia, Long id_tipo_reserva);
}
