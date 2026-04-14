package school.sptech.projeto_extensao.service;

import org.springframework.stereotype.Service;
import school.sptech.projeto_extensao.Exception.ErroException;
import school.sptech.projeto_extensao.dto.PedidoMapper;
import school.sptech.projeto_extensao.model.Pedido;
import school.sptech.projeto_extensao.repository.HistoricoPedidoRepository;
import school.sptech.projeto_extensao.repository.PedidoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository service;

    private final HistoricoPedidoRepository historico;

    public PedidoService(PedidoRepository service, HistoricoPedidoRepository historico) {
        this.service = service;
        this.historico = historico;
    }

    public List<Pedido> listar(){
        return service.findAll();
    }

    public Pedido encontrarPorId(Integer id){
        return service.findById(id).orElseThrow(() -> new ErroException("Pedido não encontrado"));
    }

    public List<Pedido> listarPorData(LocalDateTime dataInicio, LocalDateTime dataFim){
        return service.findAllByDataPedidoBetween(dataInicio, dataFim);
    }

    public Pedido cadastrar(Pedido pedido){
        return service.save(pedido);
    }

    public Pedido editar(Pedido pedido){
        historico.save(PedidoMapper.toHistorico(pedido));
        return service.save(pedido);
    }

    public Integer deletar(Integer id){
        return service.desativarPedido(id);
    }
}
