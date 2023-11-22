package com.atividade1.service.serviceImplem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atividade1.model.Alimento;
import com.atividade1.repository.AlimentosRepository;
import com.atividade1.service.ServiceAlimento;

import java.util.List;

@Service
public class ServiceImplemAlimento implements ServiceAlimento {

    @Autowired
    private AlimentosRepository alimentoRepository;

    @Override
    public List<Alimento> findAll() {
        return alimentoRepository.findAll();
    }

    @Override
    public Alimento findById(int id) {
        return alimentoRepository.findById(id).orElse(null);
    }

    @Override
    public Alimento save(Alimento alimento) {
        return alimentoRepository.save(alimento);
    }


}
