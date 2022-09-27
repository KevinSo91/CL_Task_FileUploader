package de.mainPackage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.mainPackage.model.Hilfestellung;

@Repository
public interface HilfestellungRepository extends JpaRepository<Hilfestellung, Integer>{
	
}