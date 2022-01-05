package io.github.margato.camunda.creditcard.proposal.usecases;

import io.github.margato.camunda.creditcard.proposal.models.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ValidateCreditUseCase {

    public boolean validate(Customer customer) {
        if (customer.getAge() > 18) {
            return customer.getIncome() > 1200.0;
        }

        return false;
    }

}
