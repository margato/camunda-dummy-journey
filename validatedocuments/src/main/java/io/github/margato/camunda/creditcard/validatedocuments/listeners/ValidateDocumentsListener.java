package io.github.margato.camunda.creditcard.validatedocuments.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.margato.camunda.creditcard.validatedocuments.exceptions.InvalidDocumentsException;
import io.github.margato.camunda.creditcard.validatedocuments.models.Customer;
import io.github.margato.camunda.creditcard.validatedocuments.usecases.ValidateDocumentsUseCase;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.client.task.impl.dto.BpmnErrorRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@ExternalTaskSubscription("validateDocuments")
@Component
public class ValidateDocumentsListener implements ExternalTaskHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ValidateDocumentsUseCase validateDocumentsUseCase;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${camunda.bpm.client.worker-id}")
    private String workerId;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        logger.info("Executing task {}", externalTask.getExecutionId());
        Map<String, Object> variables = externalTask.getAllVariables();
        Customer customer = objectMapper.convertValue(variables, Customer.class);

        try {
            validateDocumentsUseCase.validate(customer);
            externalTaskService.complete(externalTask);
            logger.info("Task {} completed.", externalTask.getExecutionId());
        } catch (InvalidDocumentsException e) {
           externalTaskService.handleBpmnError(externalTask, workerId);
            logger.info("Task {} completed with error.", externalTask.getExecutionId());
        }
    }
}
