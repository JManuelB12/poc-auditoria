package com.ath.springboot.backend.aud.models.service;

import java.util.List;

import com.ath.springboot.backend.aud.models.entity.Cliente;

public interface IClienteService
{
	public List<Cliente> read();
	
	public Cliente findById(Long id);

	public Cliente create(Cliente cliente);

	public Cliente update(Cliente cliente);

	public void delete(Long id);
}
