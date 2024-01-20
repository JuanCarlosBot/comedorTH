package com.th.comedor.model.serviceI;

import java.util.List;

import com.th.comedor.model.entity.Seccion;

public interface ISeccionService {
    
    public List<Seccion> findAll();

	public void save(Seccion seccion);

	public Seccion findOne(Long id_seccion);

	public void delete(Long id_seccion);
}
