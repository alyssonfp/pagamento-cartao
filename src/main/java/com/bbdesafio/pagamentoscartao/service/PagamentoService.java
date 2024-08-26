package com.bbdesafio.pagamentoscartao.service;

import com.bbdesafio.pagamentoscartao.model.Pagamento;

import java.util.List;

public interface PagamentoService {

    void processarPagamento(Pagamento pagamento);

    Pagamento buscarPagamentoPorId(Long id);

    List<Pagamento> listarPagamentos();
}
