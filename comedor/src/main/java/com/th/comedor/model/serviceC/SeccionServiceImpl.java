package com.th.comedor.model.serviceC;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.th.comedor.model.dao.SeccionDao;
import com.th.comedor.model.entity.Seccion;
import com.th.comedor.model.serviceI.ISeccionService;

@Service
public class SeccionServiceImpl implements ISeccionService{

    @Autowired
    private SeccionDao seccionDao;

    @Override
    public List<Seccion> findAll() {
        return seccionDao.findAll();
    }

    @Override
    public void save(Seccion seccion) {
        seccionDao.save(seccion);
    }

    @Override
    public Seccion findOne(Long id_seccion) {
        return seccionDao.findById(id_seccion).orElse(null);
    }

    @Override
    public void delete(Long id_seccion) {
        seccionDao.deleteById(id_seccion);
    }
    
}
