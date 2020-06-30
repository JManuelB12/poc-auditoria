package com.ath.springboot.backend.aud.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ath.springboot.backend.aud.models.dao.IClienteDao;
import com.ath.springboot.backend.aud.models.entity.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService
{
	@Autowired
	private IClienteDao clienteDao;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> read()
	{
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	public Cliente create(Cliente cliente)
	{
		return clienteDao.save(cliente);
	}

	@Override
	public Cliente update(Cliente cliente)
	{
		return clienteDao.save(cliente);
	}

	@Override
	public void delete(Long id)
	{
		clienteDao.deleteById(id);
	}

	@Override
	public Cliente findById(Long id)
	{
		return clienteDao.findById(id).orElse(null);
	}

}
