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

import com.inovar.models.Contrato;
import com.inovar.models.Endereco;
import com.inovar.models.Produto;
import com.inovar.repository.ProdutoRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@Api(value = "Produtos")
@RequestMapping("v1/protected/produtos")
public class ProdutoController {
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@ApiOperation(value = "Listar Produtos")
	@GetMapping("/listar")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Produto> listarProdutos() {
		return produtoRepository.findAll();
	}
	
	
	@ApiOperation(value = "Cadastrar Produto")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(path = "/cadastrar")
	public boolean inserirNovoProduto(@RequestBody Produto produto) {
		
		return produtoRepository.save(produto) != null;

	}
	

	@ApiOperation(value = "Excluir Produto")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(path = "/excluir/{id}")
	public ResponseEntity<Void> excluirProduto(@PathVariable int id) {
		
		produtoRepository.deleteById(id);
		return ResponseEntity.noContent().build();

	}
	
}
