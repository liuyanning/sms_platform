package com.hero.wireless.web.service;

import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.business.ChannelFee;
import com.hero.wireless.web.entity.business.Product;
import com.hero.wireless.web.entity.business.ProductChannels;

import java.util.List;
import java.util.Map;

/**
 * 网关管理
 * 
 * @author Administrator
 * 
 */
public interface INetwayManage {
	/**
	 * 查询移动网关配置列表
	 * 
	 * @param condition
	 * @return
	 */
	List<Channel> queryChannelList(Channel condition);

	/**
	 * 保存移动网关信息
	 * 
	 * @param data
	 * @return
	 */
	Channel addChannel(Channel data);

	/**
	 * 修改移动网关配置
	 * 
	 * @param data
	 * @return
	 */
	Channel editChannel(Channel data);
	
	/**
	 * 通过主健修改通道属性
	 * @param parMap
	 * @return
	 */
    Channel updateChannelByPrimaryKey(Map<String,String> parMap);

	/**
	 * 接口余额查询
	 *
	 * @param
	 * @return
	 */
	String channelBalance(String no);

	/**
	 * 修改通道状态，启用停用
	 * 
	 * @param ids
	 * @param status
	 */
	void editChannelStatus(List<Integer> ids, String status);

	/**
	 * 通道资费列表
	 * @param channelFee
	 * @return
	 * @author volcano
	 * @date 2019年9月13日上午6:31:45
	 * @version V1.0
	 */
	List<ChannelFee> queryChannelFeeList(ChannelFee channelFee);
	
	
	/**
	 * 添加通道资费
	 * 
	 * @param channelFee
	 */
	void addChannelFee(ChannelFee channelFee);
	
	/**
	 * 修改通道资费
	 * 
	 * @param channelFee
	 */
	void editChannelFee(ChannelFee channelFee);
	
	/**
	 * 
	 * 删除通道资费
	 * 
	 * @param ckIds
	 */
	void delChannelFee(List<Integer> ckIds);
	
	
	/**
	 * 获取产品
	 * 
	 * @param product
	 * @return
	 * @author volcano
	 * @date 2019年9月14日上午11:45:40
	 * @version V1.0
	 */
	List<Product> queryProductList(Product product);

	

	/**
	 * 产品通道
	 * 
	 * @param productChannels
	 * @return
	 * @author volcano
	 * @date 2019年9月14日上午11:33:49
	 * @version V1.0
	 */
	List<ProductChannels> queryProductChannelsList(ProductChannels productChannels);


	/**
	 * 通道试一试
	* @param channelNo
	 * @param phoneNos
	 * @param content
	 * @param subCode
	 * @param countryCode
	* @author volcano
	* @date 2019年9月23日下午9:44:36
	* @version V1.0
	 */
	void try2Try(String channelNo,String phoneNos,String content, String subCode, String countryCode);


}
