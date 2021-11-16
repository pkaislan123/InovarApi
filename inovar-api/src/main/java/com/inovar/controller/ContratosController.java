package com.inovar.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inovar.models.Contrato;
import com.inovar.models.Safra;
import com.inovar.models.User;
import com.inovar.repository.ContratoRepository;
import com.inovar.repository.UserRepository;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/protected/contratos")
public class ContratosController {

	
	@Autowired
	ContratoRepository contratoRepository;
	

	@ApiOperation(value = "Listar Contratos")
	@GetMapping("/listar")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Contrato> listarContratos() {
		return contratoRepository.findAll();
	}
	
	
	@ApiOperation(value = "Listar Contrato Por Id")
	@GetMapping("/listar/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Optional<Contrato> listarPorId(@PathVariable int id) {
		Optional<Contrato> contrato =  contratoRepository.findById(id);
		return contrato;
	}
	
	
	@ApiOperation(value = "Listar Contrato Por Contratante")
	@GetMapping("/listar_contratante/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ARMAZEM')")
	public List<Contrato> listarPorContratante(@PathVariable int id) {
		return  contratoRepository.listarPorContratante(id);
	}
	
	@CrossOrigin
	@ApiOperation(value = "Excluir Contrato")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(path = "/excluir/{id}")
	public ResponseEntity<Void> excluirClassificador(@PathVariable int id) {
		
		contratoRepository.deleteById(id);
		return ResponseEntity.noContent().build();

	}
	
	
	@ApiOperation(value = "Cadastrar Contrato")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(path = "/cadastrar")
	public boolean inserirContrato(@RequestBody Contrato contrato) {
		
		return contratoRepository.save(contrato) != null;

	}
	
	@ApiOperation(value = "Listar Ultimo Id ")
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(path = "/ultimoid")
	public Integer ultimoId() {
		
		return contratoRepository.ultimoId();

	}
	
	
	@CrossOrigin
	@ApiOperation(value = "Atualizar Contrato")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(path = "/atualizar/{id}")
	public ResponseEntity atualizarArmazem(@PathVariable("id") int id,
	                                      @RequestBody Contrato contrato) {
	   return contratoRepository.findById(id)
	           .map(record -> {
	               record.setValor_tonelada(contrato.getValor_tonelada());
	               record.setCadencia(contrato.getCadencia());
	               record.setInicio_vigencia(contrato.getInicio_vigencia());
	               record.setFim_vigencia(contrato.getFim_vigencia());

	               Contrato updated = contratoRepository.save(record);
	               return ResponseEntity.ok().body(updated);
	           }).orElse(ResponseEntity.notFound().build());
	}
	
}
