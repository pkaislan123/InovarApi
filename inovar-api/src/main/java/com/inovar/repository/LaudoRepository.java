package com.inovar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovar.models.Contrato;
import com.inovar.models.Laudo;
import com.inovar.models.Lote;

@Repository
public interface LaudoRepository extends JpaRepository<Laudo, Integer>  {

	@Query(value =  "select * from laudo where lote_id = ?", nativeQuery = true)
	List<Laudo> listarPorLote(int lote_id);
	
	@Query(value =  "select ld.* from laudo ld\r\n"
			+ " left join contrato ct on ct.id = contrato_id\r\n"
			+ " left join lote lt on lt.id = lote_id\r\n"
			+ " left join contrato ct_loteado on ct_loteado.id = lt.contrato_id\r\n"
			+ "where \r\n"
			+ "case \r\n"
			+ "when tipo = 0 then cliente_id = ?\r\n"
			+ "when tipo = 1 then ct.contratante_id = ?\r\n"
			+ "when tipo = 2 then ct_loteado.contratante_id = ?\r\n"
			+ "end", nativeQuery = true)
	List<Laudo> listarPorContratante(int lote_id, int lote2_id, int lote3_id);
}
