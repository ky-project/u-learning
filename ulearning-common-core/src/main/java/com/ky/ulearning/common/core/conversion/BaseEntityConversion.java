package com.ky.ulearning.common.core.conversion;

import java.util.List;

/**
 * entity与dto转换类 基础接口
 *
 * @author luyuhao
 * @date 19/12/08 15:25
 */
public interface BaseEntityConversion<D, E> {
    /**
     * DTO转Entity
     *
     * @param dto dto
     * @return entity
     */
    E toEntity(D dto);

    /**
     * Entity转DTO
     *
     * @param entity entity
     * @return dto
     */
    D toDto(E entity);

    /**
     * DTO集合转Entity集合
     *
     * @param dtoList dtoList
     * @return entityList
     */
    List<E> toEntity(List<D> dtoList);

    /**
     * Entity集合转DTO集合
     *
     * @param entityList entityList
     * @return dtoList
     */
    List<D> toDto(List<E> entityList);
}
