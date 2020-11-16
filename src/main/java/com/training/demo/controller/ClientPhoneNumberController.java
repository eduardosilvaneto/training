package com.training.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.training.demo.models.ClientPhoneNumber;
import com.training.demo.models.Logs;
import com.training.demo.repositories.ClientPhoneNumberRepository;
import com.training.demo.repositories.LogsRepository;

@RestController
public class ClientPhoneNumberController {
	
	private ClientPhoneNumberRepository clientPhoneNumberRepository;
	private LogsRepository logsRepository;
	
	public ClientPhoneNumberController(ClientPhoneNumberRepository clientPhoneNumberRepository , LogsRepository logsRepository) {
		super();
		this.clientPhoneNumberRepository = clientPhoneNumberRepository;
		this.logsRepository = logsRepository;
	}
	
	@PostMapping
	public ResponseEntity<ClientPhoneNumber> save (@RequestBody ClientPhoneNumber clientPhoneNumber){
		clientPhoneNumberRepository.save(clientPhoneNumber);
		return new ResponseEntity<>(clientPhoneNumber,HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<ClientPhoneNumber>> getAll(){
		List<ClientPhoneNumber> clientPhones = new ArrayList<>();
		clientPhones =  clientPhoneNumberRepository.findAll();
		return new ResponseEntity<>(clientPhones,HttpStatus.OK);
	}
	
	@GetMapping(path="/{id}")
	public ResponseEntity<Optional<ClientPhoneNumber>> getById(@PathVariable Long id) {
		Optional<ClientPhoneNumber> ClientPhoneNumber;
		try {
			ClientPhoneNumber = clientPhoneNumberRepository.findById(id);
			return new ResponseEntity<Optional<ClientPhoneNumber>>(ClientPhoneNumber,HttpStatus.OK);
		} catch (NoSuchElementException e) {
			logsRepository.save(new Logs("No data found element [" + id + "]", 2 , ClientPhoneNumberController.class.getSimpleName() + "." + ClientPhoneNumberController.class.getCanonicalName()));
			return new ResponseEntity<Optional<ClientPhoneNumber>>(HttpStatus.NOT_FOUND);
		}
	}
	
	public ResponseEntity<Optional<ClientPhoneNumber>> deleteById(@PathVariable Long id) {
		try {
			clientPhoneNumberRepository.deleteById(id);
			return new ResponseEntity<Optional<ClientPhoneNumber>>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Optional<ClientPhoneNumber>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(path = "/{id}")
	public ResponseEntity<ClientPhoneNumber> update(@PathVariable Long id, @RequestBody ClientPhoneNumber newClientPhoneNumber) {
		try {
			logsRepository.save(new Logs("Sucess",2,ClientPhoneNumberController.class.getSimpleName() + "." + "update"));
			return clientPhoneNumberRepository.findById(id).map(ClientPhoneNumber -> {
				ClientPhoneNumber.setName(newClientPhoneNumber.getName());
				ClientPhoneNumber.setPhoneNumber(newClientPhoneNumber.getPhoneNumber());
				ClientPhoneNumber ClientPhoneNumberUpdate = clientPhoneNumberRepository.save(ClientPhoneNumber);
				return ResponseEntity.ok(ClientPhoneNumberUpdate);
			}).orElse(ResponseEntity.notFound().build());
		} catch (NoSuchElementException e) {
			logsRepository.save(new Logs("No data found element [" + id + "]", 2 , ClientPhoneNumberController.class.getSimpleName() + "." + ClientPhoneNumberController.class.getCanonicalName()));
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
