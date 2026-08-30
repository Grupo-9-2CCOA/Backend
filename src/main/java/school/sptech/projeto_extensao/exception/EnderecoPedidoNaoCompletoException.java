package school.sptech.projeto_extensao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.CONFLICT)
public class EnderecoPedidoNaoCompletoException extends RuntimeException {
    private final List<Integer> pedidoIds;

    public EnderecoPedidoNaoCompletoException(List<Integer> pedidoIds, String message) {
        super(message);
        this.pedidoIds = pedidoIds == null ? List.of() : List.copyOf(pedidoIds);
    }

    public List<Integer> getPedidoIds() {
        return pedidoIds;
    }
}
