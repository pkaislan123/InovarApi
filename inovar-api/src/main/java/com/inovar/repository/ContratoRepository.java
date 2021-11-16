package com.inovar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovar.models.Contrato;
import com.inovar.models.User;

@Repository
public interface ContratoRepository  extends JpaRepository<Contrato, Integer>  {

	
	
	@Query(value =  "select * from contrato where contratante_id = ?", nativeQuery = true)
	List<Contrato> listarPorContratante(int contratante_id);
	
	@Query(value =  "select max(id) as id from contrato", nativeQuery = true)
	Integer ultimoId();
	
}
