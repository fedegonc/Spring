package com.atividade1.service.serviceImplem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atividade1.model.Postagems;
import com.atividade1.repository.PostagemsRepository;
import com.atividade1.service.ServicePostagem;

import java.util.List;

@Service
public class ServiceImplemPostagem implements ServicePostagem {

    @Autowired
    private PostagemsRepository postagemRepository;

    @Override
    public List<Postagems> findAll() {
        return postagemRepository.findAll();
    }

    @Override
    public Postagems findById(int id) {
        return postagemRepository.findById(id).orElse(null);
    }

    @Override
    public Postagems save(Postagems postagem) {
        return postagemRepository.save(postagem);
    }

  
}
