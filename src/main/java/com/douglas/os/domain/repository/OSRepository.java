package com.douglas.os.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.douglas.os.domain.entity.OS;

@Repository
public interface OSRepository extends JpaRepository <OS,Integer> {
	
	@Query(value = "SELECT valor FROM os", nativeQuery = true)
	public  List<String> getValor();


}
