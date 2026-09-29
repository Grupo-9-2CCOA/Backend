package school.sptech.projeto_extensao.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import school.sptech.projeto_extensao.exception.EntidadeNaoEncontradaException;
import school.sptech.projeto_extensao.model.Cliente;
import school.sptech.projeto_extensao.repository.ClienteRepository;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Page<Cliente> listar(Pageable pageable){
        return clienteRepository.findAll(pageable);
    }

    public Page<Cliente> listar(String filtros, Pageable pageable) {
        if (filtros == null || filtros.isBlank()) {
            return clienteRepository.findAllByAtivoTrue(pageable);
        }

        String apenasNumeros = filtros.replaceAll("\\D", "");
        if (!apenasNumeros.isBlank() && apenasNumeros.matches("\\d+")) {
            return clienteRepository.findByAtivoTrueAndTelefoneContaining(apenasNumeros, pageable);
        } else {
            return clienteRepository.findByAtivoTrueAndNomeContainingIgnoreCaseOrAtivoTrueAndTelefoneContaining(filtros, filtros, pageable);
        }
    }

    public Page<Cliente> listarInativos(Pageable pageable) {
        return clienteRepository.findAllByAtivoFalse(pageable);
    }

    public Cliente findById(Integer id){
        return clienteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente %d não encontrado".formatted(id)));
    }

    public Cliente cadastrar(Cliente cliente){
        cliente.setAtivo(true);
        return clienteRepository.save(cliente);
    }

    public Boolean deletar(Integer id) {
        return ativarDesativarCliente(id, false);
    }

    public Boolean reativar(Integer id) {
        return ativarDesativarCliente(id, true);
    }

    public Boolean ativarDesativarCliente(Integer id, Boolean ativado){
        Cliente cliente = findById(id);
        if (ativado && !cliente.getAtivo()){
            cliente.setAtivo(true);
            clienteRepository.save(cliente);
            return true;
        } else if (!ativado && cliente.getAtivo()){
            cliente.setAtivo(false);
            clienteRepository.save(cliente);
            return true;
        }

        return false;
    }

    public Cliente atualizar(Integer id, Cliente cliente) {
        if (!clienteRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Cliente %d não encontrado".formatted(id));
        }

        Cliente clienteAtualizado = clienteRepository.findById(id).get();
        clienteAtualizado.setNome(cliente.getNome());
        clienteAtualizado.setTelefone(cliente.getTelefone());
        clienteAtualizado.setCpf(cliente.getCpf());

        return clienteRepository.save(clienteAtualizado);
    }
}
