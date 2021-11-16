package com.inovar.utils;

import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inovar.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpArmazemRequest {

	@NotNull
	private int tipo;
	
	@NotNull
	private String razao_social;
	
	@NotNull
	private String nome_fantasia;
	
	@NotNull
	private String cnpj;
	
	@NotNull
	private String ie;
	
	private String descricao;
	
	
	private String at_primaria;
	
	private String at_secundaria;
	

	
	private String porte;
	
	private String atividade;
	
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
