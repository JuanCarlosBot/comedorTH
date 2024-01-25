package com.th.comedor.model.serviceI;

import java.util.List;

import com.th.comedor.model.entity.TipoReserva;

public interface ITipoReservaService {
    
    public List<TipoReserva> findAll();

	public void save(TipoReserva tipoReserva);

	public TipoReserva findOne(Long id_tipo_reserva);

	public void delete(Long id_tipo_reserva);
}
