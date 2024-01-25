package com.th.comedor.model.serviceC;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.th.comedor.model.dao.EstadoDao;
import com.th.comedor.model.entity.Estados;
import com.th.comedor.model.serviceI.IEstadoService;

@Service
public class EstadoServiceImpl implements IEstadoService{

    @Autowired
    private EstadoDao estadoDao; 

    @Override
    public List<Estados> findAll() {
        return estadoDao.findAll();
    }

    @Override
    public void save(Estados estados) {
        estadoDao.save(estados);
    }

    @Override
    public Estados findOne(Long id_Estado) {
        return estadoDao.findById(id_Estado).orElse(null);
    }

    @Override
    public void delete(Long id_Estado) {
        estadoDao.deleteById(id_Estado);
    }
    
}
