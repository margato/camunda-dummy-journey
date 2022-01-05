package io.github.margato.camunda.creditcard.proposal.listeners;

import io.github.margato.camunda.creditcard.proposal.usecases.RejectCreditCardUseCase;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@ExternalTaskSubscription("rejectCreditCard")
@Component
public class RejectCreditCardListener implements ExternalTaskHandler {
    @Autowired
    private RejectCreditCardUseCase rejectCreditCardUseCase;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        logger.info("Executing task {}", externalTask.getExecutionId());

        String cpf = externalTask.getVariable("cpf");
        rejectCreditCardUseCase.reject(cpf, externalTask.getProcessInstanceId());

        externalTaskService.complete(externalTask);

        logger.info("Task {} completed.", externalTask.getExecutionId());
    }
}
