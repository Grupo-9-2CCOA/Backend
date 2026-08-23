package school.sptech.projeto_extensao.service;

import org.springframework.stereotype.Service;
import school.sptech.projeto_extensao.dto.EnderecoRequestDto;
import school.sptech.projeto_extensao.dto.EnderecoResponseDto;
import school.sptech.projeto_extensao.exception.EnderecoPedidoNaoCompletoException;
import school.sptech.projeto_extensao.exception.EntidadeNaoEncontradaException;
import school.sptech.projeto_extensao.model.Cliente;
import school.sptech.projeto_extensao.model.Endereco;
import school.sptech.projeto_extensao.model.Pedido;
import school.sptech.projeto_extensao.repository.ClienteRepository;
import school.sptech.projeto_extensao.repository.EnderecoRepository;
import school.sptech.projeto_extensao.repository.PedidoRepository;

import java.util.List;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ClienteRepository clienteRepository;
    private final PedidoRepository pedidoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository, ClienteRepository clienteRepository, PedidoRepository pedidoRepository) {
        this.enderecoRepository = enderecoRepository;
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public List<EnderecoResponseDto> listarPorCliente(Integer idCliente) {
        List<Endereco> enderecos = enderecoRepository.findByClienteId(idCliente);

        return enderecos.stream()
                .map(endereco -> new EnderecoResponseDto(
                        endereco.getId(),
                        endereco.getLogradouro(),
                        endereco.getNumero(),
                        endereco.getComplemento(),
                        endereco.getCep()
                ))
                .toList();
    }

    public EnderecoResponseDto cadastrar(Integer idCliente, EnderecoRequestDto dto) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não encontrado"));

        Endereco endereco = new Endereco();
        endereco.setLogradouro(dto.getLogradouro());
        endereco.setNumero(dto.getNumero());
        endereco.setCep(dto.getCep());
        endereco.setComplemento(dto.getComplemento());
        endereco.setCliente(cliente);

        Endereco salvo = enderecoRepository.save(endereco);

        return new EnderecoResponseDto(
                salvo.getId(),
                salvo.getLogradouro(),
                salvo.getNumero(),
                salvo.getComplemento(),
                salvo.getCep()
        );
    }

    public EnderecoResponseDto atualizar(Integer id, EnderecoRequestDto dto) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Endereço não encontrado"));

        endereco.setLogradouro(dto.getLogradouro());
        endereco.setNumero(dto.getNumero());
        endereco.setCep(dto.getCep());
        endereco.setComplemento(dto.getComplemento());

        Endereco atualizado = enderecoRepository.save(endereco);

        return new EnderecoResponseDto(
                atualizado.getId(),
                atualizado.getLogradouro(),
                atualizado.getNumero(),
                atualizado.getComplemento(),
                atualizado.getCep()
        );
    }

    public void deletar(Integer id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Endereço não encontrado"));

        List<Pedido> pedidos = pedidoRepository.findByEnderecoId(id);
        Pedido pedidoNaoCompleto = pedidos.stream()
                .filter(pedido -> pedido.getIsAtivo() == null || pedido.getIsAtivo())
                .filter(pedido -> pedido.getEntrega() == null || pedido.getEntrega().getEstado() == null || !"Completo".equalsIgnoreCase(pedido.getEntrega().getEstado().trim()))
                .findFirst()
                .orElse(null);

        if (pedidoNaoCompleto != null) {
            throw new EnderecoPedidoNaoCompletoException(
                    "Não foi possível deletar o endereço porque existe um pedido ainda não completo para esse endereço (Pedido Nº " + pedidoNaoCompleto.getId() + ")."
            );
        }

        enderecoRepository.delete(endereco);
    }
}
