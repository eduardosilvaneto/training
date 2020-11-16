package com.training.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Person {
	
	@GeneratedValue
	@Id
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="age")
	private int age;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public Person() {
	}
	
	public Person(String name , int age) {
		super();
		this.name = name;
		this.age = age;
	}
}
