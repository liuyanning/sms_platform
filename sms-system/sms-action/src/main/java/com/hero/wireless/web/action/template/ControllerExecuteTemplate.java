package com.hero.wireless.web.action.template;


/**
 * Controller执行模板
 * @author Volcano
 *
 */
public interface ControllerExecuteTemplate {
	void execute();
	/**
	 * 子类实现具体业务
	 */
	void doImpl();
}
