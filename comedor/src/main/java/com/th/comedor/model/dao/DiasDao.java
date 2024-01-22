package com.th.comedor.model.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.th.comedor.model.entity.Dias;

public interface DiasDao extends JpaRepository<Dias, Long>{

    @Query(value = "select * from dias d where d.fecha >= ?1 and d.fecha <= ?2", nativeQuery = true)
    public List<Dias> buscarFechaActual(Date fecha1, Date fecha2);
    
}
