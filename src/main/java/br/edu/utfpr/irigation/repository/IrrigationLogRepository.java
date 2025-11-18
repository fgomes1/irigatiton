package br.edu.utfpr.irigation.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.utfpr.irigation.model.IrrigationLog;

@Repository
public interface IrrigationLogRepository extends JpaRepository<IrrigationLog, UUID> {
}
