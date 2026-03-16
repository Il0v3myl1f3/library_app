package md.utm.libraryapp.service.mapper;

import md.utm.libraryapp.domain.Client;
import md.utm.libraryapp.service.dto.ClientDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Client} and its DTO {@link ClientDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {}
