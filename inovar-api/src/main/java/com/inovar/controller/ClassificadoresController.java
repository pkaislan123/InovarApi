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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inovar.models.User;
import com.inovar.repository.UserRepository;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/protected/classificadores")
public class ClassificadoresController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;

	@ApiOperation(value = "Listar Classificadores")
	@GetMapping("/listar")
	@PreAuthorize("hasRole('ADMIN')")
	public List<User> listarClassificadores() {
		return userRepository.listar(2);
	}
	
	
	@ApiOperation(value = "Listar por Id")
	@GetMapping("/listar/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Optional<User> listarPorId(@PathVariable int id) {
		Optional<User> user =  userRepository.findById(id);
		return user;
	}
	
	
	@CrossOrigin
	@ApiOperation(value = "Excluir Classificador")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(path = "/excluir/{id}")
	public ResponseEntity<Void> excluirClassificador(@PathVariable int id) {
		
		userRepository.deleteById(id);
		return ResponseEntity.noContent().build();

	}
	
	@CrossOrigin
	@ApiOperation(value = "Atualizar Classificador")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(path = "/atualizar/{id}")
	public ResponseEntity atualizarClassificador(@PathVariable("id") int id,
	                                      @RequestBody User user) {
	   return userRepository.findById(id)
	           .map(record -> {
	               record.setNome(user.getNome());
	               record.setSobrenome(user.getSobrenome());
	               record.setCtps(user.getCtps());
	               record.setRg(user.getRg());
	               record.setCnh(user.getCnh());
	               record.setTitulo_eleitor(user.getTitulo_eleitor());

	               User updated = userRepository.save(record);
	               return ResponseEntity.ok().body(updated);
	           }).orElse(ResponseEntity.notFound().build());
	}
	
}
