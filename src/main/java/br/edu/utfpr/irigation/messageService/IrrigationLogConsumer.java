package br.edu.utfpr.irigation.messageService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.utfpr.irigation.dto.IrrigationLogDTO;
import br.edu.utfpr.irigation.model.IrrigationLog;
import br.edu.utfpr.irigation.service.IrrigationLogService;

@Component
public class IrrigationLogConsumer {

    @Autowired
    private IrrigationLogService logService;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConfig.IRRIGATION_LOG_QUEUE)
    public void receiveLog(String message) {
        try {
            System.out.println("Received irrigation log: " + message);

            // Deserializar a mensagem JSON
            IrrigationLogDTO logDTO = objectMapper.readValue(message, IrrigationLogDTO.class);

            // Criar e salvar o log no banco
            IrrigationLog log = new IrrigationLog();
            log.setMessage(logDTO.getMessage());
            log.setUserId(logDTO.getUserId());

            IrrigationLog savedLog = logService.saveLog(log);

            System.out.println("Log saved successfully with ID: " + savedLog.getId());
        } catch (Exception e) {
            System.err.println("Error processing irrigation log: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
