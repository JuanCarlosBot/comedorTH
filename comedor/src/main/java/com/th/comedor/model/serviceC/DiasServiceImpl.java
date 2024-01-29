package com.th.comedor.model.serviceC;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.th.comedor.model.dao.DiasDao;
import com.th.comedor.model.entity.Dias;
import com.th.comedor.model.serviceI.IDiasService;

@Service
public class DiasServiceImpl implements IDiasService{

    @Autowired
    private DiasDao diasDao;

    @Override
    public List<Dias> findAll() {
        return diasDao.findAll();
    }

    @Override
    public void save(Dias dias) {
        diasDao.save(dias);
    }

    @Override
    public Dias findOne(Long id_dias) {
        return diasDao.findById(id_dias).orElse(null);
    }

    @Override
    public void delete(Long id_dias) {
        diasDao.deleteById(id_dias);
    }

    @Override
    public List<Dias> buscarFechaActual(Date fecha1, Date fecha2) {
        return diasDao.buscarFechaActual(fecha1, fecha2);
    }

    @Override
    public List<Dias> consultaRangoDias(Date fecha1, Date fecha2) {
        return diasDao.consultaRangoDias(fecha1, fecha2);
    }

    
}
