package com.douglas.os.domain.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglas.os.domain.entity.Cliente;
import com.douglas.os.domain.entity.Pessoa;
import com.douglas.os.domain.exceptions.DataIntegratyViolationException;
import com.douglas.os.domain.exceptions.ObjectNotFoundException;
import com.douglas.os.domain.repository.ClienteRepository;
import com.douglas.os.domain.repository.PessoaRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
		
	public Cliente buscaPorId(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException ("Objeto não encontrado no ID: "+id+
				", Tipo: "+ Cliente.class.getName()));
	}

	public List<Cliente> buscaTodosClientes() {
		return clienteRepository.findAll();
	}
	
	public Cliente salvarCliente(Cliente obj) {
		if (procuraPorCPF(obj) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		return clienteRepository.save(obj);
	}

	public Pessoa procuraPorCPF(Cliente obj) {
		if (pessoaRepository.findByCPF(obj.getCpf()) != null) {
			return pessoaRepository.findByCPF(obj.getCpf());
		}
		return null;
	}
	
	public Cliente atualizarCliente(Integer id, @Valid Cliente obj) {
		Cliente oldObj = buscaPorId(id);
		if (procuraPorCPF(obj) != null && procuraPorCPF(obj).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		oldObj.setNome(obj.getNome());
		oldObj.setCpf(obj.getCpf());
		oldObj.setTelefone(obj.getTelefone());

		return clienteRepository.save(oldObj);

	}

	public void apagarCliente(Integer id) {
		Cliente obj = buscaPorId(id);
		if(obj.getList().size() > 0) {
			throw new DataIntegratyViolationException(""
					+ "Cliente possui ordens de Serviço, Não pode ser Deletado!");
		}
		clienteRepository.deleteById(id);
	}
}
