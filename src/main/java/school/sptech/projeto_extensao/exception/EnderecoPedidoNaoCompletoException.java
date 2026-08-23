package school.sptech.projeto_extensao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EnderecoPedidoNaoCompletoException extends RuntimeException {
    public EnderecoPedidoNaoCompletoException(String message) {
        super(message);
    }
}
