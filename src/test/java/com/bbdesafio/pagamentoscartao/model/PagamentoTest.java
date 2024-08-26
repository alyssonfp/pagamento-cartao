package com.bbdesafio.pagamentoscartao.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidPagamento() {
        Pagamento pagamento = new Pagamento(1L, "1234567812345678", "João Silva", "12/25", "123", 100.0, "Rua A, 123", "joao@example.com");
        Set<ConstraintViolation<Pagamento>> violations = validator.validate(pagamento);
        assertTrue(violations.isEmpty(), "Nenhuma violação esperada para um pagamento válido");
    }

    @Test
    void testInvalidCardNumber() {
        Pagamento pagamento = new Pagamento(1L, "1234", "João Silva", "12/25", "123", 100.0, "Rua A, 123", "joao@example.com");
        Set<ConstraintViolation<Pagamento>> violations = validator.validate(pagamento);
        assertFalse(violations.isEmpty(), "Devem haver violações para um número de cartão inválido");
        assertEquals("O número do cartão deve ter 16 dígitos", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidExpiryDate() {
        Pagamento pagamento = new Pagamento(1L, "1234567812345678", "João Silva", "13/25", "123", 100.0, "Rua A, 123", "joao@example.com");
        Set<ConstraintViolation<Pagamento>> violations = validator.validate(pagamento);
        assertFalse(violations.isEmpty(), "Devem haver violações para uma data de validade inválida");
        assertEquals("A data de validade deve estar no formato MM/YY", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidCVV() {
        Pagamento pagamento = new Pagamento(1L, "1234567812345678", "João Silva", "12/25", "12", 100.0, "Rua A, 123", "joao@example.com");
        Set<ConstraintViolation<Pagamento>> violations = validator.validate(pagamento);
        assertFalse(violations.isEmpty(), "Devem haver violações para um CVV inválido");
        assertEquals("CVV deve ter 3 ou 4 dígitos", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidAmount() {
        Pagamento pagamento = new Pagamento(1L, "1234567812345678", "João Silva", "12/25", "123", -10.0, "Rua A, 123", "joao@example.com");
        Set<ConstraintViolation<Pagamento>> violations = validator.validate(pagamento);
        assertFalse(violations.isEmpty(), "Devem haver violações para um valor negativo");
        assertEquals("O valor da transação deve ser positivo", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidEmail() {
        Pagamento pagamento = new Pagamento(1L, "1234567812345678", "João Silva", "12/25", "123", 100.0, "Rua A, 123", "email-invalido");
        Set<ConstraintViolation<Pagamento>> violations = validator.validate(pagamento);
        assertFalse(violations.isEmpty(), "Devem haver violações para um email inválido");
        assertEquals("Email do titular deve ser válido", violations.iterator().next().getMessage());
    }
}
