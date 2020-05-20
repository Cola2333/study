package life.usc.study.mapper;

import java.util.List;
import life.usc.study.model.Admin;
import life.usc.study.model.AdminExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AdminMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ADMIN
     *
     * @mbg.generated Tue May 19 15:30:57 PDT 2020
     */
    long countByExample(AdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ADMIN
     *
     * @mbg.generated Tue May 19 15:30:57 PDT 2020
     */
    int deleteByExample(AdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ADMIN
     *
     * @mbg.generated Tue May 19 15:30:57 PDT 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ADMIN
     *
     * @mbg.generated Tue May 19 15:30:57 PDT 2020
     */
    int insert(Admin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ADMIN
     *
     * @mbg.generated Tue May 19 15:30:57 PDT 2020
     */
    int insertSelective(Admin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ADMIN
     *
     * @mbg.generated Tue May 19 15:30:57 PDT 2020
     */
    List<Admin> selectByExampleWithRowbounds(AdminExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ADMIN
     *
     * @mbg.generated Tue May 19 15:30:57 PDT 2020
     */
    List<Admin> selectByExample(AdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ADMIN
     *
     * @mbg.generated Tue May 19 15:30:57 PDT 2020
     */
    Admin selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ADMIN
     *
     * @mbg.generated Tue May 19 15:30:57 PDT 2020
     */
    int updateByExampleSelective(@Param("record") Admin record, @Param("example") AdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ADMIN
     *
     * @mbg.generated Tue May 19 15:30:57 PDT 2020
     */
    int updateByExample(@Param("record") Admin record, @Param("example") AdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ADMIN
     *
     * @mbg.generated Tue May 19 15:30:57 PDT 2020
     */
    int updateByPrimaryKeySelective(Admin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ADMIN
     *
     * @mbg.generated Tue May 19 15:30:57 PDT 2020
     */
    int updateByPrimaryKey(Admin record);
}