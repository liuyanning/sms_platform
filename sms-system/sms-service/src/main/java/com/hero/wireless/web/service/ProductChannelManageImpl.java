package com.hero.wireless.web.service;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.ProductChannelDiversionType;
import com.hero.wireless.enums.ProductStatus;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.base.Pagination;
import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.entity.business.ProductChannelsDiversionExample.Criteria;
import com.hero.wireless.web.entity.business.ext.ProductExt;
import com.hero.wireless.web.service.base.BaseProductChannelManage;
import com.hero.wireless.web.util.BeanUtil;
import com.hero.wireless.web.util.CodeUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Consumer;

@Service("productChannelManage")
public class ProductChannelManageImpl extends BaseProductChannelManage implements IProductChannelManage {

	@Override
	public void addProduct(Product product) {
		product.setCreate_Date(new Date());
		product.setNo(CodeUtil.buildProductNo());
		product.setStatus_Code(Constant.STATUS_CODE_STOP);
		productDAO.insert(product);
	}

	@Override
	public void deleteProductByIdList(List<Integer> ckIds) {
		// 同时删除所属产品通道，相关导流策略数据
		ProductExample example = new ProductExample();
		ProductExample.Criteria cri = example.createCriteria();
		cri.andIdIn(ckIds);
		List<Product> productList = productDAO.selectByExample(example);
		for (Product product : productList) {
			delProductChannelsByProduct(product);
		}
		productDAO.deleteByExample(example);
	}

	protected void delProductChannelsByProduct(Product product) {
		ProductChannelsExample productChannelsExample = new ProductChannelsExample();
		ProductChannelsExample.Criteria criteria = productChannelsExample.createCriteria();
		criteria.andProduct_NoEqualTo(product.getNo());
		List<ProductChannels> productChannelsList = this.productChannelsDAO.selectByExample(productChannelsExample);
		for (ProductChannels productChannels : productChannelsList) {
			ProductChannelsDiversion condition = new ProductChannelsDiversion();
			condition.setProduct_Channels_Id(productChannels.getId());
			delProductChannelsDiversionByCondition(condition);
		}
		this.productChannelsDAO.deleteByExample(productChannelsExample);
	}

	@Override
	public Product queryProductById(Integer id) {
		return productDAO.selectByPrimaryKey(id);
	}

	@Override
	public void editProduct(Product product) {
		productDAO.updateByPrimaryKeySelective(product);
	}

	@Override
	public List<Product> queryProductList(ProductExt productExt) {

		List<ProductChannelsDiversion> diversionsList = new ArrayList<ProductChannelsDiversion>();
		if(StringUtils.isNotEmpty(productExt.getSignature())){
			ProductChannelsDiversionExample productChannelsDiversionExample = new ProductChannelsDiversionExample();
			ProductChannelsDiversionExample.Criteria criteria = productChannelsDiversionExample.createCriteria();
			criteria.andStrategy_RuleLike("%"+productExt.getSignature()+"%");
			diversionsList = productChannelsDiversionDAO.selectByExample(productChannelsDiversionExample);
		}
		List<ProductChannels> productChannelsList = new ArrayList<ProductChannels>();
		if(diversionsList.size() > 0){
			List<Integer> productChnnelIds = new ArrayList<Integer>();
			diversionsList.forEach(entity -> {
				productChnnelIds.add(entity.getProduct_Channels_Id());
			});
			ProductChannelsExample productChannelsExample = new ProductChannelsExample();
			ProductChannelsExample.Criteria criteria = productChannelsExample.createCriteria();
			criteria.andIdIn(productChnnelIds);
			productChannelsList = productChannelsDAO.selectByExample(productChannelsExample);
		}
		productChannelsList.forEach(entity -> {
			productExt.getProductNos().add(entity.getProduct_No());
		});
		ProductExample example = assemblyProductListConditon(productExt);
		example.setPagination(productExt.getPagination());
		example.setOrderByClause(" id desc ");
		return productDAO.selectByExamplePage(example);
	}

	@Override
	public void updateStatusProduct(List<Integer> ckIds, String status) {
	    //停用|启用产品 通道权重修改
        updateProductChannelsWeight(ckIds,status);
		ProductExample example = new ProductExample();
		example.createCriteria().andIdIn(ckIds);
		Product product = new Product();
		product.setStatus_Code(status);
		productDAO.updateByExampleSelective(product, example);
	}

	@Override
	public List<ProductChannels> queryProductChannelsList(ProductChannels productChannels) {
		ProductChannelsExample example = assemblyProductChannelsListConditon(productChannels);
		example.setOrderByClause(" id desc ");
		example.setPagination(productChannels.getPagination());
		return productChannelsDAO.selectByExamplePage(example);
	}

