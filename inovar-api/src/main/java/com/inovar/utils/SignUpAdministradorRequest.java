package com.inovar.utils;

import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpAdministradorRequest {

	@NotNull
	private int tipo;
	
	private String nome;
	private String sobrenome;
	private String cpf;
	private String nascimento;
	private String rg;
	private String ctps;
	private String titulo_eleitor;
	
	private String cnh;
	
	@NotBlank
	@Size(max = 20)
	private String username;
	
	
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;
    
    private Set<String> regra;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
  
	
}
