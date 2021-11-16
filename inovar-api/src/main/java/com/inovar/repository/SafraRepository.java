package com.inovar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inovar.models.Produto;
import com.inovar.models.Safra;

public interface SafraRepository extends JpaRepository<Safra, Integer> {

}
