package school.sptech.projeto_extensao.dto.pedido;

import lombok.Getter;
import lombok.Setter;
import school.sptech.projeto_extensao.model.Cliente;
import school.sptech.projeto_extensao.model.Endereco;
import school.sptech.projeto_extensao.model.Entrega;
import school.sptech.projeto_extensao.model.Pagamento;

import java.time.LocalDateTime;

@Getter
@Setter
public class PedidoRequestDto {
    private String produto;
    private String descricao;
    private Double valor;
    private Boolean isAtivo;
    private Boolean isReagendado;
    private LocalDateTime dataPedido;
    private Entrega entrega;
    private Pagamento pagamento;
    private Cliente cliente;
    private Endereco endereco;

    public PedidoRequestDto() {
    }

    public PedidoRequestDto(String produto, String descricao, Double valor,
                            LocalDateTime dataPedido, Entrega entrega, Pagamento pagamento, Cliente cliente, Endereco endereco) {
        this.produto = produto;
        this.descricao = descricao;
        this.valor = valor;
        this.isAtivo = true;
        this.isReagendado = false;
        this.dataPedido = dataPedido;
        this.entrega = entrega;
        this.pagamento = pagamento;
        this.cliente = cliente;
        this.endereco = endereco;
    }
}
