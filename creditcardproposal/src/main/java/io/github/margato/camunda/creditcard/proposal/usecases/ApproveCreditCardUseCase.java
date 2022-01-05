package io.github.margato.camunda.creditcard.proposal.usecases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ApproveCreditCardUseCase {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void approve(String cpf, String journeyId) {
        logger.info("Credit card approved for cpf {}. Jorney: {}", cpf, journeyId);
    }

}
