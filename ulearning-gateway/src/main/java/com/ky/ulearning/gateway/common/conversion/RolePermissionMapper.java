package com.ky.ulearning.gateway.common.conversion;

import com.ky.ulearning.common.core.conversion.BaseEntityConversion;
import com.ky.ulearning.spi.system.dto.RolePermissionDto;
import com.ky.ulearning.spi.system.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author luyuhao
 * @date 19/12/08 15:23
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RolePermissionMapper
        extends BaseEntityConversion<RolePermissionDto, RoleEntity> {
}
