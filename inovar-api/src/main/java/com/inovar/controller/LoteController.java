package com.inovar.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inovar.models.Contrato;
import com.inovar.models.Lote;
import com.inovar.repository.ContratoRepository;
import com.inovar.repository.LoteRepository;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("v1/protected/lotes")
public class LoteController {

	@Autowired
	LoteRepository loteRepository;
	

	@ApiOperation(value = "Listar Lotes")
	@GetMapping("/listar")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Lote> listarContratos() {
		return loteRepository.findAll();
	}
	
	
	@ApiOperation(value = "Listar Lote Por Id")
	@GetMapping("/listar/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Optional<Lote> listarPorId(@PathVariable int id) {
		Optional<Lote> lote =  loteRepository.findById(id);
		return lote;
	}
	
	
	@CrossOrigin
	@ApiOperation(value = "Excluir Lote")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(path = "/excluir/{id}")
	public ResponseEntity<Void> excluirLote(@PathVariable int id) {
		
		loteRepository.deleteById(id);
		return ResponseEntity.noContent().build();

	}
	
	
	@ApiOperation(value = "Cadastrar Lote")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(path = "/cadastrar")
	public boolean inserirContrato(@RequestBody Lote lote) {
		
		return loteRepository.save(lote) != null;

	}
	
	@ApiOperation(value = "Listar Lotes Por Contratante")
	@GetMapping("/listar_contratante/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ARMAZEM')")
	public List<Lote> listarPorContratante(@PathVariable int id) {
		return  loteRepository.listarPorContratante(id);
	}
	
}
