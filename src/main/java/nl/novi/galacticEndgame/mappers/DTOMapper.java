package nl.novi.galacticEndgame.mappers;

import java.util.List;

public interface DTOMapper<RESPONSE, REQUEST , T> {
    RESPONSE mapToDto(T model);

    List<RESPONSE> mapToDto(List<T> models);

    T mapToEntity(REQUEST huntModel);
}
