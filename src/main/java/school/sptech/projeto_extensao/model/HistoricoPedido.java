package school.sptech.projeto_extensao.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido idPedido;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente idCliente;

    @ManyToOne
    @JoinColumn(name = "status_entrega")
    private Entrega statusEntrega;

    @ManyToOne
    @JoinColumn(name = "status_pagamento")
    private Pagamento statusPagamento;

    private LocalDateTime dataCriacao;
}
