package com.th.comedor.model.serviceC;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.th.comedor.model.dao.TipoReservaDao;
import com.th.comedor.model.entity.TipoReserva;
import com.th.comedor.model.serviceI.ITipoReservaService;

@Service
public class TipoReservaServiceImpl implements ITipoReservaService{

    @Autowired
    private TipoReservaDao tipoReservaDao;

    @Override
    public List<TipoReserva> findAll() {
        return tipoReservaDao.findAll();
    }

    @Override
    public void save(TipoReserva tipoReserva) {
        tipoReservaDao.save(tipoReserva);
    }

    @Override
    public TipoReserva findOne(Long id_tipo_reserva) {
        return tipoReservaDao.findById(id_tipo_reserva).orElse(null);
    }

    @Override
    public void delete(Long id_tipo_reserva) {
        tipoReservaDao.deleteById(id_tipo_reserva);
    }
    
}