	@Override
	public void addProductChannels(ProductChannels productChannels) {
		productChannels.setCreate_Date(new Date());
		productChannelsDAO.insert(productChannels);
	}

	@Override
	public void editProductChannels(ProductChannels productChannels) {
		productChannelsDAO.updateByPrimaryKeySelective(productChannels);
	}

	@Override
	public ProductChannels queryProductChannelsById(Integer id) {
		if (id == null || id == 0) {
			throw new ServiceException("id is null");
		}
		return productChannelsDAO.selectByPrimaryKey(id);
	}

	@Override
	public void deleteProductChannelsById(Integer id) {
		if (id == null || id == 0) {
			throw new NullPointerException("id is null");
		}
		ProductChannelsDiversion condition = new ProductChannelsDiversion();
		condition.setProduct_Channels_Id(id);
		delProductChannelsDiversionByCondition(condition);
		productChannelsDAO.deleteByPrimaryKey(id);
	}

	@Override
	public List<ProductChannelsDiversion> queryProductChannelsDiversionList(ProductChannelsDiversion condition) {
		ProductChannelsDiversionExample example = assemblyProductChannelsDiversionListConditon(condition);
		example.setPagination(condition.getPagination());
		example.setOrderByClause(" id desc ");
		return productChannelsDiversionDAO.selectByExamplePage(example);
	}

	@Override
	public void importProductChannelsPhoneNoPool(MultipartFile phoneNoFile,
			ProductChannelsDiversion productChannelsDiversion,Integer appendRandomSize) {
		productChannelsDiversion.setStrategy_Type_Code(ProductChannelDiversionType.PHONE_NO_POLL.toString());
		productChannelsDiversion.setName(ProductChannelDiversionType.PHONE_NO_POLL.getName());
		productChannelsDiversion.setCreate_Date(new Date());
		readFile(phoneNoFile, productChannelsDiversion, new IImportFile() {
			@Override
			public void doBusiness(InputStreamReader inputStreamReader, BufferedReader reader) throws Exception {
				String splitChar = DatabaseCache.getStringValueBySystemEnvAndCode("splitChar", "-");
				String textLine = "";
				List<ProductChannelsDiversion> insertList = new ArrayList<>();
				while ((textLine = reader.readLine()) != null) {
					if (StringUtils.isBlank(textLine)) {
						continue;
					}
					if (textLine.contains(splitChar)) {
						insertList = blockImport(textLine, splitChar, insertList, productChannelsDiversion,appendRandomSize);
					} else if (NumberUtils.isDigits(textLine)) {
						insertList = batchInsert(getPhonePoolJson(textLine,appendRandomSize), insertList, productChannelsDiversion);
					}
				}
				if (insertList.size() > 0) {
					productChannelsDiversionDAO.insertList(insertList);
				}
			}
		});
	}

	@Override
	public ProductChannelsDiversion queryProductChannelsDiversionById(Integer id) {
		if (id == null || id == 0) {
			throw new ServiceException("请选择一条数据");
		}
		return productChannelsDiversionDAO.selectByPrimaryKey(id);
	}

	@Override
	public ProductChannelsDiversion queryTypeDiversionByProductChannelId(Integer productChannelId, String type) {
		ProductChannelsDiversion diversion = new ProductChannelsDiversion();
		diversion.setProduct_Channels_Id(productChannelId);
		diversion.setStrategy_Type_Code(type);
		List<ProductChannelsDiversion> diversionList = queryProductChannelsDiversionList(diversion);
		return ObjectUtils.isEmpty(diversionList) ? new ProductChannelsDiversion() : diversionList.get(0);
	}

	@Override
	public void exportProductChannelsDiversion(final String baseExportBasePath, final Integer productChannelsId,
			final ExportFile exportFile, String type) {
		String redisKey = newThreadBefore(Constant.THREAD_TOTAL_EXPORT);// 校验
		new Thread() {
			public void run() {
				ProductChannelsDiversion diversion = new ProductChannelsDiversion();
				diversion.setProduct_Channels_Id(productChannelsId);
				diversion.setStrategy_Type_Code(type);
				ProductChannelsDiversionExample example = assemblyProductChannelsDiversionListConditon(diversion);
				List<ProductChannelsDiversion> list = productChannelsDiversionDAO.selectByExample(example);
				if (list == null || list.size() < 1) {
					return;
				}
				exportFile.setBatch_Id(UUID.randomUUID().toString());
				exportFile.setCreate_Date(new Date());
				exportFile.setId(null);
				exportDiversionFile(baseExportBasePath, list, exportFile, type);
				newThreadAfter(redisKey);
			}
		}.start();
	}

	@Override
	public void addProductChannelsDiversion(ProductChannelsDiversion diversion) {
		diversion.setCreate_Date(new Date());
		productChannelsDiversionDAO.insert(diversion);
	}

