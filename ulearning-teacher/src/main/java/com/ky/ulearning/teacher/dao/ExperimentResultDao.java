package com.ky.ulearning.teacher.dao;

import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.student.dto.ExperimentResultDto;
import com.ky.ulearning.spi.student.entity.ExperimentResultEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 实验结果dao
 *
 * @author luyuhao
 * @since 2020/03/06 01:42
 */
@Mapper
@Repository
public interface ExperimentResultDao {

    /**
     * 根据id查询实验结果
     *
     * @param id id
     * @return 实验结果对象
     */
    ExperimentResultDto getById(Long id);

    /**
     * 更新实验结果
     *
     * @param experimentResultEntity 待更新的对象
     */
    void update(ExperimentResultEntity experimentResultEntity);

    /**
     * 根据实验id和学生id查询实验结果
     *
     * @param experimentId 实验id
     * @param stuId        学生id
     * @return 实验结果
     */
    ExperimentResultEntity getByExperimentIdAndStuId(@Param("experimentId") Long experimentId,
                                                     @Param("stuId") Long stuId);

    /**
     * 分页查询实验结果
     *
     * @param experimentResultDto 筛选条件
     * @param pageParam           分页参数
     * @return 实验结果集合
     */
    List<ExperimentResultDto> listPage(@Param("experimentResultDto") ExperimentResultDto experimentResultDto,
                                       @Param("pageParam") PageParam pageParam);

    /**
     * 分页查询实验结果 - 总记录数
     *
     * @param experimentResultDto 筛选条件
     * @return 总记录数
     */
    Integer countListPage(@Param("experimentResultDto") ExperimentResultDto experimentResultDto);

    /**
     * 更新分享
     *
     * @param id               实验结果id
     * @param username         更新者
     * @param experimentShared 是否分享展示
     */
    void updateSharedById(@Param("id") Long id, @Param("username") String username, @Param("experimentShared") Boolean experimentShared);

    /**
     * 查询所有实验结果
     *
     * @param experimentId 实验id
     * @return 实验结果集合
     * @author luyuhao
     * @date 20/07/08 02:33
     */
    List<ExperimentResultDto> listByExperimentId(@Param("experimentId") Long experimentId);
}
