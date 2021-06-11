package com.hero.wireless.web.service;

import com.hero.wireless.web.entity.business.ExportFile;
import com.hero.wireless.web.entity.business.Product;
import com.hero.wireless.web.entity.business.ProductChannels;
import com.hero.wireless.web.entity.business.ProductChannelsDiversion;
import com.hero.wireless.web.entity.business.ProductChannelsDiversionExample;
import com.hero.wireless.web.entity.business.ext.ProductExt;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.function.Consumer;

/**
 * 通道产品的service类 IChannelProductManage.java
 *
 * @author wjl
 * @date 2020年2月11日上午11:08:08
 */
public interface IProductChannelManage {

    /**
     * 添加产品
     *
     * @param product
     */
    @CacheEvict(value = "product_list", allEntries = true)
    void addProduct(Product product);

    /**
     * 删除产品
     *
     * @param ckIds
     */
    @CacheEvict(value = "product_list", allEntries = true)
    void deleteProductByIdList(List<Integer> ckIds);

    /**
     * 主键查询产品
     *
     * @param id
     */
    Product queryProductById(Integer id);

    /**
     * 编辑产品
     *
     * @param product
     */
    @CacheEvict(value = "product_list", allEntries = true)
    void editProduct(Product product);

    /**
     * 根据条件查询产品
     *
     * @param productExt
     */
    @Cacheable(value = "product_list", condition = "#p0.pagination==null")
    List<Product> queryProductList(ProductExt productExt);

    /**
     * 更新产品状态
     *
     * @param ckIds
     */
    @CacheEvict(value = "product_list", allEntries = true)
    void updateStatusProduct(List<Integer> ckIds, String status);

    /**
     * 查询产品通道列表
     *
     * @param productChannels
     * @return
     */
    @Cacheable(value = "product_channels_list", condition = "#p0.pagination==null")
    List<ProductChannels> queryProductChannelsList(ProductChannels productChannels);

    /**
     * 添加产品通道
     *
     * @param productChannels
     */
    @CacheEvict(value = "product_channels_list", allEntries = true)
    void addProductChannels(ProductChannels productChannels);

    /**
     * 删除产品通道 会将所有通道策略删除
     *
     * @param id
     */
    @CacheEvict(value = "product_channels_list", allEntries = true)
    void deleteProductChannelsById(Integer id);

    /**
     * 修改产品通道
     *
     * @param productChannels
     */
    @CacheEvict(value = "product_channels_list", allEntries = true)
    void editProductChannels(ProductChannels productChannels);

    /**
     * 通过主健查询通道产品
     *
     * @param id
     * @return
     */
    ProductChannels queryProductChannelsById(Integer id);

    /**
     * TODO 查询产品通道列表
     *
     * @param diversion
     * @return
     */
    List<ProductChannelsDiversion> queryProductChannelsDiversionList(ProductChannelsDiversion diversion);

    /**
     * 导入号码池 TODO
     *
     * @param phonePool
     */
    void importProductChannelsPhoneNoPool(MultipartFile phoneNoFile, ProductChannelsDiversion phonePool,Integer appendRandomSize);

    /**
     * TODO 通过主健id获取ProductChannelsDiversion
     *
     * @param id
     * @return
     */
    ProductChannelsDiversion queryProductChannelsDiversionById(Integer id);

    /**
     * 通过产品通道Id和策略类型获取单个ProductChannelsDiversion（适用于单条策略）
     *
     * @param productChannelId
     * @param type
     */
    ProductChannelsDiversion queryTypeDiversionByProductChannelId(Integer productChannelId, String type);

    /**
     * 导流策略添加关键字
     *
     * @param diversion
     */
    void addProductChannelsDiversion(ProductChannelsDiversion diversion);

    /**
     * 导流策略修改关键字
     *
     * @param diversion
     */

    void updateProductChannelsDiversion(ProductChannelsDiversion diversion);

    /**
     * 导流策略导入关键字或签名
     *
     * @param importFile
     * @param productChannelsDiversion
     * @param strategyType
     */
    void importProductChannelsDiversion(MultipartFile importFile, ProductChannelsDiversion productChannelsDiversion,
                                        String strategyType);

    /**
     * 导流策略导出关键字或签名
     *
     * @param realPath
     * @param productChannelsId
     * @param exportFile
     * @param type
     */
    void exportProductChannelsDiversion(String realPath, Integer productChannelsId, ExportFile exportFile, String type);

    /**
     * 导流策略通过主健批量删除导流策略
     *
     * @param ids
     */
    void delProductChannelsDiversion(List<Integer> ids,String type);

    /**
     * 通过条件删除导流测率
     *
     * @param diversion
     */
    void delProductChannelsDiversionByCondition(ProductChannelsDiversion diversion);

    /**
     * 导流策略删除或新增导流策略（根据是否有ID判断）
     *
     * @param diversion
     */
    void updateOrInsertDiversion(ProductChannelsDiversion diversion);

    /**
     * 导流策略修改状态，启用，停用
     *
     * @param ids
     * @param status
     */
    void updateDiversionStatus(List<Integer> ids, String status,String type);

   /**
    * 查询导流策略用于缓存
    * @param conditionFun
    * @return
    */
    List<ProductChannelsDiversion> queryProductChannelsDiversionByCache(Consumer<ProductChannelsDiversionExample.Criteria> conditionFun);

}