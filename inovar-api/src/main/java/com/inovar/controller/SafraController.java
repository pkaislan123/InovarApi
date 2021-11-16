package com.inovar.controller;

import java.util.List;

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

import com.inovar.models.Produto;
import com.inovar.models.Safra;
import com.inovar.repository.ProdutoRepository;
import com.inovar.repository.SafraRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@Api(value = "Safras")
@RequestMapping("v1/protected/safras")
public class SafraController {

	@Autowired
	SafraRepository safraRepository;
	
	@ApiOperation(value = "Listar Safras")
	@GetMapping("/listar")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Safra> listarProdutos() {
		return safraRepository.findAll();
	}
	
	
	@ApiOperation(value = "Cadastrar Safra")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(path = "/cadastrar")
	public boolean inserirSafra(@RequestBody Safra safra) {
		
		return safraRepository.save(safra) != null;

	}
	
	@ApiOperation(value = "Excluir Safra")
	@DeleteMapping(path = "/excluir/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> excluirSafra(@PathVariable int id) {
		safraRepository.deleteById(id);
		return ResponseEntity.noContent().build();

	}
	
}
