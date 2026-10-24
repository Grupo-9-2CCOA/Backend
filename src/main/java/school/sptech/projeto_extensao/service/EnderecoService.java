package school.sptech.projeto_extensao.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.projeto_extensao.dto.EnderecoRequestDto;
import school.sptech.projeto_extensao.dto.EnderecoResponseDto;
import school.sptech.projeto_extensao.exception.EnderecoPedidoNaoCompletoException;
import school.sptech.projeto_extensao.exception.EntidadeNaoEncontradaException;
import school.sptech.projeto_extensao.model.Cliente;
import school.sptech.projeto_extensao.model.Endereco;
import school.sptech.projeto_extensao.repository.ClienteRepository;
import school.sptech.projeto_extensao.repository.EnderecoRepository;

import java.util.List;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ClienteRepository clienteRepository;
    private final PedidoConsultaService pedidoConsultaService;

    public EnderecoService(EnderecoRepository enderecoRepository,
                          ClienteRepository clienteRepository,
                          PedidoConsultaService pedidoConsultaService) {
        this.enderecoRepository = enderecoRepository;
        this.clienteRepository = clienteRepository;
        this.pedidoConsultaService = pedidoConsultaService;
    }

    public Page<EnderecoResponseDto> listarPorCliente(Integer idCliente, Pageable pageable) {
        if (!clienteRepository.existsById(idCliente)) {
            throw new EntidadeNaoEncontradaException("Cliente %d não encontrado".formatted(idCliente));
        }

        Page<Endereco> enderecos = enderecoRepository.findByClienteIdAndIsAtivoTrue(idCliente, pageable);

        List<EnderecoResponseDto> enderecosResponse = enderecos.stream()
                .map(endereco -> new EnderecoResponseDto(
                        endereco.getId(),
                        endereco.getLogradouro(),
                        endereco.getNumero(),
                        endereco.getComplemento(),
                        endereco.getCep()
                ))
                .toList();

        return new PageImpl<>(enderecosResponse, pageable, enderecos.getTotalElements());
    }

    public EnderecoResponseDto cadastrar(Integer idCliente, EnderecoRequestDto dto) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não %d encontrado".formatted(idCliente)));

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

    @Transactional
    public void deletar(Integer id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Endereço não encontrado"));

        if (Boolean.FALSE.equals(endereco.getIsAtivo())) {
            return;
        }

        List<Integer> pedidoIdsIncompletos = pedidoConsultaService.buscarPedidosIncompletosPorEndereco(id);

        if (pedidoIdsIncompletos != null && !pedidoIdsIncompletos.isEmpty()) {
            throw new EnderecoPedidoNaoCompletoException(
                    pedidoIdsIncompletos,
                    "Não foi possível deletar o endereço porque existem pedidos ainda não completos para esse endereço. Pedidos: %s.".formatted(pedidoIdsIncompletos)
            );
        }

        endereco.setIsAtivo(false);
        enderecoRepository.save(endereco);
        enderecoRepository.flush();
    }
}
