package com.training.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.training.demo.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
