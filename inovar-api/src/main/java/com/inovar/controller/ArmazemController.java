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
@RequestMapping("v1/protected/armazens")
public class ArmazemController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(path = "/listar")
	public List<User> retornarArmazens() {
		
		return userRepository.listar(3);
	}
	
	
	@ApiOperation(value = "Listar por Id")
	@GetMapping("/listar/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Optional<User> listarPorId(@PathVariable int id) {
		Optional<User> user =  userRepository.findById(id);
		return user;
	}
	
	
	@CrossOrigin
	@ApiOperation(value = "Excluir Armazem")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(path = "/excluir/{id}")
	public ResponseEntity<Void> excluirArmazem(@PathVariable int id) {
		
		userRepository.deleteById(id);
		return ResponseEntity.noContent().build();

	}
	
	@CrossOrigin
	@ApiOperation(value = "Atualizar Armazem")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(path = "/atualizar/{id}")
	public ResponseEntity atualizarArmazem(@PathVariable("id") int id,
	                                      @RequestBody User user) {
	   return userRepository.findById(id)
	           .map(record -> {
	               record.setRazao_social(user.getRazao_social());
	               record.setNome_fantasia(user.getNome_fantasia());
	               record.setIe(user.getIe());
	               record.setPorte(user.getPorte());
	               record.setAtividade(user.getAtividade());
	               record.setDescricao(user.getDescricao());

	               User updated = userRepository.save(record);
	               return ResponseEntity.ok().body(updated);
	           }).orElse(ResponseEntity.notFound().build());
	}
	
}
