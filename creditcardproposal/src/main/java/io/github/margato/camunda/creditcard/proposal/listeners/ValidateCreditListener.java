package io.github.margato.camunda.creditcard.proposal.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.margato.camunda.creditcard.proposal.models.Customer;
import io.github.margato.camunda.creditcard.proposal.usecases.ValidateCreditUseCase;
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


@ExternalTaskSubscription("validateCredit")
@Component
public class ValidateCreditListener implements ExternalTaskHandler {
    @Autowired
    private ValidateCreditUseCase validateCreditUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        logger.info("Executing task {}", externalTask.getExecutionId());
        Map<String, Object> variables = externalTask.getAllVariables();

        Customer customer = objectMapper.convertValue(variables, Customer.class);
        boolean validationResult = validateCreditUseCase.validate(customer);

        VariableMap output = Variables.createVariables();
        output.putValue("is_credit_allowed", validationResult);
        externalTaskService.complete(externalTask, output);

        logger.info("Task {} completed.", externalTask.getExecutionId());
    }
}
