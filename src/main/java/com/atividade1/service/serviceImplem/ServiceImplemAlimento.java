package com.atividade1.service.serviceImplem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atividade1.model.Alimentos;
import com.atividade1.repository.AlimentosRepository;
import com.atividade1.service.ServiceAlimento;

import java.util.List;

@Service
public class ServiceImplemAlimento implements ServiceAlimento {

    @Autowired
    private AlimentosRepository alimentoRepository;

    @Override
    public List<Alimentos> findAll() {
        return alimentoRepository.findAll();
    }

    @Override
    public Alimentos findById(int id) {
        return alimentoRepository.findById(id).orElse(null);
    }

    @Override
    public Alimentos save(Alimentos alimento) {
        return alimentoRepository.save(alimento);
    }


}
