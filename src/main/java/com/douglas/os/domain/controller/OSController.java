package com.douglas.os.domain.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.douglas.os.domain.dto.OSDTO;
import com.douglas.os.domain.entity.OS;
import com.douglas.os.domain.service.OSService;
import com.douglas.os.domain.utilitys.GenerateOSPdfReport;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/os")
public class OSController {

	@Autowired
	private OSService service;

	// Busca uma Ordem de Serviço
	@GetMapping(value = "/{id}")
	public ResponseEntity<OSDTO> findById(@PathVariable Integer id) {
		OSDTO obj = new OSDTO(service.buscaPorId(id));
		return ResponseEntity.ok().body(obj);
	}

	// Busca todas as Ordens de Serviços
	@GetMapping
	public ResponseEntity<List<OSDTO>> findAll() {
		List<OSDTO> list = service.buscaTodasOS().stream().map(obj -> new OSDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(list);
	}

	// Gera Relatório com de Ordem de Serviços
	@GetMapping(value = "/pdfreport")
	public ResponseEntity<InputStreamResource> osReport() throws IOException {

		List<OS> os = service.buscaTodasOS();

		ByteArrayInputStream bis = GenerateOSPdfReport.osReport(os);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=OSCliente.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	// Criar uma Ordem de Serviço
	@PostMapping
	public ResponseEntity<OSDTO> create(@Valid @RequestBody OSDTO obj) {
		obj = new OSDTO(service.salvarOS(obj));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	// Atualiza uma Ordem de Serviço
	@PutMapping
	public ResponseEntity<OSDTO> update(@Valid @RequestBody OSDTO obj) {
		obj = new OSDTO(service.atualizarOS(obj));
		return ResponseEntity.ok().body(obj);
	}

	// Soma Valor Total das Ordens de Serviço
	@GetMapping(value = "/caixa")
	public ResponseEntity<Double> caixa() {
		List<String> list = service.buscaValor();
		List<Double> newList = new ArrayList<Double>();
		for (int i = 0; i < list.size(); i++) {
			String converter = list.get(i);
			Double passagem = Double.parseDouble(converter);
			newList.add(passagem);
		}
		Double sum = 0.0;
		for (Double k : newList)
			sum = sum + k;
		return ResponseEntity.ok().body(sum);
	}
}
