package com.hero.wireless.sort;

/**
 * 
 * 事务问题，单独出类
 * 
 * @author volcano
 * @date 2019年11月9日下午6:58:44
 * @version V1.0
 */
public interface ISortChargingService {

	/**
	 * 分拣计费
	 * @param ctx
	 * @return
	 */
	boolean charging(SortContext ctx);
}
