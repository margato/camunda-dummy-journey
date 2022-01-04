package io.github.margato.camunda.creditcard.proposal.listeners;

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@ExternalTaskSubscription("rejectCreditCard")
@Component
public class RejectCreditCardHandler implements ExternalTaskHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        String cpf = externalTask.getVariable("cpf");
        logger.info("Credit card rejected for cpf {}", cpf);

        externalTaskService.complete(externalTask);
    }
}
