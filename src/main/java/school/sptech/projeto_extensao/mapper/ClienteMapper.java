package school.sptech.projeto_extensao.mapper;

import org.springframework.data.domain.Page;
import school.sptech.projeto_extensao.dto.cliente.ClienteRequestDto;
import school.sptech.projeto_extensao.dto.cliente.ClienteResponseDto;
import school.sptech.projeto_extensao.model.Cliente;

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

        return new ClienteResponseDto(
                model.getId(),
                model.getNome(),
                model.getCpf(),
                model.getTelefone(),
                model.getIsAtivo() != null ? model.getIsAtivo() : true
        );
    }

    public static Page<ClienteResponseDto> toDto(Page<Cliente> entities){
        if (entities == null){
            return null;
        }

        return entities.map(ClienteMapper::toDto);
    }
}