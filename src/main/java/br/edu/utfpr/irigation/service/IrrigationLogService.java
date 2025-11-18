package br.edu.utfpr.irigation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.irigation.model.IrrigationLog;
import br.edu.utfpr.irigation.repository.IrrigationLogRepository;

@Service
public class IrrigationLogService {

    @Autowired
    private IrrigationLogRepository logRepository;

    public IrrigationLog saveLog(IrrigationLog log) {
        return logRepository.save(log);
    }

    public IrrigationLog createLog(String message, String userId) {
        IrrigationLog log = new IrrigationLog();
        log.setMessage(message);
        log.setUserId(userId);
        return saveLog(log);
    }
}
