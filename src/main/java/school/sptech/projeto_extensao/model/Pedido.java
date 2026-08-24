package school.sptech.projeto_extensao.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "Representa um pedido do sistema")
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Pedido {
    @Schema(description = "ID do histórico de pedido", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Produto principal do pedido", example = "Bolo 5kg")
    private String produto;

    @Schema(description = "Descrição dos produtos relacionados ao pedido", example = "1 Bolo de kg decorado da Turma da Mônica; 100 coxinhas")
    private String descricao;

    @Schema(description = "O valor cobrado no pedido", example = "59.99")
    private Double valor;

    @Schema(description = "Booleano que informa se o pedido sofreu deleção lógica", example = "false")
    @Column(name = "is_ativo")
    private Boolean isAtivo;

    @Schema(description = "Booleano que informa se o pedido foi reagendado em algum momento", example = "false")
    @Column(name = "is_reagendado")
    private Boolean isReagendado = false;

    @Schema(description = "Data programada para o pedido ser entregue", example = "2026-10-10")
    @Column(name = "data_pedido")
    private LocalDateTime dataPedido;

    @Schema(description = "Data em que o pedido sofreu modicação", example = "2026-03-05")
    @Column(name = "data_modificacao")
    private LocalDateTime dataModificacao;

    @Schema(description = "Data em que o pedido foi criado", example = "2026-06-08")
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "status_entrega")
    private Entrega entrega;

    @ManyToOne
    @JoinColumn(name = "status_pagamento")
    private Pagamento pagamento;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    private Endereco endereco;

    @Schema(description = "ID de evento gerado na API do Google Calendar", example = "23k4j54k232l423j42k")
    private String eventoGoogleCalendarId;

    public Pedido(Integer id, String produto, String descricao, Double valor, Boolean isAtivo, Boolean isReagendado,
                  LocalDateTime dataPedido, LocalDateTime dataModificacao, Entrega entrega, Pagamento pagamento, Cliente cliente, Endereco endereco, String eventoGoogleCalendarId) {
        this.id = id;
        this.produto = produto;
        this.descricao = descricao;
        this.valor = valor;
        this.isReagendado = isReagendado;
        this.isAtivo = isAtivo;
        this.dataPedido = dataPedido;
        this.dataModificacao = dataModificacao;
        this.entrega = entrega;
        this.pagamento = pagamento;
        this.cliente = cliente;
        this.endereco = endereco;
        this.eventoGoogleCalendarId = eventoGoogleCalendarId;
    }

    public Pedido(String produto, String descricao, Double valor, Boolean isAtivo, LocalDateTime dataPedido, LocalDateTime dataCriacao, Entrega entrega, Pagamento pagamento, Cliente cliente, Endereco endereco, String eventoGoogleCalendarId) {
        this.produto = produto;
        this.descricao = descricao;
        this.valor = valor;
        this.isAtivo = isAtivo;
        this.dataPedido = dataPedido;
        this.dataCriacao = dataCriacao;
        this.entrega = entrega;
        this.pagamento = pagamento;
        this.cliente = cliente;
        this.endereco = endereco;
        this.eventoGoogleCalendarId = eventoGoogleCalendarId;
    }

    public Pedido() {

    }
}
