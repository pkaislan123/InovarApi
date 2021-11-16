package com.inovar.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "laudo")
public class Laudo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	private int tipo;

	// variaveis tipo livre
	@OneToOne
	@JoinColumn(name = "cliente_id", referencedColumnName = "id")
	private User cliente;

	@OneToOne
	@JoinColumn(name = "safra_id", referencedColumnName = "id")
	private Safra safra;

	// variavies tipo contrato
	@OneToOne
	@JoinColumn(name = "contrato_id", referencedColumnName = "id")
	private Contrato contrato;

	@OneToOne
	@JoinColumn(name = "armazem_origem_id", referencedColumnName = "id")
	private User armazem_origem;
	
	@OneToOne
	@JoinColumn(name = "endereco_armazem_origem_id", referencedColumnName = "id")
	private Endereco endereco_armazem_origem;

	// variavies tipo lote
	@OneToOne
	@JoinColumn(name = "lote_id", referencedColumnName = "id") 
	private Lote lote;

	@OneToOne
	@JoinColumn(name = "classificador_id", referencedColumnName = "id")
	private User classificador;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate data;

	private String nota_fiscal;

	@NotNull
	private Double peso;

	@NotEmpty
	private String placa;
	
	private int teste_gmo;
	private int resultado_teste_gmo;
	private int nf_declarada;
	
	
	private String destino;
	
	private Double umidade, impureza, queimados,mofados, ardidos,
	fermentados, picados, imaturos, total_avariados, esverdeados, quebrados;
	

}
