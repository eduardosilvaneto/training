package com.training.demo.metrics;

import java.util.Stack;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.training.demo.exception.ErrorCode;

@Aspect
@Component
@DependsOn("traceServiceImpl")
public class MetricService {
  private Logger LOGGER  = LoggerFactory.getLogger(MetricService.class);
  @Autowired
  TraceServiceImpl traceServiceImpl;
  private ThreadLocal<Stack<Long>> levelStack = new ThreadLocal<Stack<Long>>() {
    @Override
    protected Stack<Long> initialValue() {
      Stack<Long> stack = new Stack<Long>();
      stack.push(0L);
      return stack;
    }
  };

  @Around("execution(* com.training.demo..*.*(..)) "
  		+ "&& !execution(* com.training.demo.metrics.MetricService.*(..)) "
  		+ "&& !execution(* com.training.demo.metrics.TraceServiceImpl.*(..))")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    Object result = null;
    int index = 0;
    Long startTime = System.currentTimeMillis();
    try {
      levelStack.get().push(0L);
      result = joinPoint.proceed();
      Long elapsedTime = System.currentTimeMillis() - startTime;
      index = levelStack.get().size()-1;
      levelStack.get().set(index, levelStack.get().peek() + elapsedTime);     
      traceServiceImpl.setMethodMetrics(joinPoint.getSignature().getName(), elapsedTime);
    } catch (Exception e) {
      LOGGER.error(ErrorCode.DEMO_ERROR_0001.toString(),e);
    }finally {
      try {
        if (levelStack.get() != null) {
          if(levelStack.get().get(index) != null) {
            levelStack.get().remove(index);
          }
        }
      }catch (Exception e) {
        LOGGER.error(ErrorCode.DEMO_ERROR_0001.toString(),e);
      }
    }
    return result;
  }

}