package com.ath.springboot.backend.aud.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.ath.springboot.backend.aud.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long>
{

}
