package school.sptech.projeto_extensao.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            erros.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Map<String, String>> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensagem", ex.getMessage()));
    }

    @ExceptionHandler(EnderecoPedidoNaoCompletoException.class)
    public ResponseEntity<Map<String, Object>> handleEnderecoPedidoNaoCompleto(EnderecoPedidoNaoCompletoException ex) {
        Map<String, Object> erro = new HashMap<>();
        erro.put("codigo", "ENDERECO_PEDIDO_NAO_COMPLETO");
        erro.put("mensagem", ex.getMessage());
        erro.put("pedidoIds", ex.getPedidoIds());
        erro.put("nivel", "CONFLICT");

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("erro", erro));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, Object> erro = new HashMap<>();
        erro.put("codigo", "ENDERECO_COM_PEDIDO_VINCULADO");
        erro.put("mensagem", "Não foi possível deletar o endereço porque existe um pedido associado a ele. Primeiro finalize ou remova o vínculo do pedido.");
        erro.put("pedidoIds", List.of());
        erro.put("nivel", "CONFLICT");

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("erro", erro));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("mensagem", "Ocorreu um erro interno inesperado. " + ex.getMessage()));
    }
}
