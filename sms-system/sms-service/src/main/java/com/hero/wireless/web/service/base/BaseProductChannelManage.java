package com.hero.wireless.web.service.base;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hero.wireless.enums.ProductChannelDiversionType;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.web.dao.business.IExportFileDAO;
import com.hero.wireless.web.dao.business.IProductChannelsDAO;
import com.hero.wireless.web.dao.business.IProductChannelsDiversionDAO;
import com.hero.wireless.web.dao.business.IProductDAO;
import com.hero.wireless.web.entity.business.ExportFile;
import com.hero.wireless.web.entity.business.Product;
import com.hero.wireless.web.entity.business.ProductChannels;
import com.hero.wireless.web.entity.business.ProductChannelsDiversion;
import com.hero.wireless.web.entity.business.ProductChannelsDiversionExample;
import com.hero.wireless.web.entity.business.ProductChannelsExample;
import com.hero.wireless.web.entity.business.ProductExample;
import com.hero.wireless.web.entity.business.ext.ProductExt;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.annotation.Resource;

public class BaseProductChannelManage extends BaseService {
	
	@Resource(name = "IProductChannelsDAO")
	protected IProductChannelsDAO<ProductChannels> productChannelsDAO;

	@Resource(name = "IProductChannelsDiversionDAO")
	protected IProductChannelsDiversionDAO<ProductChannelsDiversion> productChannelsDiversionDAO;
	
	@Resource(name = "IExportFileDAO")
	protected IExportFileDAO<ExportFile> exportFileDAO;

	@Resource(name = "IProductDAO")
	protected IProductDAO<Product> productDAO;

	protected ProductChannelsExample assemblyProductChannelsListConditon(ProductChannels productChannels) {
		ProductChannelsExample example = new ProductChannelsExample();
		ProductChannelsExample.Criteria criteria = example.createCriteria();
		if (null != productChannels.getId()) {
			criteria.andIdEqualTo(productChannels.getId());
		}
		if (StringUtils.isNotBlank(productChannels.getProduct_No())) {
			criteria.andProduct_NoEqualTo(productChannels.getProduct_No());
		}
		if (null != productChannels.getWeight()) {
			criteria.andWeightEqualTo(productChannels.getWeight());
		}
		if (StringUtils.isNotBlank(productChannels.getChannel_No())) {
			criteria.andChannel_NoEqualTo(productChannels.getChannel_No());
		}
		if (StringUtils.isNotBlank(productChannels.getOperator())) {
			criteria.andOperatorEqualTo(productChannels.getOperator());
		}
		if (StringUtils.isNotBlank(productChannels.getMessage_Type_Code())) {
			criteria.andMessage_Type_CodeEqualTo(productChannels.getMessage_Type_Code());
		}
		if (StringUtils.isNotBlank(productChannels.getCountry_Number())) {
			criteria.andCountry_NumberEqualTo(productChannels.getCountry_Number());
		}
		return example;
	}

	protected ProductChannelsDiversionExample assemblyProductChannelsDiversionListConditon(
			ProductChannelsDiversion condition) {
		ProductChannelsDiversionExample example = new ProductChannelsDiversionExample();
		ProductChannelsDiversionExample.Criteria criteria = example.createCriteria();
		if (null != condition.getId()) {
			criteria.andIdEqualTo(condition.getId());
		}
		if (null != condition.getProduct_Channels_Id()) {
			criteria.andProduct_Channels_IdEqualTo(condition.getProduct_Channels_Id());
		}
		if (StringUtils.isNotBlank(condition.getStatus_Code())) {
			criteria.andStatus_CodeEqualTo(condition.getStatus_Code());
		}
		if (StringUtils.isNotBlank(condition.getStrategy_Type_Code())) {
			criteria.andStrategy_Type_CodeEqualTo(condition.getStrategy_Type_Code());
		}
		if (StringUtils.isNotBlank(condition.getStrategy_Rule()) && condition.getStrategy_Rule().contains("%")) {
			criteria.andStrategy_RuleLike(condition.getStrategy_Rule());
		} else if(StringUtils.isNotBlank(condition.getStrategy_Rule())) {
			criteria.andStrategy_RuleEqualTo(condition.getStrategy_Rule());
		}
		return example;
	}

	protected List<ProductChannelsDiversion> blockImport(String textLine, String splitChar,
			List<ProductChannelsDiversion> insertList, ProductChannelsDiversion productChannelsDiversion
            ,Integer appendRandomSize) {
		
		String[] blocks = getStartNoAndEndNo(textLine, splitChar);
		String strZero = subZero(blocks[0]);
		long count = checkBlockNo(blocks[0], blocks[1]);
		for (int i = 0; i <= count; i++) {
		    String no = strZero + (Long.parseLong(blocks[0].substring(strZero.length())) + i);
		    String strategyRule = getPhonePoolJson(no,appendRandomSize);
            insertList = batchInsert(strategyRule, insertList, productChannelsDiversion);
		}
		return insertList;
	}

