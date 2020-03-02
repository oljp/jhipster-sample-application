package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.DistinctEventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DistinctEvent} and its DTO {@link DistinctEventDTO}.
 */
@Mapper(componentModel = "spring", uses = {SelMapper.class})
public interface DistinctEventMapper extends EntityMapper<DistinctEventDTO, DistinctEvent> {


    @Mapping(target = "removeInitiatingSel", ignore = true)
    @Mapping(target = "removeAssociatedSel", ignore = true)

    default DistinctEvent fromId(Long id) {
        if (id == null) {
            return null;
        }
        DistinctEvent distinctEvent = new DistinctEvent();
        distinctEvent.setId(id);
        return distinctEvent;
    }
}
