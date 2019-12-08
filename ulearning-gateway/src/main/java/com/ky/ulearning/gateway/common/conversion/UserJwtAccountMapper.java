package com.ky.ulearning.gateway.common.conversion;

import com.ky.ulearning.common.core.conversion.BaseEntityConversion;
import com.ky.ulearning.gateway.common.security.JwtAccount;
import com.ky.ulearning.spi.common.dto.UserContext;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author luyuhao
 * @date 19/12/09 02:34
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserJwtAccountMapper extends BaseEntityConversion<JwtAccount, UserContext> {
}
