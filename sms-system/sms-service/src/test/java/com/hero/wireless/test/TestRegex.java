package com.hero.wireless.test;


import org.apache.commons.lang3.StringUtils;

public class TestRegex {

	public static void main(String[] args) {
		String content = "【省呗】尾号-5056用户，您的47800元額度已于7月3日到.达！日息低至2元，请24小时内领取(的），点击 tb59.cn/c 退回T";
		System.out.println(content);
		String item = "【省呗】尾号{1}用户，您的47800元額度已于{1}到.达！日息低至2元，请24小时内领取(的），点击 {1}{2}{3}{4} 退回T";
		System.out.println(escapeExprSpecialWord(item));
		item = escapeExprSpecialWord(item);
		//item = item.replaceAll("\\{\\d+\\}", "([\u4e00-\u9fa5a-zA-Z0-9]{1,6})");
//		String regEx ="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]{1,6}";
		//item = item.replaceAll("\\{\\d+\\}", "[a-zA-Z0-9\\.\\:\\,\\?\\//\\+/a-zA-Z0-9%a-zA-Z0-9]");

		item = item.trim().replaceAll("\\{\\d+\\}", "([-%\\\\+\\\\“\\\\”\\\\*\\\\u4e00-\\\\u9fa5a-zA-Z0-9\\.:/\\?\\\\[\\\\]]{1,6})");
//		item = item.trim().replaceAll("\\{\\d+\\}", regEx);
		item = "^" + item + "$";
		//String regex = "([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)";
//		String regex = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
//		Pattern pattern = Pattern.compile(item);
//		Matcher matcher = pattern.matcher(content);
//		System.out.println(matcher.matches());
//		item = item.replaceAll("\\{\\d+\\}", regex);
		System.out.println(item);
		//item = "^" + item + "$";

		try {
			System.out.println(content.trim().matches(item));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}


	/**
	 * 转义正则特殊字符 （$()*+.[]?\^{},|）
	 *
	 * @param keyword
	 * @return
	 */
	public static String escapeExprSpecialWord(String keyword) {
		if (StringUtils.isNotBlank(keyword)) {
			String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "|" };
			for (String key : fbsArr) {
				if (keyword.contains(key)) {
					keyword = keyword.replace(key, "\\" + key);
				}
			}
		}
		return keyword;
	}

}
