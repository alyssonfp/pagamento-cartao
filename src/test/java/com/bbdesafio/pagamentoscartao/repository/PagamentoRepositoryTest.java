package com.bbdesafio.pagamentoscartao.repository;

import com.bbdesafio.pagamentoscartao.model.Pagamento;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class PagamentoRepositoryTest {

    @Inject
    PagamentoRepository pagamentoRepository;

    @Test
    @Transactional
    public void testPersistAndFindById() {
        Pagamento pagamento = new Pagamento();
        pagamento.setCardNumber("1234567812345678");
        pagamento.setCardHolderName("João Silva");
        pagamento.setExpiryDate("12/25");
        pagamento.setCvv("123");
        pagamento.setAmount(100.0);
        pagamento.setBillingAddress("Rua A, 123");
        pagamento.setCardHolderEmail("joao@example.com");

        pagamentoRepository.persist(pagamento);

        assertNotNull(pagamento.getId(), "O ID do pagamento não deve ser nulo após a persistência");

        Pagamento pagamentoEncontrado = pagamentoRepository.findById(pagamento.getId());
        assertNotNull(pagamentoEncontrado, "Pagamento deve ser encontrado pelo ID");
        assertEquals(pagamento.getCardNumber(), pagamentoEncontrado.getCardNumber(), "O número do cartão deve ser igual");
    }

    @Test
    @Transactional
    public void testListAll() {
        Pagamento pagamento1 = new Pagamento(null, "1234567812345678", "João Silva", "12/25", "123", 100.0, "Rua A, 123", "joao@example.com");
        Pagamento pagamento2 = new Pagamento(null, "8765432187654321", "Maria Silva", "11/24", "456", 200.0, "Rua B, 456", "maria@example.com");
        pagamentoRepository.persist(pagamento1);
        pagamentoRepository.persist(pagamento2);

        List<Pagamento> pagamentos = pagamentoRepository.listAll();
        assertFalse(pagamentos.isEmpty(), "A lista de pagamentos não deve estar vazia");
        assertTrue(pagamentos.contains(pagamento1), "A lista de pagamentos deve conter o pagamento1");
        assertTrue(pagamentos.contains(pagamento2), "A lista de pagamentos deve conter o pagamento2");
    }

    @Test
    @Transactional
    public void testDelete() {
        Pagamento pagamento = new Pagamento(null, "1234567812345678", "João Silva", "12/25", "123", 100.0, "Rua A, 123", "joao@example.com");
        pagamentoRepository.persist(pagamento);

        assertNotNull(pagamento.getId(), "O ID do pagamento não deve ser nulo após a persistência");

        pagamentoRepository.deleteById(pagamento.getId());

        Pagamento pagamentoExcluido = pagamentoRepository.findById(pagamento.getId());
        assertNull(pagamentoExcluido, "Pagamento não deve ser encontrado após a exclusão");
    }
}
