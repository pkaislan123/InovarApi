package com.inovar.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lote")
public class Lote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	@JoinColumn(name = "contrato_id", referencedColumnName = "id")
	private Contrato contrato;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate  data_abertura;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate data_fechamento;
	
	@OneToOne
	@JoinColumn(name = "armazem_origem_id", referencedColumnName = "id")
	private User armazem_origem;
	
	@OneToOne
	@JoinColumn(name = "endereco_origem_id", referencedColumnName = "id")
	private Endereco endereco_origem;
	
	@NotNull
	private Double quantidade;
	
}
