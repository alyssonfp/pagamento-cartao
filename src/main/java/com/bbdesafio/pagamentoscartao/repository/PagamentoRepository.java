package com.bbdesafio.pagamentoscartao.repository;

import com.bbdesafio.pagamentoscartao.model.Pagamento;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class PagamentoRepository implements PanacheRepositoryBase<Pagamento, Long> {
}
