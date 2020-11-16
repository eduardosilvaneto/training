package com.training.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.training.demo.models.Logs;

@Repository
public interface LogsRepository extends JpaRepository<Logs, Long> {

}
