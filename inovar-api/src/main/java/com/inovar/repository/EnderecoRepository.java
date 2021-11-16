package com.inovar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inovar.models.Endereco;



public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

	@Query(value =  "select * from endereco where id_cliente = ?", nativeQuery = true)
	List<Endereco> listByIdCliente (int id_cliente);

	
}
