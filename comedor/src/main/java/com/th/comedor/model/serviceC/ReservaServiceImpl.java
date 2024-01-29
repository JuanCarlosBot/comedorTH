package com.th.comedor.model.serviceC;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.th.comedor.model.dao.ReservaDao;
import com.th.comedor.model.entity.Reserva;
import com.th.comedor.model.serviceI.IReservaService;

@Service
public class ReservaServiceImpl implements IReservaService{

    @Autowired
    private ReservaDao reservaDao;

    @Override
    public List<Reserva> findAll() {
        return reservaDao.findAll();
    }

    @Override
    public void save(Reserva reserva) {
        reservaDao.save(reserva);
    }

    @Override
    public Reserva findOne(Long id_reserva) {
        return reservaDao.findById(id_reserva).orElse(null);
    }

    @Override
    public void delete(Long id_reserva) {
        reservaDao.deleteById(id_reserva);
    }

    @Override
    public List<Reserva> listaReservaPorDia(Date fecha, Long id_tipo_reserva) {
        return reservaDao.listaReservaPorDia(fecha, id_tipo_reserva);
    }
 
}
