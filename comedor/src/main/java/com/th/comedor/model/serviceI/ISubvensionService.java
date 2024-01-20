package com.th.comedor.model.serviceI;

import java.util.List;

import com.th.comedor.model.entity.Subvension;

public interface ISubvensionService {
    
    public List<Subvension> findAll();

	public void save(Subvension subvension);

	public Subvension findOne(Long id_subvension);

	public void delete(Long id_subvension);
}
