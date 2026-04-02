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

    public Cliente findById(Integer id){
        return clienteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não encontrado".formatted(id)));
    }

    public Cliente cadastrar(Cliente cliente){
        return clienteRepository.save(cliente);
    }
}
