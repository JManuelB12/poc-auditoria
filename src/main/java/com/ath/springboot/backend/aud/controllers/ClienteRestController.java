package com.ath.springboot.backend.aud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ath.springboot.backend.aud.models.entity.Cliente;
import com.ath.springboot.backend.aud.models.service.IClienteService;

@RestController
@RequestMapping("/aud")
public class ClienteRestController
{
	@Autowired
	private IClienteService clienteService;

	@GetMapping("/cliente")
	@ResponseStatus(HttpStatus.OK)
	public List<Cliente> read()
	{
		return clienteService.read();
	}

	@PostMapping("/cliente")
	public ResponseEntity<?> create(@RequestBody Cliente cliente)
	{
		Cliente             clienteGuardado;
		Map<String, Object> response;

		response = new HashMap<>();

		try
		{
			clienteGuardado = clienteService.create(cliente);

			response.put("mensaje", "El cliente ha sido creado con éxito!");
			response.put("cliente", clienteGuardado);
		}
		catch(DataAccessException e)
		{
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/cliente/{id}")
	public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id)
	{
		Cliente             clienteActual = null;
		Map<String, Object> response      = new HashMap<>();

		try
		{
			clienteActual = clienteService.findById(id);

			if(clienteActual == null)
			{
				response.put("mensaje", "El cliente ID: " + id + " no existe en la base de datos!");

				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}

			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setDireccion(cliente.getDireccion());
			clienteActual.setNombreContactoEmeregencia(cliente.getNombreContactoEmeregencia());
			clienteActual.setNumeroDocumento(cliente.getNumeroDocumento());
			clienteActual.setTelefono(cliente.getTelefono());
			clienteActual.setTelefonoEmergencia(cliente.getTelefonoEmergencia());
			clienteActual.setTipoDocumento(cliente.getTipoDocumento());

			clienteActual = clienteService.update(clienteActual);
		}
		catch(DataAccessException e)
		{
			response.put("mensaje", "Error al realizar el update en la base de datos");
			response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente ha sido modificado con éxito!");
		response.put("cliente", clienteActual);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/cliente/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id)
	{
		Map<String, Object> response = new HashMap<>();

		try
		{
			Cliente cliente = clienteService.findById(id);

			if(cliente != null)
				clienteService.delete(id);
		}
		catch(DataAccessException e)
		{
			response.put("mensaje", "Error al realizar el delete en la base de datos");
			response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente ha sido eliminado con éxito!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
