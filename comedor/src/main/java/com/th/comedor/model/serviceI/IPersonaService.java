package com.th.comedor.model.serviceI;

import java.util.List;

import com.th.comedor.model.entity.Persona;

public interface IPersonaService {
    
    public List<Persona> findAll();

	public void save(Persona persona);

	public Persona findOne(Long id_persona);

	public void delete(Long id_persona);
}
