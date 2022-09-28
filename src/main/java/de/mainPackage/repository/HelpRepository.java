package de.mainPackage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.mainPackage.model.Help;

@Repository
public interface HelpRepository extends JpaRepository<Help, Integer>{
	
}