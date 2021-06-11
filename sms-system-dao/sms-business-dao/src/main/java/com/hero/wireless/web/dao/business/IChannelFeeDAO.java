package com.hero.wireless.web.dao.business;

import com.hero.wireless.web.dao.base.IDao;
import com.hero.wireless.web.entity.business.ChannelFee;
import com.hero.wireless.web.entity.business.ChannelFeeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IChannelFeeDAO<T extends ChannelFee> extends IDao<ChannelFee, ChannelFeeExample> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table channel_fee
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int countByExample(ChannelFeeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table channel_fee
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int deleteByExample(ChannelFeeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table channel_fee
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table channel_fee
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int insert(ChannelFee record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table channel_fee
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int insertList(List<ChannelFee> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table channel_fee
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int insertSelective(ChannelFee record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table channel_fee
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    List<ChannelFee> selectByExample(ChannelFeeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table channel_fee
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    ChannelFee selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table channel_fee
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int updateByExampleSelective(@Param("record") ChannelFee record, @Param("example") ChannelFeeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table channel_fee
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int updateByExample(@Param("record") ChannelFee record, @Param("example") ChannelFeeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table channel_fee
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int updateByPrimaryKeySelective(ChannelFee record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table channel_fee
     *
     * @mbg.generated Sat Sep 14 11:02:15 CST 2019
     */
    int updateByPrimaryKey(ChannelFee record);
}