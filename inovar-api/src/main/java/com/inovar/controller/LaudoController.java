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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inovar.models.Contrato;
import com.inovar.models.Laudo;
import com.inovar.models.Lote;
import com.inovar.models.User;
import com.inovar.repository.LaudoRepository;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/protected/laudos")
public class LaudoController {

	@Autowired
	LaudoRepository laudoRepository;
	
	@CrossOrigin
	@ApiOperation(value = "Listar Laudos")
	@GetMapping("/listar")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Laudo> listarLaudos() {
		return laudoRepository.findAll();
	}
	
	@CrossOrigin
	@ApiOperation(value = "Listar Laudo Por Id")
	@GetMapping("/listar/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Optional<Laudo> listarPorId(@PathVariable int id) {
		Optional<Laudo> laudo =  laudoRepository.findById(id);
		return laudo;
	}
	
	@CrossOrigin
	@ApiOperation(value = "Listar Laudo Por Lote")
	@GetMapping("/listarporlote/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ARMAZEM')")
	public List<Laudo> listarPorLote(@PathVariable int id) {
		return   laudoRepository.listarPorLote(id);
	}
	
	@CrossOrigin
	@ApiOperation(value = "Listar Laudo Por Contratante")
	@GetMapping("/listar_contratante/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('ARMAZEM')")
	public List<Laudo> listarPorContratante(@PathVariable int id) {
		return   laudoRepository.listarPorContratante(id, id, id);
	}
	
	
	
	@CrossOrigin
	@ApiOperation(value = "Excluir Laudo")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(path = "/excluir/{id}")
	public ResponseEntity<Void> excluirLote(@PathVariable int id) {
		
		laudoRepository.deleteById(id);
		return ResponseEntity.noContent().build();

	}
	
	@CrossOrigin
	@ApiOperation(value = "Cadastrar Laudo")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(path = "/cadastrar")
	public boolean inserirLaudo(@RequestBody Laudo laudo) {
		
		return laudoRepository.save(laudo) != null;


}
	
	
	
	
	@CrossOrigin
	@ApiOperation(value = "Atualizar Laudo")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(path = "/atualizar/{id}")
	public ResponseEntity atualizarArmazem(@PathVariable("id") int id,
	                                      @RequestBody Laudo laudo) {
	   return laudoRepository.findById(id)
	           .map(record -> {


	        	   int tipo = laudo.getTipo();
	        	   if(tipo == 0) {
	        		   //livre
	        		   record.setCliente(laudo.getCliente());
	        		   record.setArmazem_origem(laudo.getArmazem_origem());
	        		   record.setEndereco_armazem_origem(laudo.getEndereco_armazem_origem());
	        		   record.setSafra(laudo.getSafra());
	        	   }else if(tipo == 1) {
	        		   //contratato
	        		   record.setContrato(laudo.getContrato());
	        		   record.setArmazem_origem(laudo.getArmazem_origem());
	        		   record.setEndereco_armazem_origem(laudo.getEndereco_armazem_origem());
	        	   }else {
	        		   //loteado
	        		   record.setLote(laudo.getLote());
	        		   
	        	   }
	        	   
        		   record.setTipo(tipo);

	        	   record.setDestino(laudo.getDestino());
	        	   record.setData(laudo.getData());
	        	   record.setPlaca(laudo.getPlaca());
	        	   record.setNota_fiscal(laudo.getNota_fiscal());
	        	   record.setPeso(laudo.getPeso());
	        	   
	        	   record.setNf_declarada(laudo.getNf_declarada());
	        	   record.setTeste_gmo(laudo.getTeste_gmo());
	        	   record.setResultado_teste_gmo(laudo.getResultado_teste_gmo());
	        	   
	        	   record.setClassificador(laudo.getClassificador());
	        	   
	        	   record.setUmidade(laudo.getUmidade());
	        	   record.setImpureza(laudo.getImpureza());
	        	   record.setQueimados(laudo.getQueimados());
	        	   record.setMofados(laudo.getMofados());
	        	   record.setArdidos(laudo.getArdidos());
	        	   record.setFermentados(laudo.getFermentados());
	        	   record.setPicados(laudo.getPicados());
	        	   record.setImaturos(laudo.getImaturos());
	        	   record.setTotal_avariados(laudo.getTotal_avariados());
	        	   record.setQuebrados(laudo.getQuebrados());
	        	   record.setEsverdeados(laudo.getEsverdeados());

	               Laudo updated = laudoRepository.save(record);
	               return ResponseEntity.ok().body(updated);
	           }).orElse(ResponseEntity.notFound().build());
	}
	
}
