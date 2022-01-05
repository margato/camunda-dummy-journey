package io.github.margato.camunda.creditcard.proposal.usecases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RejectCreditCardUseCase {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void reject(String cpf, String journeyId) {
        logger.info("Credit card rejected for cpf {}. Journey: {}", cpf, journeyId);
    }

}
