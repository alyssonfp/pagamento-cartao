package com.bbdesafio.pagamentoscartao.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long id;

    @NotNull(message = "Número do cartão não pode ser nulo")
    @Size(min = 16, max = 16, message = "O número do cartão deve ter 16 dígitos")
    private String cardNumber;

    @NotNull(message = "Nome do titular não pode ser nulo")
    private String cardHolderName;

    @NotNull(message = "Data de validade não pode ser nula")
    @Pattern(regexp = "(0[1-9]|1[0-2])/([0-9]{2})", message = "A data de validade deve estar no formato MM/YY")
    private String expiryDate;

    @NotNull(message = "CVV não pode ser nulo")
    @Size(min = 3, max = 4, message = "CVV deve ter 3 ou 4 dígitos")
    private String cvv;

    @NotNull(message = "Valor da transação não pode ser nulo")
    @Positive(message = "O valor da transação deve ser positivo")
    private Double amount;

    private String billingAddress;

    @Email(message = "Email do titular deve ser válido")
    private String cardHolderEmail;

    public Pagamento() {}

    public Pagamento(Long id, String cardNumber, String cardHolderName, String expiryDate, String cvv, Double amount, String billingAddress, String cardHolderEmail) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.amount = amount;
        this.billingAddress = billingAddress;
        this.cardHolderEmail = cardHolderEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getCardHolderEmail() {
        return cardHolderEmail;
    }

    public void setCardHolderEmail(String cardHolderEmail) {
        this.cardHolderEmail = cardHolderEmail;
    }
}
