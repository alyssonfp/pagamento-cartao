package com.bbdesafio.pagamentoscartao.service;

import com.bbdesafio.pagamentoscartao.model.Pagamento;
import com.bbdesafio.pagamentoscartao.repository.PagamentoRepository;
import com.bbdesafio.pagamentoscartao.service.Impl.PagamentoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PagamentoServiceImplTest {

    @Mock
    PagamentoRepository pagamentoRepository;

    @InjectMocks
    PagamentoServiceImpl pagamentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Transactional
    void testProcessarPagamento() {
        Pagamento pagamento = new Pagamento(null, "1234567812345678", "João Silva", "12/25", "123", 100.0, "Rua A, 123", "joao@example.com");

        doNothing().when(pagamentoRepository).persist(any(Pagamento.class));

        pagamentoService.processarPagamento(pagamento);

        verify(pagamentoRepository, times(1)).persist(any(Pagamento.class));
    }

    @Test
    void testBuscarPagamentoPorId() {
        Pagamento pagamento = new Pagamento(1L, "1234567812345678", "João Silva", "12/25", "123", 100.0, "Rua A, 123", "joao@example.com");

        when(pagamentoRepository.findById(1L)).thenReturn(pagamento);

        Pagamento result = pagamentoService.buscarPagamentoPorId(1L);

        assertNotNull(result);
        assertEquals(pagamento.getCardNumber(), result.getCardNumber());
    }

    @Test
    void testListarPagamentos() {
        Pagamento pagamento1 = new Pagamento(1L, "1234567812345678", "João Silva", "12/25", "123", 100.0, "Rua A, 123", "joao@example.com");
        Pagamento pagamento2 = new Pagamento(2L, "8765432187654321", "Maria Silva", "11/24", "456", 200.0, "Rua B, 456", "maria@example.com");

        when(pagamentoRepository.listAll()).thenReturn(Arrays.asList(pagamento1, pagamento2));

        List<Pagamento> result = pagamentoService.listarPagamentos();

        assertEquals(2, result.size());
        assertEquals("1234567812345678", result.get(0).getCardNumber());
        assertEquals("8765432187654321", result.get(1).getCardNumber());
    }

    @Test
    void testValidarDadosPagamento() {
        Pagamento pagamentoValido = new Pagamento(null, "1234567812345678", "João Silva", "12/25", "123", 100.0, "Rua A, 123", "joao@example.com");

        assertDoesNotThrow(() -> pagamentoService.processarPagamento(pagamentoValido));

        Pagamento pagamentoInvalido = new Pagamento(null, "1234", "João Silva", "12/25", "123", 100.0, "Rua A, 123", "joao@example.com");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            pagamentoService.processarPagamento(pagamentoInvalido);
        });

        assertEquals("Número do cartão inválido. Deve conter 16 dígitos.", exception.getMessage());
    }
}
