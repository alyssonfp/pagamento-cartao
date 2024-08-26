package com.bbdesafio.pagamentoscartao.service.Impl;

import com.bbdesafio.pagamentoscartao.model.Pagamento;
import com.bbdesafio.pagamentoscartao.repository.PagamentoRepository;
import com.bbdesafio.pagamentoscartao.service.PagamentoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ApplicationScoped
public class PagamentoServiceImpl implements PagamentoService {

    @Inject
    PagamentoRepository pagamentoRepository;

    @Override
    @Transactional
    public void processarPagamento(Pagamento pagamento) {
        validarDadosPagamento(pagamento);
        pagamentoRepository.persist(pagamento);
    }

    @Override
    public Pagamento buscarPagamentoPorId(Long id) {
        return pagamentoRepository.findById(id);
    }

    @Override
    public List<Pagamento> listarPagamentos() {
        return pagamentoRepository.listAll();
    }

    private void validarDadosPagamento(Pagamento pagamento) {
        validarNumeroCartao(pagamento.getCardNumber());
        validarNomeTitular(pagamento.getCardHolderName());
        validarDataValidadeCartao(pagamento.getExpiryDate());
        validarCVV(pagamento.getCvv());
        validarValor(pagamento.getAmount());
    }

    private void validarNumeroCartao(String numeroCartao) {
        if (numeroCartao == null || !numeroCartao.matches("\\d{16}")) {
            throw new IllegalArgumentException("Número do cartão inválido. Deve conter 16 dígitos.");
        }
    }

    private void validarNomeTitular(String nomeTitular) {
        if (nomeTitular == null || nomeTitular.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do titular não pode ser nulo ou vazio.");
        }
    }

    private void validarDataValidadeCartao(String expiryDate) {
        if (expiryDate == null) {
            throw new IllegalArgumentException("Data de validade do cartão não pode ser nula.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        YearMonth validade = YearMonth.parse(expiryDate, formatter);

        if (validade.isBefore(YearMonth.now())) {
            throw new IllegalArgumentException("Data de validade do cartão não pode ser no passado.");
        }
    }

    private void validarCVV(String cvv) {
        if (cvv == null || !cvv.matches("\\d{3,4}")) {
            throw new IllegalArgumentException("CVV inválido. Deve conter 3 ou 4 dígitos.");
        }
    }

    private void validarValor(Double valor) {
        if (valor == null || valor <= 0) {
            throw new IllegalArgumentException("Valor do pagamento deve ser positivo.");
        }
    }
}
