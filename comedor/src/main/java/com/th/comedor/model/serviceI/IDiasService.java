package com.th.comedor.model.serviceI;

import java.util.Date;
import java.util.List;

import com.th.comedor.model.entity.Dias;

public interface IDiasService {
    
    public List<Dias> findAll();

	public void save(Dias dias);

	public Dias findOne(Long id_dias);

	public void delete(Long id_dias);

	public List<Dias> buscarFechaActual(Date fecha1, Date fecha2);
}