	@Override
	public void updateProductChannelsDiversion(ProductChannelsDiversion diversion) {
		productChannelsDiversionDAO.updateByPrimaryKeySelective(diversion);
	}

	@Override
	public void importProductChannelsDiversion(MultipartFile importFile,
			ProductChannelsDiversion productChannelsDiversion, String strategyType) {
		productChannelsDiversion.setStrategy_Type_Code(strategyType);
		productChannelsDiversion.setCreate_Date(new Date());
		readFile(importFile, productChannelsDiversion, new IImportFile() {
			@Override
			public void doBusiness(InputStreamReader inputStreamReader, BufferedReader reader) throws Exception {
				String textLine = "";
				List<ProductChannelsDiversion> insertList = new ArrayList<>();
				while ((textLine = reader.readLine()) != null) {
					if (StringUtils.isBlank(textLine)) {
						continue;
					}
					if (ProductChannelDiversionType.SIGNATURE.toString().equals(strategyType)) {
						String[] lineArray = textLine.split(",");
						String key = lineArray[0].trim();
						if(key.startsWith(BeanUtil.UFEFF)){
							key = key.replaceAll(BeanUtil.UFEFF, "");
						}
						String subCode = lineArray.length > 1 ? lineArray[1].trim() : "";
						if (ObjectUtils.isEmpty(key) || ObjectUtils.isEmpty(subCode)) {
							throw new ServiceException("导入失败，请勿上传空值");
						}
						Map<String, String> map = new TreeMap<>();
						map.put("signature", key);
						map.put("subCode", subCode);
						textLine = JsonUtil.STANDARD.writeValueAsString(map);
					}
					insertList = batchInsert(textLine, insertList, productChannelsDiversion);
				}
				if (insertList.size() > 0) {
					productChannelsDiversionDAO.insertList(insertList);
				}
			}
		});
	}

	@Override
	public void delProductChannelsDiversion(List<Integer> ckIds,String type) {
		if (ObjectUtils.isEmpty(ckIds)) {
			return;
		}
		ckIds.stream().forEach(item -> {
			this.productChannelsDiversionDAO.deleteByPrimaryKey(item);
		});
	}

	@Override
	public void delProductChannelsDiversionByCondition(ProductChannelsDiversion diversion) {
		ProductChannelsDiversionExample example = assemblyProductChannelsDiversionListConditon(diversion);
		productChannelsDiversionDAO.deleteByExample(example);
	}

	@Override
	public void updateOrInsertDiversion(ProductChannelsDiversion diversion) {
		if (diversion.getId() != null) {
			this.productChannelsDiversionDAO.updateByPrimaryKeySelective(diversion);
		} else {
			diversion.setCreate_Date(new Date());
			this.productChannelsDiversionDAO.insert(diversion);
		}
	}

	@Override
	public void updateDiversionStatus(List<Integer> ids, String status,String type) {
		if (ObjectUtils.isEmpty(ids)) {
			return;
		}
		ids.stream().forEach(item -> {
			ProductChannelsDiversion diversion = new ProductChannelsDiversion();
			diversion.setId(item);
			diversion.setStatus_Code(status);
			productChannelsDiversionDAO.updateByPrimaryKeySelective(diversion);
		});
	}

	private void readFile(MultipartFile phoneNoFile, ProductChannelsDiversion productChannelsDiversion,
			IImportFile importFile) {
		BufferedReader reader = null;
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(phoneNoFile.getInputStream());
			reader = new BufferedReader(inputStreamReader);
			importFile.doBusiness(inputStreamReader, reader);
		} catch (ServiceException e) {
			throw new ServiceException(e.getMessage());
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		} finally {
			try {
				reader.close();
				inputStreamReader.close();
			} catch (IOException e) {
				SuperLogger.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public List<ProductChannelsDiversion> queryProductChannelsDiversionByCache(Consumer<Criteria> conditionFun) {
		List<ProductChannelsDiversion> all = new ArrayList<>();
		int pageSize = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "datamigratepagesize", 500);
		Pagination pagination = new Pagination(1, pageSize);
		while (true) {
			ProductChannelsDiversionExample example = new ProductChannelsDiversionExample();
			example.setPagination(pagination);
			ProductChannelsDiversionExample.Criteria cri = example.createCriteria();
			cri.andStatus_CodeEqualTo(ProductStatus.START.toString());
			conditionFun.accept(cri);
			List<ProductChannelsDiversion> list = productChannelsDiversionDAO.selectByExample(example);
			if (ObjectUtils.isEmpty(list)) {
				break;
			}
			pagination = new Pagination(pagination.getPageIndex() + 1, pageSize);
			all.addAll(list);
		}
		return all;
	}

}

interface IImportFile {
	void doBusiness(InputStreamReader inputStreamReader, BufferedReader reader) throws Exception;
}