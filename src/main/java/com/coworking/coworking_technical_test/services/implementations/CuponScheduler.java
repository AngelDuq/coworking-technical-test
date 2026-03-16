package com.coworking.coworking_technical_test.services.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.coworking.coworking_technical_test.exceptions.MessageKey;
import com.coworking.coworking_technical_test.exceptions.MessageResolver;
import com.coworking.coworking_technical_test.services.interfaces.ICuponService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CuponScheduler {

    private static final Logger log = LoggerFactory.getLogger(CuponScheduler.class);
    private final ICuponService cuponService;
    private final MessageResolver messageResolver;

    @Scheduled(cron = "0 0 0 * * *")
    public void expirarCuponesVencidos() {
        log.info(messageResolver.get(MessageKey.LOG_CUPON_SCHEDULER_INICIO));
        cuponService.expirarCuponesVencidos();
        log.info(messageResolver.get(MessageKey.LOG_CUPON_SCHEDULER_FIN));
    }

}
