package com.training.demo.metrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.training.demo.models.Logs;
import com.training.demo.repositories.LogsRepository;

import ch.qos.logback.classic.Logger;
import util.DateUtils;

@Service("traceServiceImpl")
public class TraceServiceImpl implements TraceService {
	protected static final Logger LOGGER = (Logger) LoggerFactory.getLogger(TraceServiceImpl.class);
	protected ConcurrentHashMap<String, List<Long>> methodMetrics;
	protected ConcurrentHashMap<String, List<Long>> cachedMetrics;

	private final ScheduledThreadPoolExecutor executor1 = new ScheduledThreadPoolExecutor(1);
	private final ScheduledThreadPoolExecutor executor2 = new ScheduledThreadPoolExecutor(1);
	private volatile boolean semaphore = false;	
	
	private LogsRepository logsRepository;
	
	public TraceServiceImpl	(LogsRepository logsRepository) {
		this.logsRepository = logsRepository;
		methodMetrics = new ConcurrentHashMap<String, List<Long>>();
		cachedMetrics = new ConcurrentHashMap<String, List<Long>>();
		executor1.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				performTrace();
			}
		}, 15, 35, TimeUnit.SECONDS);    // Agendamos para executar performTrace a cada período de 30 segundos
		
		executor2.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				performTrace();
			}
		}, 30, 60, TimeUnit.SECONDS);    // Agendamos para executar performTrace a cada período de 35 segundos
	}
	
	public ScheduledThreadPoolExecutor getExecutor1() {
		return executor1;
	}
	
	public ScheduledThreadPoolExecutor getExecutor2() {
		return executor2;
	}
	
	public void setMethodMetrics(String methodName, Long metrics) {
		ConcurrentMap<String, List<Long>> methodMetrics = null;
		
		if(semaphore) {
			methodMetrics = this.cachedMetrics;
		} else {
			methodMetrics = this.methodMetrics;
			synchronized(this) {
				if(!this.cachedMetrics.isEmpty() && methodMetrics != this.cachedMetrics) {
					this.methodMetrics.putAll(this.cachedMetrics);
					this.cachedMetrics.clear();
				}
			}
		}
		
		List<Long> metricList = methodMetrics.get(methodName);
		
		if(metricList != null) {
			metricList.add(metrics);
		} else {
			List<Long> newMetricList = Collections.synchronizedList(new LinkedList<Long>());
			newMetricList.add(metrics);
			methodMetrics.put(methodName, newMetricList);
		}
	}
	
	
	protected synchronized void performTrace() {
		
		if(this.methodMetrics.isEmpty()) return;
		
		semaphore = true;
		HashMap<String, List<Long>> methodMetricsClone = new HashMap<String, List<Long>>(this.methodMetrics);
		this.methodMetrics.clear();
		semaphore = false;
		
		List<String> methodNames = new ArrayList<String>();
		for (Map.Entry<String, List<Long>> metrics : methodMetricsClone.entrySet()) {
			methodNames.add(metrics.getKey());
		}	
		StringBuffer logs = new StringBuffer();
		
		for(int i = 0; i < methodMetricsClone.size(); ++i) {
			
			List<Long> metricsList = methodMetricsClone.get(methodNames.get(i));
			Long average = this.computeAverage(metricsList);
			
			Logs log = new Logs("Metrics",1,methodNames.get(i));
			log.setTime(average);
			log.setProcessDate(DateUtils.toDate());
			logsRepository.save(log);
			logs.append("\n [" + DateUtils.toString(DateUtils.toDate(), DateUtils.DATE_TIME_FORMAT_2) + "] Method Name: [" + methodNames.get(i) + "]: Total Executions: [" + metricsList.size() + "]: Time Average (ms): [" + average + "]");
			
		}
		LOGGER.info(logs.toString());
	}
	protected synchronized Long computeAverage(List<Long> metricsList) {
		Long sum = 0L;
		for(Long value : metricsList) {
			sum += value;
		}	
		return sum / metricsList.size();
	}
}
