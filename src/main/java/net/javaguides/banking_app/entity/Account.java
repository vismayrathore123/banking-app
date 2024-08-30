package net.javaguides.banking_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Account holder name must not be blank")
    @Column(name = "account_holder_name")
    private String accountHolderName;

    @Min(value = 0, message = "Balance must be zero or positive")
    @Column(nullable = false)
    private double balance;
}
