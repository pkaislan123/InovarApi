package com.inovar.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inovar.models.Endereco;
import com.inovar.repository.EnderecoRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("v1")
@RestController
@CrossOrigin
@Api(value = "Enderecos")
public class EnderecoController {

	@Autowired
	private EnderecoRepository enderecoRepository;

	
	@ApiOperation(value = "Listar Por Id Cliente")
	@GetMapping("protected/enderecos/listarporid/{id_cliente}")
	public List<Endereco> listarPorIdCliente(@PathVariable int id_cliente) {
		return enderecoRepository.listByIdCliente(id_cliente);
	}
	
	@ApiOperation(value = "Recuperar Endereço por Id")
	@GetMapping("protected/enderecos/recuperarporid/{id}")
	public Optional<Endereco> recuperarPorId(@PathVariable int id) {
		return enderecoRepository.findById(id);
	}
	
	@ApiOperation(value = "Cadastrar Novo Endereço")
	@PostMapping(path = "protected/enderecos/novo_cadastro")
	public boolean inserirNovoEndereco(@RequestBody Endereco endereco) {
		
		return enderecoRepository.save(endereco) != null;

	}
	
	@ApiOperation(value = "Excluir Endereço")
	@DeleteMapping(path = "protected/enderecos/excluir/{id}")
	public ResponseEntity<Void> excluirEndereco(@PathVariable int id) {
		
		enderecoRepository.deleteById(id);
		return ResponseEntity.noContent().build();

	}
	
	@ApiOperation(value = "Atualizar Endereço")
	@PutMapping(path = "protected/enderecos/atualizar/{id}")
	public ResponseEntity update(@PathVariable("id") int id,
	                                      @RequestBody Endereco endereco) {
	   return enderecoRepository.findById(id)
	           .map(record -> {
	               record.setNome_recebedor(endereco.getNome_recebedor());
	               record.setCep(endereco.getCep());
	               record.setEstado(endereco.getEstado());
	               record.setCidade(endereco.getCidade());
	               record.setBairro(endereco.getBairro());
	               record.setLogradouro(endereco.getLogradouro());
	               record.setNumero(endereco.getNumero());
	               record.setComplemento(endereco.getComplemento());
	               record.setTelefone_contato(endereco.getTelefone_contato());
	               record.setInfo_adicional(endereco.getInfo_adicional());

	               Endereco updated = enderecoRepository.save(record);
	               return ResponseEntity.ok().body(updated);
	           }).orElse(ResponseEntity.notFound().build());
	}
}
