package io.github.margato.camunda.creditcard.validatedocuments.usecases;


import io.github.margato.camunda.creditcard.validatedocuments.exceptions.InvalidDocumentsException;
import io.github.margato.camunda.creditcard.validatedocuments.models.Customer;
import org.springframework.stereotype.Component;

@Component
public class ValidateDocumentsUseCase {

    public void validate(Customer customer) throws InvalidDocumentsException {
        boolean isCpfValid = customer.getCpf() != null && customer.getCpf().length() == 11;
        boolean isPhoneValid = customer.getPhone() != null && customer.getPhone().length() == 11;
        boolean isAgeValid = customer.getAge() != null && customer.getAge() > 0;
        boolean isIncomeValid = customer.getIncome() != null && customer.getIncome() >= 0;
        boolean areDocumentsValid = isCpfValid && isPhoneValid && isAgeValid && isIncomeValid;

        if (!areDocumentsValid) {
            throw new InvalidDocumentsException();
        }
    }

}
