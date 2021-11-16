package com.inovar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inovar.models.ERole;
import com.inovar.models.Role;




@Repository
public  interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(ERole name);


}
