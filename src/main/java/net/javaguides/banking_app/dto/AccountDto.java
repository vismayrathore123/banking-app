package net.javaguides.banking_app.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDto {

    private long id;

    @NotNull(message = "Account holder name is required")
    @Size(min = 2, message = "Account holder name must have at least 2 characters")
    private String accountHolderName;

    @Min(value = 0, message = "Balance must be a positive number")
    private double balance;
}
