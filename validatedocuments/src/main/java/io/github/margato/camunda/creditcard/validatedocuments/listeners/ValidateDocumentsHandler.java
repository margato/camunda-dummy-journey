package io.github.margato.camunda.creditcard.validatedocuments.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.margato.camunda.creditcard.validatedocuments.model.Customer;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@ExternalTaskSubscription("validateDocuments")
@Component
public class ValidateDocumentsHandler implements ExternalTaskHandler {
    @Autowired
    private ObjectMapper objectMapper;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        logger.info("Executing task {}", externalTask.getExecutionId());
        Map<String, Object> variables = externalTask.getAllVariables();
        Customer customer = objectMapper.convertValue(variables, Customer.class);

        boolean isCpfValid = customer.getCpf() != null && customer.getCpf().length() == 11;
        boolean isPhoneValid = customer.getPhone() != null && customer.getPhone().length() == 11;
        boolean areDocumentsValid = isCpfValid && isPhoneValid;

        VariableMap output = Variables.createVariables();
        output.putValue("cpf", customer.getCpf());
        output.putValue("are_documents_valid", areDocumentsValid);

        externalTaskService.complete(externalTask, output);

        logger.info("Task {} completed. Validation result: {}", externalTask.getExecutionId(), areDocumentsValid);
    }
}
