package com.th.comedor.model.serviceI;

import java.util.List;

import com.th.comedor.model.entity.Estados;

public interface IEstadoService {
    
    public List<Estados> findAll();

	public void save(Estados estados);

	public Estados findOne(Long id_Estado);

	public void delete(Long id_Estado);
}
