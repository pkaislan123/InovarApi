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
public class SignUpClassificadorRequest {

	@NotNull
	private int tipo;
	
	@NotBlank
	@Size(max = 50)
	private String nome;
	
	@NotBlank
	@Size(max = 50)
	private String sobrenome;
	
	@NotBlank
	@Size(max = 11, min = 11)
	private String cpf;
	
	@NotBlank
	@Size(max = 11)
	private String nascimento;
	
	private String rg;
	private String ctps;
	private String titulo_eleitor;
	private String cnh;
	
	@NotBlank
	@Size(max = 20)
	private String username;
	
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime data_cadastro;
 
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;
    
    private Set<String> regra;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
  
	
}
