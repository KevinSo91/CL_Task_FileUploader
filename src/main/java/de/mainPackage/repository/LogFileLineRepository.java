package de.mainPackage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.mainPackage.model.LogFileLine;

@Repository
public interface LogFileLineRepository extends JpaRepository<LogFileLine, Integer>{

}
