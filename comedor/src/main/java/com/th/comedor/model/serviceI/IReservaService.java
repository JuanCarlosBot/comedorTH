package com.th.comedor.model.serviceI;

import java.util.Date;
import java.util.List;

import com.th.comedor.model.entity.Reserva;

public interface IReservaService {

    public List<Reserva> findAll();

	public void save(Reserva reserva);

	public Reserva findOne(Long id_reserva);

	public void delete(Long id_reserva);

	public List<Reserva> listaReservaPorDia(Date fecha, Long id_tipo_reserva);

	public List<Reserva> reservasPorTipoYDia(Long id_dia, Long id_tipo_reserva);

}
