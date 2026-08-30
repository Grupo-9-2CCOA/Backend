package school.sptech.projeto_extensao.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.projeto_extensao.repository.PedidoRepository;

import java.util.List;

@Service
public class PedidoConsultaService {

    private final PedidoRepository pedidoRepository;

    public PedidoConsultaService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<Integer> buscarPedidosIncompletosPorEndereco(Integer enderecoId) {
        return pedidoRepository.findIdsByEnderecoIdWithEntregaNot(enderecoId, 3);
    }
}
