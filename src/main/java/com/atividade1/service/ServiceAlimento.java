package com.atividade1.service;

import java.util.List;

import com.atividade1.model.Alimentos;

public interface ServiceAlimento {

    List<Alimentos> findAll();

    Alimentos findById(int id);
   
	Alimentos save(Alimentos alimento);
}
