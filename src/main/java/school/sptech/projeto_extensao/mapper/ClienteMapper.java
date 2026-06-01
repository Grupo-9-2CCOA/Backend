package school.sptech.projeto_extensao.mapper;

import school.sptech.projeto_extensao.dto.cliente.ClienteRequestDto;
import school.sptech.projeto_extensao.dto.cliente.ClienteResponseDto;
import school.sptech.projeto_extensao.model.Cliente;

import java.util.List;

public class ClienteMapper {
    public static Cliente toEntity(ClienteRequestDto dto){
        if(dto == null){
            return null;
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setTelefone(dto.getTelefone());

        return cliente;
    }

    public static ClienteResponseDto toDto(Cliente model){
        if(model == null){
            return null;
        }

        ClienteResponseDto dto = new ClienteResponseDto(
                model.getId(),
                model.getNome(),
                model.getCpf(),
                model.getTelefone(),
                model.getAtivo()
        );

        return dto;
    }

    public static List<ClienteResponseDto> toDto(List<Cliente> entities){
        return entities.stream()
                .map(ClienteMapper::toDto)
                .toList();
    }
}
