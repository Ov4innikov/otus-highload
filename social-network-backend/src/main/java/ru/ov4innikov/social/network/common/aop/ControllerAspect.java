package ru.ov4innikov.social.network.common.aop;

import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
@Component
@Aspect
public class ControllerAspect {

    private final Map<String, AtomicInteger> methodsCounter = new ConcurrentHashMap<>();

    @PreDestroy
    public void destroy() {
        log.info("Closing context. Counters state is {}", methodsCounter);
    }

    @Around(value = "@within(org.springframework.stereotype.Controller) || @annotation(org.springframework.stereotype.Controller) || @within(io.micrometer.observation.annotation.Observed) || @annotation(io.micrometer.observation.annotation.Observed)")
    public Object measureMethodExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        if (!methodsCounter.containsKey(pjp.getSignature().getName()))
            methodsCounter.put(pjp.getSignature().getName(), new AtomicInteger(0));
        log.info("Calling method {}, {}", pjp.getSignature().getName(), methodsCounter.get(pjp.getSignature().getName()).incrementAndGet());
        try {
            return pjp.proceed();
        } finally {
            methodsCounter.get(pjp.getSignature().getName()).decrementAndGet();
        }
    }
}
