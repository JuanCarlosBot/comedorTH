package com.th.comedor.model.dao;

import java.util.Date;
import java.util.List;

import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.th.comedor.model.entity.Dias;

public interface DiasDao extends JpaRepository<Dias, Long>{

    @Query(value = "select * from dias d where d.fecha >= ?1 and d.fecha <= ?2", nativeQuery = true)
    public List<Dias> buscarFechaActual(Date fecha1, Date fecha2);

   /* @Query("SELECT d.fecha, COUNT(r.id_reserva), d.estado, d.fechaFormateada, d.cantidadReservas, r, tr " +
    "FROM Dias d " +
    "LEFT JOIN d.reserva r " +
    "LEFT JOIN r.tipo_reserva tr " +
    "WHERE tr.id_tipo_reserva = ?1 AND d.fecha >= ?2 AND d.fecha <= ?3 " +
    "GROUP BY d.fecha, d.estado, d.fechaFormateada, d.id_dia, r.id_reserva, tr.id_tipo_reserva " +
    "ORDER BY d.fecha")
public List<Object[]> consultaReservasRangoDiasYTipo(Long id_tipo_reserva, Date fecha1, Date fecha2);*/

@Query(value = "select * from dias d where d.fecha>=?1 and d.fecha<=?2", nativeQuery = true)
public List<Dias> consultaRangoDias(Date fecha1, Date fecha2);
    
}
