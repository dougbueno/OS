package com.douglas.os.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.douglas.os.domain.entity.OS;

@Repository
public interface OSRepository extends JpaRepository <OS,Integer> {


}
