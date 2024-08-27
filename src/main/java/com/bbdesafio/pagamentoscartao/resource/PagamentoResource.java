package com.bbdesafio.pagamentoscartao.resource;

import com.bbdesafio.pagamentoscartao.model.Pagamento;
import com.bbdesafio.pagamentoscartao.service.PagamentoService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

@Path("/pagamentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PagamentoResource {

    @Inject
    PagamentoService pagamentoService;

    @POST
    @Transactional
    @Counted(name = "pagamento_criado_contador", description = "Número de vezes que o endpoint de criação de pagamento foi chamado")
    @Timed(name = "pagamento_criado_tempo", description = "Tempo gasto para processar uma criação de pagamento")
    public Response receberPagamento(Pagamento pagamento) {
        try {
            pagamentoService.processarPagamento(pagamento);
            return Response.status(Response.Status.CREATED)
                    .entity("Pagamento processado com sucesso!")
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @Counted(name = "pagamento_busca_por_id_contador", description = "Número de vezes que o endpoint de busca por ID foi chamado")
    @Timed(name = "pagamento_busca_por_id_tempo", description = "Tempo gasto para buscar um pagamento por ID")
    public Response buscarPagamentoPorId(@PathParam("id") Long id) {
        Pagamento pagamento = pagamentoService.buscarPagamentoPorId(id);
        if (pagamento == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(pagamento).build();
    }

    @GET
    @Counted(name = "pagamento_listar_contador", description = "Número de vezes que o endpoint de listagem de pagamentos foi chamado")
    @Timed(name = "pagamento_listar_tempo", description = "Tempo gasto para listar todos os pagamentos")
    public Response listarPagamentos() {
        return Response.ok(pagamentoService.listarPagamentos()).build();
    }
}
