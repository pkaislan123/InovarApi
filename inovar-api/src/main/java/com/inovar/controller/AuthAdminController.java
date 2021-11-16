package com.inovar.controller;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import com.inovar.configs.JwtUtils;
import com.inovar.models.ERole;
import com.inovar.models.Role;
import com.inovar.models.User;
import com.inovar.repository.RoleRepository;
import com.inovar.repository.UserRepository;
import com.inovar.services.UserDetailsImpl;
import com.inovar.utils.JwtResponse;
import com.inovar.utils.LoginRequest;
import com.inovar.utils.MessageResponse;
import com.inovar.utils.SignUpAdministradorRequest;
import com.inovar.utils.SignUpArmazemRequest;
import com.inovar.utils.SignUpClassificadorRequest;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("v1/admin/auth")
public class AuthAdminController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword() ));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		JwtResponse resposta = new JwtResponse(token, userDetails.getId(), roles);
		
	 	return ResponseEntity.ok(resposta);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('CLASSIFICADOR') or hasRole('ARMAZEM')")
	@GetMapping(path = "retornardados/{id}")
	public Optional<User> retornarDadosCliente(@PathVariable int id) {
		
		return userRepository.findById(id);
	}

	

	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/cadastraradministrador")
	public ResponseEntity<?> cadastrarAdministrador(@Valid @RequestBody SignUpAdministradorRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Login para administrador já existe"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email para administrador já existe"));
		}

		// Create new user's account
		User user = new User();
		
		user.setUsername(signUpRequest.getUsername());
		user.setPassword( encoder.encode(signUpRequest.getPassword()));
		user.setEmail( signUpRequest.getEmail());
		user.setTipo(signUpRequest.getTipo());
		user.setCpf(signUpRequest.getCpf());
		user.setNome(signUpRequest.getNome());
		user.setSobrenome(signUpRequest.getSobrenome());
		user.setRg(signUpRequest.getRg());
		user.setCtps(signUpRequest.getCtps());
		user.setTitulo_eleitor(signUpRequest.getTitulo_eleitor());
		user.setCnh(signUpRequest.getCnh());
		user.setStatus(1);

		Set<String> strRoles = signUpRequest.getRegra();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_ARMAZEM)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "classificador":
					Role modRole = roleRepository.findByName(ERole.ROLE_CLASSIFICADOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_ARMAZEM)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("Administrador registered successfully!"));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/cadastrararmazem")
	public ResponseEntity<?> cadastrarArmazem(@Valid @RequestBody SignUpArmazemRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Usuario para o aramzem ja existe!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email para o aramzem ja existe!"));
		}

		// Create new user's account
		User user = new User();
		
		user.setUsername(signUpRequest.getUsername());
		user.setPassword( encoder.encode(signUpRequest.getPassword()));
		user.setEmail( signUpRequest.getEmail());
		user.setTipo(signUpRequest.getTipo());
		user.setCnpj(signUpRequest.getCnpj());
		user.setIe(signUpRequest.getIe());
		user.setRazao_social(signUpRequest.getRazao_social());
		user.setNome_fantasia(signUpRequest.getNome_fantasia());
		user.setAtividade(signUpRequest.getAtividade());
		user.setAt_primaria(signUpRequest.getAt_primaria());
		user.setAt_secundaria(signUpRequest.getAt_secundaria());
		user.setDescricao(signUpRequest.getDescricao());
		user.setPorte(signUpRequest.getPorte());
		user.setStatus(1);
		user.setData_cadastro(signUpRequest.getData_cadastro());

		Set<String> strRoles = signUpRequest.getRegra();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_ARMAZEM)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "classificador":
					Role modRole = roleRepository.findByName(ERole.ROLE_CLASSIFICADOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_ARMAZEM)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("Armazem registered successfully!"));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/cadastrarclassificador")
	public ResponseEntity<?> cadastrarClassificador(@Valid @RequestBody SignUpClassificadorRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Usuario para o classificador ja existe!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email para o classificador ja existe!"));
		}

		// Create new user's account
		User user = new User();
		
		user.setUsername(signUpRequest.getUsername());
		user.setPassword( encoder.encode(signUpRequest.getPassword()));
		user.setEmail( signUpRequest.getEmail());
		user.setTipo(signUpRequest.getTipo());
		user.setCpf(signUpRequest.getCpf());
		user.setNome(signUpRequest.getNome());
		user.setNascimento(signUpRequest.getNascimento());
		user.setSobrenome(signUpRequest.getSobrenome());
		user.setRg(signUpRequest.getRg());
		user.setCtps(signUpRequest.getCtps());
		user.setTitulo_eleitor(signUpRequest.getTitulo_eleitor());
		user.setCnh(signUpRequest.getCnh());
		user.setStatus(1);
		user.setData_cadastro(signUpRequest.getData_cadastro());
		
		
		Set<String> strRoles = signUpRequest.getRegra();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_ARMAZEM)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "classificador":
					Role modRole = roleRepository.findByName(ERole.ROLE_CLASSIFICADOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_ARMAZEM)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("Classificador registered successfully!"));
	}
	

	
	@CrossOrigin
	@ApiOperation(value = "Busca Por Cpf")
	@GetMapping("buscarcpf/{cpf}")
	@PreAuthorize("hasRole('ADMIN')")
	public int buscarPorCpf(@PathVariable String cpf) {
		Optional<User> user = userRepository.findByCpf(cpf);

		if (user.isEmpty()) {
			return 0;
			
		} else {
			return 1;
		}

	}
	
	@CrossOrigin
	@ApiOperation(value = "Busca Por Cnpj")
	@GetMapping("buscarcnpj/{cnpj}")
	@PreAuthorize("hasRole('ADMIN')")
	public int buscarPorCnpj(@PathVariable String cnpj) {
		Optional<User> user = userRepository.findByCnpj(cnpj);

		if (user.isEmpty()) {
			return 0;
			
		} else {
			return 1;
		}

	}
	
	
	
	@CrossOrigin
	@ApiOperation(value = "Busca Por Usuário")
	@GetMapping("buscarusername/{username}")
	@PreAuthorize("hasRole('ADMIN')")
	public int buscarPorUsername(@PathVariable String username) {
		Optional<User> user = userRepository.findByUsername(username);

		if (user.isEmpty()) {
			return 0;
			
		} else {
			return 1;
		}

	}
	

	@ApiOperation(value = "Busca Por E-Mail")
	@GetMapping("buscaremail/{email}")
	@PreAuthorize("hasRole('ADMIN')")
	public int buscarPorEmail(@PathVariable String email) {
		Optional<User> user = userRepository.findByEmail(email);

		if(user.isEmpty()) {
			//throw new UsernameNotFoundException("Usuário [" + cliente + "] não encontradto!");
			return 0;
		}else {
           return 1;
		
		}

	}

	
}
