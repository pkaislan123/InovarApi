package com.inovar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovar.models.Contrato;
import com.inovar.models.Lote;

@Repository
public interface LoteRepository  extends JpaRepository<Lote, Integer> {


	
	@Query(value =  "select lt.* from lote lt left join contrato ct on ct.id = lt.contrato_id where ct.contratante_id = ?", nativeQuery = true)
	List<Lote> listarPorContratante(int contratante_id);
	
	
}
