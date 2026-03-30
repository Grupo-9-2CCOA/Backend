package school.sptech.projeto_extensao.service;

import org.springframework.stereotype.Service;
import school.sptech.projeto_extensao.dto.PedidoRequestDto;
import school.sptech.projeto_extensao.model.Pedido;
import school.sptech.projeto_extensao.repository.PedidoRepository;

@Service
public class PedidoService {
    private final PedidoRepository service;

    public PedidoService(PedidoRepository service) {
        this.service = service;
    }

    public Pedido cadastrar(Pedido pedido){
        return service.save(pedido);
    }

    public Pedido editar(PedidoRequestDto pedido){
//        incompleto
        return null;
    }
}
