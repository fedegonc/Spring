package com.atividade1.service.serviceImplem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atividade1.model.Postagem;
import com.atividade1.repository.PostagemsRepository;
import com.atividade1.service.ServicePostagem;

import java.util.List;

@Service
public class ServiceImplemPostagem implements ServicePostagem {

    @Autowired
    private PostagemsRepository postagemRepository;

    @Override
    public List<Postagem> findAll() {
        return postagemRepository.findAll();
    }

    @Override
    public Postagem findById(int id) {
        return postagemRepository.findById(id).orElse(null);
    }

    @Override
    public Postagem save(Postagem postagem) {
        return postagemRepository.save(postagem);
    }

  
}
