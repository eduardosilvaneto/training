package com.training.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.demo.models.Logs;
import com.training.demo.models.Person;
import com.training.demo.repositories.LogsRepository;
import com.training.demo.repositories.PersonRepository;

@RestController
@RequestMapping(path="/persons")
public class PersonContoller {

	private PersonRepository personRepository;
	private LogsRepository logsRepository;
	
	public PersonContoller(PersonRepository personRepository , LogsRepository logsRepository) {
		super();
		this.personRepository = personRepository;
		this.logsRepository = logsRepository;
	}
	
	@PostMapping
	public ResponseEntity<Person> save (@RequestBody Person person){
		personRepository.save(person);
		return new ResponseEntity<>(person,HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<Person>> getAll(){
		List<Person> persons = new ArrayList<>();
		persons =  personRepository.findAll();
		return new ResponseEntity<>(persons,HttpStatus.OK);
	}
	
	@GetMapping(path="/{id}")
	public ResponseEntity<Optional<Person>> getById(@PathVariable Long id) {
		Optional<Person> person;
		try {
			person = personRepository.findById(id);
			return new ResponseEntity<Optional<Person>>(person,HttpStatus.OK);
		} catch (NoSuchElementException e) {
			logsRepository.save(new Logs("No data found element [" + id + "]", 2 , PersonContoller.class.getSimpleName() + "." + PersonContoller.class.getCanonicalName()));
			return new ResponseEntity<Optional<Person>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Optional<Person>> deleteById(@PathVariable Long id) {
		try {
			personRepository.deleteById(id);
			return new ResponseEntity<Optional<Person>>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Optional<Person>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(path = "/{id}")
	public ResponseEntity<Person> update(@PathVariable Long id, @RequestBody Person newPerson) {
		try {
			logsRepository.save(new Logs("Sucess",2,PersonContoller.class.getSimpleName() + "." + "update"));
			return personRepository.findById(id).map(person -> {
				person.setName(newPerson.getName());
				person.setAge(newPerson.getAge());
				Person personUpdate = personRepository.save(person);
				return ResponseEntity.ok(personUpdate);
			}).orElse(ResponseEntity.notFound().build());
		} catch (NoSuchElementException e) {
			logsRepository.save(new Logs("No data found element [" + id + "]", 2 , PersonContoller.class.getSimpleName() + "." + PersonContoller.class.getCanonicalName()));
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
