package com.douglas.os.domain.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import com.douglas.os.domain.entity.Tecnico;
import com.douglas.os.domain.service.TecnicoService;
import com.douglas.os.domain.utilitys.GeneratePdfReport;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/tecnico")
public class TecnicoController {

	@Autowired
	private TecnicoService tecnicoService;

	// Verifica se esta ativo o server
	@GetMapping(value = "/health")
	public String health() {
		LocalDateTime agora = LocalDateTime.now();
		DateTimeFormatter formatado = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return agora.format(formatado);
	}

	// Busca um técnico
	@GetMapping(value = "/{id}")
	public ResponseEntity<Tecnico> buscaTecnicos(@PathVariable Integer id) {
		Tecnico obj = tecnicoService.buscaPorId(id);
		return ResponseEntity.ok().body(obj);
	}

	// Gera Relatório com os Técnicos
	@GetMapping(value = "/pdfreport")
	public ResponseEntity<InputStreamResource> tecnicosReport() throws IOException {

		List<Tecnico> tecnico = tecnicoService.buscaTodosTecnicos();

		ByteArrayInputStream bis = GeneratePdfReport.tecnicosReport(tecnico);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=ListaTecnicos.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	// Busca todos técnicos
	@GetMapping
	public ResponseEntity<List<Tecnico>> buscaTodosTecnicos() {
		List<Tecnico> obj = tecnicoService.buscaTodosTecnicos();
		return ResponseEntity.ok().body(obj);
	}

	// Criar um técnico
	@PostMapping
	public Tecnico novoTecnico(@Valid @RequestBody Tecnico obj) {
		tecnicoService.salvarTecnico(obj);
		return obj;
	}

	// Atualiza um técnico através do ID
	@PutMapping(value = "/{id}")
	public ResponseEntity<Tecnico> atualizaTecnico(@PathVariable Integer id, @Valid @RequestBody Tecnico obj) {

		Tecnico newObj = tecnicoService.atualizarTecnico(id, obj);
		return ResponseEntity.ok().body(newObj);
	}

	// Deleta um técnico através do ID
	@DeleteMapping(value = "/{id}")
	public void apagarTecnico(@PathVariable Integer id) {
		tecnicoService.apagarTecnico(id);
	}

}
