package com.douglas.os.domain.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglas.os.domain.dto.OSDTO;
import com.douglas.os.domain.entity.Cliente;
import com.douglas.os.domain.entity.OS;
import com.douglas.os.domain.entity.Tecnico;
import com.douglas.os.domain.enums.Prioridade;
import com.douglas.os.domain.enums.Status;
import com.douglas.os.domain.exceptions.ObjectNotFoundException;
import com.douglas.os.domain.repository.OSRepository;

@Service
public class OSService {

	@Autowired
	private OSRepository osRepository;

	@Autowired
	private TecnicoService tecnicoService;
	
	@Autowired
	private ClienteService clienteService;

	public OS buscaPorId(Integer id) {
		Optional<OS> obj = osRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado no ID: " + id + ", Tipo: " + OS.class.getName()));
	}

	public List<OS> buscaTodasOS() {
		return osRepository.findAll();
	}

	public OS salvarOS(@Valid OSDTO obj) {
		return fromDTO(obj);
	}


	public OS atualizarOS(@Valid OSDTO obj) {
		buscaPorId(obj.getId());
		return fromDTO(obj);
	}
	
	private OS fromDTO(OSDTO obj) {
		OS newObj = new OS();
		newObj.setId(obj.getId());
		newObj.setObservacoes(obj.getObservacoes());
		newObj.setPrioridade(Prioridade.toEnum(obj.getPrioridade().getCod()));
		newObj.setStatus(Status.toEnum(obj.getStatus().getCod()));
		
		Tecnico tec = tecnicoService.buscaPorId(obj.getTecnico());
		Cliente cli = clienteService.buscaPorId(obj.getCliente());
		
		newObj.setTecnico(tec);
		newObj.setCliente(cli);
		
		if(newObj.getStatus().getCod().equals(2)) {
			newObj.setDataFechamento(LocalDateTime.now());
		}
		
		return osRepository.save(newObj);
	}
}
