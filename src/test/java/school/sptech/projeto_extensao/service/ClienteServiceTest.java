package school.sptech.projeto_extensao.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.projeto_extensao.exception.EntidadeNaoEncontradaException;
import school.sptech.projeto_extensao.model.Cliente;
import school.sptech.projeto_extensao.repository.ClienteRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService service;

    @Test
    @DisplayName("Retorna lista vazia quando não há clientes")
    void testaListarRetornaVazia() {
        Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Cliente> lista = service.listar();

        Assertions.assertTrue(lista.isEmpty());
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Retorna lista com clientes quando há registros")
    void testaListarRetornaDados() {
        List<Cliente> listaMock = new ArrayList<>();
        listaMock.add(new Cliente(1, "Sabrina", "29458394801", "11994827483", true));

        Mockito.when(repository.findAll()).thenReturn(listaMock);

        List<Cliente> lista = service.listar();

        Assertions.assertFalse(lista.isEmpty());
        Assertions.assertEquals(1, lista.size());
        Assertions.assertEquals("Sabrina", lista.get(0).getNome());
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Retorna todos os clientes corretamente")
    void testaListarRetornaTodosOsClientes() {
        List<Cliente> listaMock = new ArrayList<>();
        listaMock.add(new Cliente(1, "Sabrina", "29458394801", "11994827483", true));
        listaMock.add(new Cliente(2, "Carlos",  "12345678900", "11987654321", true));
        listaMock.add(new Cliente(3, "Mariana", "98765432100", "11911223344", true));

        Mockito.when(repository.findAll()).thenReturn(listaMock);

        List<Cliente> lista = service.listar();

        Assertions.assertEquals(3, lista.size());
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Retorna cliente quando ID existe")
    void testaFindByIdRetornaClienteExistente() {
        Cliente clienteMock = new Cliente(1, "Sabrina", "29458394801", "11994827483", true);
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(clienteMock));

        Cliente resultado = service.findById(1);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(1, resultado.getId());
        Assertions.assertEquals("Sabrina", resultado.getNome());
        Mockito.verify(repository, Mockito.times(1)).findById(1);
    }

    @Test
    @DisplayName("Lança exceção quando ID não existe")
    void testaFindByIdLancaExcecaoQuandoNaoEncontrado() {
        Mockito.when(repository.findById(99)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> service.findById(99)
        );

        Mockito.verify(repository, Mockito.times(1)).findById(99);
    }

    @Test
    @DisplayName("Salva e retorna o cliente cadastrado")
    void testaCadastrarClienteComSucesso() {
        Cliente clienteEntrada = new Cliente(null, "João", "11122233344", "11999998888", true);
        Cliente clienteSalvo   = new Cliente(1,    "João", "11122233344", "11999998888", true);

        Mockito.when(repository.save(clienteEntrada)).thenReturn(clienteSalvo);

        Cliente resultado = service.cadastrar(clienteEntrada);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(1, resultado.getId());
        Assertions.assertEquals("João", resultado.getNome());
        Mockito.verify(repository, Mockito.times(1)).save(clienteEntrada);
    }

    @Test
    @DisplayName("Persiste os dados do cliente corretamente")
    void testaCadastrarClientePersisteDados() {
        Cliente clienteEntrada = new Cliente(null, "Ana", "55566677788", "11933334444", true);
        Cliente clienteSalvo   = new Cliente(2,    "Ana", "55566677788", "11933334444", true);

        Mockito.when(repository.save(clienteEntrada)).thenReturn(clienteSalvo);

        Cliente resultado = service.cadastrar(clienteEntrada);

        Assertions.assertEquals("Ana",         resultado.getNome());
        Assertions.assertEquals("55566677788", resultado.getCpf());
        Assertions.assertEquals("11933334444", resultado.getTelefone());
        Assertions.assertTrue(resultado.getAtivo());
    }

    @Test
    @DisplayName("Inativa cliente existente e retorna true")
    void testaDeletarClienteExistenteRetornaTrue() {
        Cliente clienteMock = new Cliente(1, "Sabrina", "29458394801", "11994827483", true);

        Mockito.when(repository.findById(1)).thenReturn(Optional.of(clienteMock));

        Boolean resultado = service.deletar(1);

        Assertions.assertTrue(resultado);
        Assertions.assertFalse(clienteMock.getAtivo());
    }

    @Test
    @DisplayName("Lança exceção quando cliente não encontrado ao deletar")
    void testaDeletarClienteInexistenteLancaExcecao() {
        Mockito.when(repository.findById(99)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> service.deletar(99)
        );

        Mockito.verify(repository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @DisplayName("Lista somente clientes inativos")
    void testaListarClientesInativos() {
        List<Cliente> inativos = List.of(new Cliente(1, "Sabrina", "29458394801", "11994827483", false));
        Mockito.when(repository.findAllByAtivoFalse()).thenReturn(inativos);

        List<Cliente> resultado = service.listarInativos();

        Assertions.assertEquals(inativos, resultado);
        Mockito.verify(repository).findAllByAtivoFalse();
    }

    @Test
    @DisplayName("Reativa cliente existente")
    void testaReativarClienteExistente() {
        Cliente cliente = new Cliente(1, "Sabrina", "29458394801", "11994827483", false);
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(cliente));

        Boolean resultado = service.reativar(1);

        Assertions.assertTrue(resultado);
        Assertions.assertTrue(cliente.getAtivo());
        Mockito.verify(repository).save(cliente);
    }

    @Test
    @DisplayName("Lança exceção ao reativar cliente inexistente")
    void testaReativarClienteInexistente() {
        Mockito.when(repository.findById(99)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntidadeNaoEncontradaException.class, () -> service.reativar(99));
        Mockito.verify(repository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @DisplayName("Atualiza e retorna cliente existente")
    void testaAtualizarClienteComSucesso() {
        Cliente clienteExistente  = new Cliente(1, "Sabrina", "29458394801", "11994827483", true);
        Cliente clienteAtualizado = new Cliente(1, "Sabrina Silva", "29458394801", "11900001111", true);

        Mockito.when(repository.existsById(1)).thenReturn(true);
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(clienteExistente));
        Mockito.when(repository.save(clienteExistente)).thenReturn(clienteAtualizado);

        Cliente resultado = service.atualizar(1, clienteAtualizado);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Sabrina Silva", resultado.getNome());
        Assertions.assertEquals("11900001111",   resultado.getTelefone());
        Mockito.verify(repository, Mockito.times(1)).save(clienteExistente);
    }

    @Test
    @DisplayName("Lança exceção quando cliente não existe ao atualizar")
    void testaAtualizarClienteInexistenteLancaExcecao() {
        Cliente clienteAtualizado = new Cliente(99, "Fantasma", "00000000000", "11900000000", true);

        Mockito.when(repository.existsById(99)).thenReturn(false);

        Assertions.assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> service.atualizar(99, clienteAtualizado)
        );

        Mockito.verify(repository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @DisplayName("Persiste dados cadastrais e mantém cliente ativo")
    void testaAtualizarPersisteTodosOsCampos() {
        Cliente clienteExistente = new Cliente(1, "Nome Antigo", "11111111111", "11900000000", true);
        Cliente dadosNovos       = new Cliente(1, "Nome Novo",   "22222222222", "11911111111", false);
        Cliente clienteSalvo     = new Cliente(1, "Nome Novo",   "22222222222", "11911111111", true);

        Mockito.when(repository.existsById(1)).thenReturn(true);
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(clienteExistente));
        Mockito.when(repository.save(clienteExistente)).thenReturn(clienteSalvo);

        Cliente resultado = service.atualizar(1, dadosNovos);

        Assertions.assertEquals("Nome Novo",   resultado.getNome());
        Assertions.assertEquals("22222222222", resultado.getCpf());
        Assertions.assertEquals("11911111111", resultado.getTelefone());
        Assertions.assertTrue(resultado.getAtivo());
    }

    @Test
    @DisplayName("Preserva cliente inativo durante atualização de dados")
    void testaAtualizarPreservaClienteInativo() {
        Cliente clienteExistente = new Cliente(1, "Nome Antigo", "11111111111", "11900000000", false);
        Cliente dadosNovos = new Cliente(1, "Nome Novo", "22222222222", "11911111111", true);

        Mockito.when(repository.existsById(1)).thenReturn(true);
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(clienteExistente));
        Mockito.when(repository.save(clienteExistente)).thenReturn(clienteExistente);

        Cliente resultado = service.atualizar(1, dadosNovos);

        Assertions.assertFalse(resultado.getAtivo());
        Mockito.verify(repository).save(clienteExistente);
    }

    @Test
    @DisplayName("Busca por telefone quando q contém apenas dígitos")
    void testaListarPorTelefoneQuandoSomenteDigitos() {
        List<Cliente> listaMock = new ArrayList<>();
        listaMock.add(new Cliente(1, "Sabrina", "29458394801", "11994827483", true));

        Mockito.when(repository.findByAtivoTrueAndTelefoneContaining("11994827483")).thenReturn(listaMock);

        List<Cliente> lista = service.listar("11994827483");

        Assertions.assertEquals(1, lista.size());
        Mockito.verify(repository, Mockito.times(1)).findByAtivoTrueAndTelefoneContaining("11994827483");
    }

    @Test
    @DisplayName("Busca por nome ou telefone quando q contém texto")
    void testaListarPorNomeOuTelefoneQuandoTexto() {
        List<Cliente> listaMock = new ArrayList<>();
        listaMock.add(new Cliente(2, "Carlos", "12345678900", "11987654321", true));

        Mockito.when(repository.findByAtivoTrueAndNomeContainingIgnoreCaseOrAtivoTrueAndTelefoneContaining("Carlos", "Carlos")).thenReturn(listaMock);

        List<Cliente> lista = service.listar("Carlos");

        Assertions.assertEquals(1, lista.size());
        Mockito.verify(repository, Mockito.times(1)).findByAtivoTrueAndNomeContainingIgnoreCaseOrAtivoTrueAndTelefoneContaining("Carlos", "Carlos");
    }
}
