package school.sptech.projeto_extensao.service;

import org.springframework.stereotype.Service;
import school.sptech.projeto_extensao.exception.EntidadeNaoEncontradaException;
import school.sptech.projeto_extensao.model.Cliente;
import school.sptech.projeto_extensao.repository.ClienteRepository;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }

    public List<Cliente> listar(String q) {
        if (q == null || q.isBlank()) {
            return clienteRepository.findAllByAtivoTrue();
        }

        String digitsOnly = q.replaceAll("\\D", "");
        if (!digitsOnly.isBlank() && digitsOnly.matches("\\d+")) {
            return clienteRepository.findByAtivoTrueAndTelefoneContaining(digitsOnly);
        } else {
            return clienteRepository.findByAtivoTrueAndNomeContainingIgnoreCaseOrAtivoTrueAndTelefoneContaining(q, q);
        }
    }

    public Cliente findById(Integer id){
        return clienteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não encontrado".formatted(id)));
    }

    public Cliente cadastrar(Cliente cliente){
        if (cliente.getAtivo() == null) {
            cliente.setAtivo(true);
        }
        return clienteRepository.save(cliente);
    }

    public Boolean deletar(Integer id) {
        Cliente cliente = findById(id);
        cliente.setAtivo(false);
        clienteRepository.save(cliente);
        return true;
    }

    public Cliente atualizar(Integer id, Cliente cliente) {
        if (!clienteRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Cliente não encontrado".formatted(id));
        }

        Cliente cliente1 = clienteRepository.findById(id).get();
        cliente1.setNome(cliente.getNome());
        cliente1.setTelefone(cliente.getTelefone());
        cliente1.setCpf(cliente.getCpf());
        cliente1.setAtivo(cliente.getAtivo());

        return clienteRepository.save(cliente1);
    }
}
