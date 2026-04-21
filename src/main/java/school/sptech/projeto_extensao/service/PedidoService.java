package school.sptech.projeto_extensao.service;

import org.springframework.scheduling.config.Task;
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
        return service.findAllByIsAtivoTrue();
    }

    public Pedido encontrarPorId(Integer id){
        return service.findByIdAndIsAtivoTrue(id);
    }

    public List<Pedido> listarPorData(LocalDateTime dataInicio, LocalDateTime dataFim){
        return service.findAllByIsAtivoTrueAndDataPedidoBetween(dataInicio, dataFim);
    }

    public Pedido cadastrar(Pedido pedido){
        return service.save(pedido);
    }

    public Pedido editar(Pedido pedido){
        pedido.setDataModificacao(LocalDateTime.now());
        historico.save(PedidoMapper.toHistorico(pedido));
        return service.save(pedido);
    }

    public Integer deletar(Integer id){
        if (encontrarPorId(id) != null){
            return service.desativarPedido(id);
        } else {
            return 0;
        }
    }
}
