package com.inovar.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "endereco")
public class Endereco{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
    private int id_cliente;
	
	@NotEmpty
	private String nome_recebedor;
	
	@NotEmpty
	private String cep;
	
	@NotEmpty
	private String logradouro;
	
	@NotEmpty
	private String bairro;
	
	@NotEmpty
	private String numero;
	
	@NotEmpty
	private String cidade;
	
	@NotEmpty
	private String estado;
	
	
	
	private String complemento;
	
	
	@NotEmpty
	private String telefone_contato;
	
	
	
	private String info_adicional;
	
}