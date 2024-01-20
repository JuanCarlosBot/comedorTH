package com.th.comedor.model.serviceC;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.th.comedor.model.dao.SubvensionDao;
import com.th.comedor.model.entity.Subvension;
import com.th.comedor.model.serviceI.ISubvensionService;

@Service
public class SubvensionServiceImpl implements ISubvensionService{

    @Autowired
    private SubvensionDao subvensionDao;

    @Override
    public List<Subvension> findAll() {
        return subvensionDao.findAll();
    }

    @Override
    public void save(Subvension subvension) {
        subvensionDao.save(subvension);
    }

    @Override
    public Subvension findOne(Long id_subvension) {
        return subvensionDao.findById(id_subvension).orElse(null);
    }

    @Override
    public void delete(Long id_subvension) {
        subvensionDao.deleteById(id_subvension);
    }
    
}
