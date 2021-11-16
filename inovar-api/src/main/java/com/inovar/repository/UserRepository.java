package com.inovar.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inovar.models.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);

	Optional<User> findById(int id);

	Optional<User> findByCpf(String cpf);

	Optional<User> findByEmail(String e);

	Optional<User> findByCnpj(String cnpj);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	@Query(value =  "select * from users where tipo = ?", nativeQuery = true)
	List<User> listar (int tipo);

	@Query(value =  "select id, razao_social, cnpj from users where tipo = 3", nativeQuery = true)
	List<User> listarArmazens ( );

	
	
}
