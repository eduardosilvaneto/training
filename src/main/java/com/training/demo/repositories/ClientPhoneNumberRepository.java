package com.training.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.training.demo.models.ClientPhoneNumber;

@Repository
public interface ClientPhoneNumberRepository extends JpaRepository<ClientPhoneNumber, Long>{
}
