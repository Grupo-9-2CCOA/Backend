package school.sptech.projeto_extensao.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.projeto_extensao.dto.EnderecoRequestDto;
import school.sptech.projeto_extensao.dto.EnderecoResponseDto;
import school.sptech.projeto_extensao.exception.EnderecoPedidoNaoCompletoException;
import school.sptech.projeto_extensao.exception.EntidadeNaoEncontradaException;
import school.sptech.projeto_extensao.model.Cliente;
import school.sptech.projeto_extensao.model.Endereco;
import school.sptech.projeto_extensao.model.Entrega;
import school.sptech.projeto_extensao.model.Pedido;
import school.sptech.projeto_extensao.repository.ClienteRepository;
import school.sptech.projeto_extensao.repository.EnderecoRepository;
import school.sptech.projeto_extensao.repository.PedidoRepository;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private EnderecoService service;

    @Test
    @DisplayName("Retorna lista de endereços por cliente")
    void deveListarEnderecosPorCliente() {
        Cliente cliente = new Cliente(1, "Ana", "11122233344", "11999990001", true);
        Endereco endereco = new Endereco();
        endereco.setId(10);
        endereco.setLogradouro("Rua das Flores");
        endereco.setNumero("123");
        endereco.setComplemento("Apto 10");
        endereco.setCep("01001000");
        endereco.setCliente(cliente);

        Mockito.when(enderecoRepository.findByClienteId(1)).thenReturn(List.of(endereco));

        List<EnderecoResponseDto> resultado = service.listarPorCliente(1);

        Assertions.assertEquals(1, resultado.size());
        Assertions.assertEquals(10, resultado.get(0).getId());
        Assertions.assertEquals("Rua das Flores", resultado.get(0).getLogradouro());
        Assertions.assertEquals("123", resultado.get(0).getNumero());
        Assertions.assertEquals("Apto 10", resultado.get(0).getComplemento());
        Assertions.assertEquals("01001000", resultado.get(0).getCep());
    }

    @Test
    @DisplayName("Retorna lista vazia quando não há endereços para o cliente")
    void deveRetornarListaVaziaQuandoNaoExistiremEnderecos() {
        Mockito.when(enderecoRepository.findByClienteId(99)).thenReturn(List.of());

        List<EnderecoResponseDto> resultado = service.listarPorCliente(99);

        Assertions.assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Cadastra endereço com sucesso")
    void deveCadastrarEnderecoComSucesso() {
        Cliente cliente = new Cliente(1, "Ana", "11122233344", "11999990001", true);
        EnderecoRequestDto dto = new EnderecoRequestDto();
        dto.setLogradouro("Avenida Paulista");
        dto.setNumero("75");
        dto.setCep("01311000");
        dto.setComplemento("Bloco A");
        dto.setIdCliente(1L);

        Endereco enderecoSalvo = new Endereco();
        enderecoSalvo.setId(5);
        enderecoSalvo.setLogradouro("Avenida Paulista");
        enderecoSalvo.setNumero("75");
        enderecoSalvo.setCep("01311000");
        enderecoSalvo.setComplemento("Bloco A");
        enderecoSalvo.setCliente(cliente);

        Mockito.when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        Mockito.when(enderecoRepository.save(Mockito.any(Endereco.class))).thenReturn(enderecoSalvo);

        EnderecoResponseDto resultado = service.cadastrar(1, dto);

        Assertions.assertEquals(5, resultado.getId());
        Assertions.assertEquals("Avenida Paulista", resultado.getLogradouro());
        Assertions.assertEquals("75", resultado.getNumero());
        Assertions.assertEquals("Bloco A", resultado.getComplemento());
        Assertions.assertEquals("01311000", resultado.getCep());
    }

    @Test
    @DisplayName("Lança erro ao cadastrar endereço para cliente inexistente")
    void deveLancarExcecaoQuandoClienteNaoExisteAoCadastrar() {
        EnderecoRequestDto dto = new EnderecoRequestDto();
        dto.setLogradouro("Avenida Paulista");
        dto.setNumero("75");
        dto.setCep("01311000");
        dto.setComplemento("Bloco A");

        Mockito.when(clienteRepository.findById(99)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> service.cadastrar(99, dto)
        );
    }

    @Test
    @DisplayName("Atualiza endereço com sucesso")
    void deveAtualizarEnderecoComSucesso() {
        Endereco enderecoExistente = new Endereco();
        enderecoExistente.setId(7);
        enderecoExistente.setLogradouro("Rua Antiga");
        enderecoExistente.setNumero("10");
        enderecoExistente.setCep("01001000");
        enderecoExistente.setComplemento("Casa");

        EnderecoRequestDto dto = new EnderecoRequestDto();
        dto.setLogradouro("Rua Nova");
        dto.setNumero("20");
        dto.setCep("02002000");
        dto.setComplemento("Apartamento 2");

        Mockito.when(enderecoRepository.findById(7)).thenReturn(Optional.of(enderecoExistente));
        Mockito.when(enderecoRepository.save(Mockito.any(Endereco.class))).thenAnswer(invocation -> invocation.getArgument(0));

        EnderecoResponseDto resultado = service.atualizar(7, dto);

        Assertions.assertEquals(7, resultado.getId());
        Assertions.assertEquals("Rua Nova", resultado.getLogradouro());
        Assertions.assertEquals("20", resultado.getNumero());
        Assertions.assertEquals("Apartamento 2", resultado.getComplemento());
        Assertions.assertEquals("02002000", resultado.getCep());
    }

    @Test
    @DisplayName("Lança erro ao atualizar endereço inexistente")
    void deveLancarExcecaoQuandoEnderecoNaoExisteAoAtualizar() {
        EnderecoRequestDto dto = new EnderecoRequestDto();
        dto.setLogradouro("Rua Nova");
        dto.setNumero("20");
        dto.setCep("02002000");
        dto.setComplemento("Apartamento 2");

        Mockito.when(enderecoRepository.findById(999)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> service.atualizar(999, dto)
        );
    }

    @Test
    @DisplayName("Deleta endereço com sucesso quando não há pedido incompleto")
    void deveDeletarEnderecoComSucesso() {
        Endereco endereco = new Endereco();
        endereco.setId(11);
        endereco.setLogradouro("Rua das Flores");

        Pedido pedidoCompleto = new Pedido();
        pedidoCompleto.setId(21);
        pedidoCompleto.setIsAtivo(true);
        Entrega entrega = new Entrega();
        entrega.setEstado("Completo");
        pedidoCompleto.setEntrega(entrega);

        Mockito.when(enderecoRepository.findById(11)).thenReturn(Optional.of(endereco));
        Mockito.when(pedidoRepository.findByEnderecoId(11)).thenReturn(List.of(pedidoCompleto));

        Assertions.assertDoesNotThrow(() -> service.deletar(11));
        Mockito.verify(enderecoRepository, Mockito.times(1)).delete(endereco);
    }

    @Test
    @DisplayName("Lança erro ao tentar deletar endereço com pedido ainda não completo")
    void deveLancarExcecaoQuandoEnderecoPossuiPedidoNaoCompleto() {
        Endereco endereco = new Endereco();
        endereco.setId(12);
        endereco.setLogradouro("Rua das Flores");

        Pedido pedidoEmAberto = new Pedido();
        pedidoEmAberto.setId(55);
        pedidoEmAberto.setIsAtivo(true);
        Entrega entrega = new Entrega();
        entrega.setEstado("Pendente");
        pedidoEmAberto.setEntrega(entrega);

        Mockito.when(enderecoRepository.findById(12)).thenReturn(Optional.of(endereco));
        Mockito.when(pedidoRepository.findByEnderecoId(12)).thenReturn(List.of(pedidoEmAberto));

        EnderecoPedidoNaoCompletoException excecao = Assertions.assertThrows(
                EnderecoPedidoNaoCompletoException.class,
                () -> service.deletar(12)
        );

        Assertions.assertTrue(excecao.getMessage().contains("Pedido Nº 55"));
        Mockito.verify(enderecoRepository, Mockito.never()).delete(Mockito.any());
    }

    @Test
    @DisplayName("Lança erro ao tentar deletar endereço inexistente")
    void deveLancarExcecaoQuandoEnderecoNaoExisteAoDeletar() {
        Mockito.when(enderecoRepository.findById(404)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> service.deletar(404)
        );
    }
}
