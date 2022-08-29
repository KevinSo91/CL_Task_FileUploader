package de.mainPackage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.mainPackage.model.LogFile;

@Repository
public interface LogFileRepository extends JpaRepository<LogFile, Integer> {

}