    protected String getPhonePoolJson(String no, Integer appendRandomSize) {
        Map<String,Object> map = new TreeMap<>();
        map.put("appendRandomSize",appendRandomSize);
        map.put("callerNo",no);
        try {
            return JsonUtil.STANDARD.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected List<ProductChannelsDiversion> batchInsert(String strategyRule, List<ProductChannelsDiversion> insertList,
			ProductChannelsDiversion productChannelsDiversion) {
		ProductChannelsDiversion insertData = new ProductChannelsDiversion();
		BeanUtils.copyProperties(productChannelsDiversion, insertData);
		insertData.setStrategy_Rule(strategyRule);
		insertList.add(insertData);
		if (insertList.size() == 200) {
			productChannelsDiversionDAO.insertList(insertList);
			insertList = new ArrayList<>();
		}
		return insertList;
	}

	protected void exportDiversionFile(String baseExportBasePath, List<ProductChannelsDiversion> list,
			ExportFile exportFile, String type) {
		try {
			SuperLogger.debug(baseExportBasePath);
			String path = baseExportBasePath + "/admin/export";
			exportFile.setFIle_Name(exportFile.getBatch_Id() + ".txt");
			exportFile.setFile_Url(path + "/" + exportFile.getFIle_Name());
			// 生成的文件路径
			File file = new File(exportFile.getFile_Url());
			if (!file.exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
			OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			BufferedWriter bw = new BufferedWriter(fw);
			StringBuffer sb = new StringBuffer();
			for (ProductChannelsDiversion diversion : list) {
				if (ProductChannelDiversionType.SIGNATURE.toString().equals(type)) {
					Map<String, String> map = JsonUtil.readValue(diversion.getStrategy_Rule(),
							new TypeReference<Map<String, String>>() {
							});
					sb.append(map.get("signature")).append(",");
					sb.append(map.get("subCode"));
				} else {
                    sb.append(diversion.getStrategy_Rule());
                }
                sb.append("\r\n");
			}
			bw.write(sb.toString());
			bw.flush();
			bw.close();
			fw.close();
			this.exportFileDAO.insert(exportFile);
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 检查区间的开始字符和结束字符是不是数字
	 * 
	 * @param textLine
	 * @param splitChar
	 * @return
	 */
	private String[] getStartNoAndEndNo(String textLine, String splitChar) {
		String[] blocks = textLine.split(splitChar);
		if (blocks.length > 2) {
			throw new ServiceException("格式错误");
		}
		String startNo = blocks[0];
		String endNo = blocks[1];
		if(!NumberUtils.isDigits(startNo)|| !NumberUtils.isDigits(endNo)) {
			throw new ServiceException("文件内容不是数字");
		}
		return blocks;
	}
	/**
	 * 数据差距是不是大于限制空间
	 * 
	 * @param startNo
	 * @param endNo
	 * @return
	 */
	private long checkBlockNo(String startNo, String endNo) {
		long count = Long.parseLong(endNo) - Long.parseLong(startNo);
		if (count > 50000) {
			throw new ServiceException("数据量过大，最大5万");
		}
		return count;
	}
	
	private String subZero(String str) {
		StringBuffer reStr = new StringBuffer();
		if (str.startsWith("0")) {
			reStr.append("0");
			str = str.substring(1);
			reStr.append(subZero(str));
		}
		return reStr.toString();
	}

	protected ProductExample assemblyProductListConditon(ProductExt productExt) {
		ProductExample example = new ProductExample();
		ProductExample.Criteria criteria = example.createCriteria();
		if (productExt.getId() != null) {
			criteria.andIdEqualTo(productExt.getId());
		}
		if (StringUtils.isNotBlank(productExt.getName())) {
			criteria.andNameLike("%" + productExt.getName() + "%");
		}
		if (StringUtils.isNotBlank(productExt.getNo())) {
			criteria.andNoEqualTo(productExt.getNo());
		}
		if (null != productExt.getIntercept_Strategy_Id()) {
			criteria.andIntercept_Strategy_IdEqualTo(productExt.getIntercept_Strategy_Id());
		}
		if (StringUtils.isNotBlank(productExt.getTrade_Type_Code())) {
			criteria.andTrade_Type_CodeEqualTo(productExt.getTrade_Type_Code());
		}
		if (StringUtils.isNotBlank(productExt.getStatus_Code())) {
			criteria.andStatus_CodeEqualTo(productExt.getStatus_Code());
		}
		if (StringUtils.isNotEmpty(productExt.getChannel_No())){
			ProductChannels productChannels = new ProductChannels();
			productChannels.setChannel_No(productExt.getChannel_No());
			ProductChannelsExample channelsExample = assemblyProductChannelsListConditon(productChannels);
			List<ProductChannels> productChannelsList = productChannelsDAO.selectByExample(channelsExample);
			List<String> productNos = new ArrayList<>();
			if (productChannelsList.size() < 1){
				productNos.add("-1");
			}
			for (ProductChannels channels: productChannelsList){
				productNos.add(channels.getProduct_No());
			}
			criteria.andNoIn(productNos);
		}
		if(productExt.getProductNos().size() > 0){
			criteria.andNoIn(productExt.getProductNos());
		}
		return example;
	}

    //停用|启用产品时 通道权重修改
    protected void updateProductChannelsWeight(List<Integer> productIds, String status) {
	    //获取所有产品
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andIdIn(productIds);
        List<Product> products = productDAO.selectByExample(productExample);
        List<String> noList = products.stream().map(Product::getNo).collect(Collectors.toList());
        ProductChannelsExample productChannelsExample = new ProductChannelsExample();
        ProductChannelsExample.Criteria cri = productChannelsExample.createCriteria();
        cri.andProduct_NoIn(noList);
        ProductChannels productChannels = new ProductChannels();
        productChannels.setWeight(Constant.STATUS_CODE_START.equals(status)?1:0);
        productChannelsDAO.updateByExampleSelective(productChannels,productChannelsExample);
    }
}