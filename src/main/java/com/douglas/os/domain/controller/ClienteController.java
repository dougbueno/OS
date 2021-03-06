package com.douglas.os.domain.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douglas.os.domain.entity.Cliente;
import com.douglas.os.domain.service.ClienteService;
import com.douglas.os.domain.utilitys.GenerateClientePdfReport;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	// Busca um Cliente
	@GetMapping(value = "/{id}")
	public ResponseEntity<Cliente> buscaClientes(@PathVariable Integer id) {
		Cliente obj = clienteService.buscaPorId(id);
		return ResponseEntity.ok().body(obj);
	}

	// Busca lista de Clientes
	@GetMapping
	public ResponseEntity<List<Cliente>> buscaTodosClientes() {
		List<Cliente> obj = clienteService.buscaTodosClientes();
		return ResponseEntity.ok().body(obj);
	}
	
	// Gera Relatório com os Clientes
		@GetMapping(value = "/pdfreport")
		public ResponseEntity<InputStreamResource> tecnicosReport() throws IOException {

			List<Cliente> cliente = clienteService.buscaTodosClientes();

			ByteArrayInputStream bis = GenerateClientePdfReport.clienteReport(cliente);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "inline; filename=ListaCliente.pdf");

			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
					.body(new InputStreamResource(bis));
		}

	// Criar um Cliente
	@PostMapping
	public Cliente novoCliente(@Valid @RequestBody Cliente obj) {
		clienteService.salvarCliente(obj);
		return obj;
	}

	// Atualiza um Cliente através do ID
	@PutMapping(value = "/{id}")
	public ResponseEntity<Cliente> atualizaCliente(@PathVariable Integer id, 
			@Valid @RequestBody Cliente obj) {

		Cliente newObj = clienteService.atualizarCliente(id, obj);
		return ResponseEntity.ok().body(newObj);
	}

	// Deleta um Cliente através do ID
	@DeleteMapping(value = "/{id}")
	public String apagarCliente(@PathVariable Integer id) {
		clienteService.apagarCliente(id);
		return "Cliente deletado Com Sucesso";
	}

}
