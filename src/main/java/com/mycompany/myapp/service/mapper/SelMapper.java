package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.SelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sel} and its DTO {@link SelDTO}.
 */
@Mapper(componentModel = "spring", uses = {DistinctEventMapper.class})
public interface SelMapper extends EntityMapper<SelDTO, Sel> {


    @Mapping(target = "removeDistinctEvent", ignore = true)
    @Mapping(target = "removeDistinctEvent", ignore = true)
    @Mapping(target = "distinctEvents", ignore = true)
    @Mapping(target = "removeDistinctEvent", ignore = true)
    Sel toEntity(SelDTO selDTO);

    default Sel fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sel sel = new Sel();
        sel.setId(id);
        return sel;
    }
}
