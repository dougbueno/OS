package com.douglas.os.domain.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglas.os.domain.entity.Pessoa;
import com.douglas.os.domain.entity.Tecnico;
import com.douglas.os.domain.exceptions.DataIntegratyViolationException;
import com.douglas.os.domain.exceptions.ObjectNotFoundException;
import com.douglas.os.domain.repository.PessoaRepository;
import com.douglas.os.domain.repository.TecnicoRepository;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Tecnico buscaPorId(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado no ID: " + id + ", Tipo: " + Tecnico.class.getName()));
	}

	public List<Tecnico> buscaTodosTecnicos() {
		return tecnicoRepository.findAll();
	}

	public Tecnico salvarTecnico(Tecnico obj) {
		if (procuraPorCPF(obj) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		return tecnicoRepository.save(obj);
	}

	public Pessoa procuraPorCPF(Tecnico obj) {
		if (pessoaRepository.findByCPF(obj.getCpf()) != null) {
			return pessoaRepository.findByCPF(obj.getCpf());
		}
		return null;
	}

	public Tecnico atualizarTecnico(Integer id, @Valid Tecnico obj) {
		Tecnico oldObj = buscaPorId(id);
		if (procuraPorCPF(obj) != null && procuraPorCPF(obj).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		oldObj.setNome(obj.getNome());
		oldObj.setCpf(obj.getCpf());
		oldObj.setTelefone(obj.getTelefone());

		return tecnicoRepository.save(oldObj);

	}

	public void apagarTecnico(Integer id) {
		Tecnico obj = buscaPorId(id);
		if(obj.getList().size() > 0) {
			throw new DataIntegratyViolationException(""
					+ "Tecnico possui ordens de Serviço, Não pode ser Deletado!");
		}
		tecnicoRepository.deleteById(id);
	}

}
