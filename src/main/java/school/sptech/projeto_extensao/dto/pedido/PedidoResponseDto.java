package school.sptech.projeto_extensao.dto.pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school.sptech.projeto_extensao.model.Cliente;
import school.sptech.projeto_extensao.model.Endereco;
import school.sptech.projeto_extensao.model.Entrega;
import school.sptech.projeto_extensao.model.Pagamento;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PedidoResponseDto {
    private Integer id;
    private String produto;
    private String descricao;
    private Double valor;
    private Boolean isAtivo;
    private LocalDateTime dataPedido;
    private Entrega entrega;
    private Pagamento pagamento;
    private Cliente cliente;
    private Endereco endereco;
    private String linkEvento;

    public PedidoResponseDto(Integer id, String produto, String descricao, Double valor, Boolean isAtivo, LocalDateTime dataPedido, Entrega entrega, Pagamento pagamento, Cliente cliente, Endereco endereco) {
        this.id = id;
        this.produto = produto;
        this.descricao = descricao;
        this.valor = valor;
        this.isAtivo = isAtivo;
        this.dataPedido = dataPedido;
        this.entrega = entrega;
        this.pagamento = pagamento;
        this.cliente = cliente;
        this.endereco = endereco;
    }
}
