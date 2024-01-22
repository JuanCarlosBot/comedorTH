package com.th.comedor.model.serviceC;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.th.comedor.model.dao.PersonaDao;
import com.th.comedor.model.entity.Persona;
import com.th.comedor.model.serviceI.IPersonaService;

@Service
public class PersonaServiceImpl implements IPersonaService {

    @Autowired
    private PersonaDao personaDao;

    @Override
    public List<Persona> findAll() {
        return personaDao.findAll();
    }

    @Override
    public void save(Persona persona) {
        personaDao.save(persona);
    }

    @Override
    public Persona findOne(Long id_persona) {
        return personaDao.findById(id_persona).orElse(null);
    }

    @Override
    public void delete(Long id_persona) {
        personaDao.deleteById(id_persona);
    }

    @Override
    public List<Persona> listaPersonasPorCi(String ci) {
        return personaDao.listaPersonasPorCi(ci);
    }

    @Override
    public List<Persona> listaPersonasPorCodigo(String codigo) {
        return personaDao.listaPersonasPorCi(codigo);
    }
    
}
