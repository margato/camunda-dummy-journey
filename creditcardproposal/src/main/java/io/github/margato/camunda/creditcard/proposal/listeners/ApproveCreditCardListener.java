package io.github.margato.camunda.creditcard.proposal.listeners;

import io.github.margato.camunda.creditcard.proposal.usecases.ApproveCreditCardUseCase;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@ExternalTaskSubscription("approveCreditCard")
@Component
public class ApproveCreditCardListener implements ExternalTaskHandler {
    @Autowired
    private ApproveCreditCardUseCase approveCreditCardUseCase;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        logger.info("Executing task {}", externalTask.getExecutionId());

        String cpf = externalTask.getVariable("cpf");
        approveCreditCardUseCase.approve(cpf, externalTask.getProcessInstanceId());

        externalTaskService.complete(externalTask);

        logger.info("Task {} completed.", externalTask.getExecutionId());
    }
}
