package com.training.demo.models;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Logs {
	
	@GeneratedValue
	@Id
	private Long id;
	
	@Column(name="processDate")
	private Date processDate;
	
	@Column(name="code")
	private Integer code;
	
	@Column(name="message")
	private String message;
	
	@Column(name="method")
	private String method;
	
	@Column(name="time")
	private Long time;
	
	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}
	
	public Logs(){
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Logs(String message , Integer code , String method) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
		this.processDate = cal.getTime();
		this.message = message;
		this.code = code;
		this.method = method;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}
}
