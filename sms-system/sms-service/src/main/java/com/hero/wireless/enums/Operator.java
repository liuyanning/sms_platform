package com.hero.wireless.enums;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * @author volcano
 * @date 2019年9月13日下午10:36:21
 * @version V1.0
 */
public enum Operator {
	/**未知*/
	UNKNOW,
	/** 中国移动 */
	CHINA_MOBILE,
	/** 中国联通 */
	CHINA_UNICOM,
	/** 中国电信 */
	CHINA_TELECOM,
	/** 美国 */
	USA,
	/** 加拿大 */
	CANADA,
	/** 俄罗斯 */
	RUSSIA,
	/** 哈萨克斯坦 */
	KAZAKHSTAN,
	/** 埃及 */
	EGYPT,
	/** 南非 */
	SOUTH_AFRICA,
	/** 希腊 */
	GREECE,
	/** 荷兰 */
	NETHERLANDS,
	/** 比利时 */
	BELGIUM,
	/** 法国 */
	FRANCE,
	/** 圣皮埃尔和密克隆 */
	ST_PIERRE_AND_MIQUELON,
	/** 西班牙 */
	SPAIN,
	/** 匈牙利 */
	HUNGRAY,
	/** 意大利 */
	ITALY,
	/** 罗马尼亚 */
	ROMANIA,
	/** 瑞士 */
	SWITZERLAND,
	/** 奥地利 */
	AUSTRIA,
	/** 英国 */
	UNITED_KIONGDOM,
	/** 丹麦 */
	DENMARK,
	/** 瑞典 */
	SWEDEN,
	/** 挪威 */
	NORWAY,
	/** 波兰 */
	POLAND,
	/** 德国 */
	GERMANY,
	/** 秘鲁 */
	PERU,
	/** 墨西哥 */
	MEXICO,
	/** 古巴 */
	CUBA,
	/** 阿根廷 */
	ARGENTINA,
	/** 巴西 */
	BRAZIL,
	/** 智利 */
	CHILE,
	/** 哥伦比亚 */
	COLOMBIA,
	/** 委内瑞拉 */
	VENEZUELA,
	/** 马来西亚 */
	MALAYSIA,
	/** 澳大利亚 */
	AUSTRALIA,
	/** 诺福克群岛 */
	NORFOLK_IS,
	/** 印度尼西亚 */
	INDONESIA,
	/** 菲律宾 */
	PHILIPPINES,
	/** 新西兰 */
	NEW_ZEALAND,
	/** 新加坡 */
	SINGAPORE,
	/** 泰国 */
	THAILAND,
	/** 日本 */
	JAPAN,
	/** 韩国 */
	KOREA,
	/** 越南 */
	VIETNAM,
	/** 中国 */
	CHINA,
	/** 土耳其 */
	TURKY,
	/** 印度 */
	INDIA,
	/** 巴基斯坦 */
	PAKISTAN,
	/** 阿富汗 */
	AFGHANISTAN,
	/** 斯里兰卡 */
	SRILANKA,
	/** 缅甸 */
	MYANMAR,
	/** 伊朗 */
	IRAN,
	/** 南苏丹 */
	SOUTH_SUDAN,
	/** 摩洛哥 */
	MOROCCO,
	/** 阿尔及利亚 */
	ALGERIA,
	/** 突尼斯 */
	TUNISIA,
	/** 利比亚 */
	LIBYA,
	/** 冈比亚 */
	GAMBIA,
	/** 塞内加尔 */
	SENEGAL,
	/** 毛里塔尼亚 */
	MAURITANIA,
	/** 马里 */
	MALI,
	/** 几内亚代表 */
	GUINEA_REP,
	/** 科特迪瓦 */
	IVORY_COAST,
	/** 布基纳法索 */
	BURKINA_FASO,
	/** 尼日尔 */
	NIGER,
	/** 多哥 */
	TOGO,
	/** 贝宁 */
	BENIN,
	/** 毛里求斯 */
	MAURITIUS,
	/** 利比里亚 */
	LIBERIA,
	/** 塞拉利昂 */
	SIERRA_LEONE,
	/** 加纳 */
	GHANA,
	/** 尼日利亚 */
	NIGERIA,
	/** 乍得 */
	CHAD,
	/** 中非共和国 */
	CENTRAL_AFRICAN_REPUBLIC,
	/** 喀麦隆 */
	CAMEROON,
	/** 佛得角群岛 */
	CAPE_VERDE_IS,
	/** 圣多美和普林西比 */
	SAO_TOME_AND_PRINCIPE,
	/** 赤道几内亚 */
	EQUATORIAL_GUINEA,
	/** 加蓬 */
	GABON,
	/** 刚果 */
	CONGO,
	/** 刚果金 */
	CONGO_REP,
	/** 刚果民主共和国 */
	DEMOCRATIC_REP_OF_THE_CONGO,
	/** 安哥拉 */
	ANGOLA,
	/** 几内亚比绍 */
	GUINEA_BISSAU,
	/** 塞舌尔 */
	SEYCHELLES,
	/** 苏丹 */
	SUDAN,
	/** 卢旺达 */
	RWANDA,
	/** 埃塞俄比亚 */
	ETHIOPIA,
	/** 索马里 */
	SOMALIA,
	/** 吉布提 */
	DJIBOUTI,
	/** 肯尼亚 */
	KENYA,
	/** 坦桑尼亚 */
	TANZANIA,
	/** 乌干达 */
	UGANDA,
	/** 布隆迪 */
	BURUNDI,
	/** 莫桑比克 */
	MOZAMBIQUE,
	/** 赞比亚 */
	ZAMBIA,
	/** 马达加斯加 */
	MADAGASCAR,
	/** 留尼汪岛 */
	REUNION,
	/** 津巴布韦 */
	ZIMBABWE,
	/** 纳米比亚 */
	NAMIBIA,
	/** 马拉维 */
	MALAWI,
	/** 莱索托 */
	LESOTHO,
	/** 博茨瓦纳 */
	BOTSWANA,
	/** 斯威士兰 */
	SWAZILAND,
	/** 科摩罗 */
	COMOROS,
	/** 厄立特里亚 */
	ERITREA,
	/** 阿鲁巴 */
	ARUBA,
	/** 法罗群岛 */
	FAROE_IS,
	/** 格陵兰 */
	GREENLAND,
	/** 直布罗陀 */
	GIBRALTAR,
	/** 葡萄牙 */
	PORTUGAL,
	/** 卢森堡 */
	LUXEMBOURG,
	/** 爱尔兰 */
	IRELAND,
	/** 冰岛 */
	ICELAND,
	/** 阿尔巴尼亚 */
	ALBANIA,
	/** 马耳他 */
	MALTA,
	/** 塞浦路斯 */
	CYPRUS,
	/** 芬兰 */
	FINLAND,
	/** 保加利亚 */
	BULGARIA,
	/** 立陶宛 */
	LITHUANIA,
	/** 拉脱维亚 */
	LATVIA,
	/** 爱沙尼亚 */
	ESTONIA,
	/** 海地 */
	HAITI,
	/** 摩尔多瓦 */
	MOLDOVA,
	/** 亚美尼亚 */
	ARMENIA,
	/** 白俄罗斯 */
	BELARUS,
	/** 安道尔 */
	ANDORRA,
	/** 摩纳哥 */
	MONACO,
	/** 圣马力诺 */
	SAN_MARINO,
	/** 乌克兰 */
	UKRAINE,
	/** 塞尔维亚 */
	SERBIA,
	/** 黑山 */
	MONTENEGRO,
	/** 克罗地亚 */
	CROATIA,
	/** 波斯尼亚和黑塞哥维那 */
	BOSNIA_AND_HERZEGOVINA,
	/** 马其顿 */
	MACEDONIA,
	/** 捷克 */
	CZECH_REPUBLIC,
	/** 斯洛伐克 */
	SLOVAKIA,
	/** 列支敦士登 */
	LIECHTENSTEIN,
	/** 福克兰群岛 */
	FALKLAND_IS,
	/** 伯利兹 */
	BELIZE,
	/** 危地马拉 */
	GUATEMALA,
	/** 萨尔瓦多 */
	EL_SALVADOR,
	/** 洪都拉斯 */
	HONDURAS,
	/** 尼加拉瓜 */
	NICARAGUA,
	/** 哥斯达黎加 */
	COSTA_RICA,
	/** 巴拿马 */
	PANAMA,
	/** 瓜德罗普岛 */
	GUADELOUPE,
	/** 玻利维亚 */
	BOLIVIA,
	/** 圭亚那 */
	GUYANA,
	/** 厄瓜多尔 */
	ECUADOR,
	/** 法属圭亚那 */
	FRENCH_GUIANA,
	/** 巴拉圭 */
	PARAGUAY,
	/** 苏里南 */
	SURINAME,
	/** 乌拉圭 */
	URUGUAY,
	/** 荷属安的列斯 */
	NETHERLANDS_ANTILLES,
	/** 东帝汶 */
	EAST_TIMOR,
	/** 文莱 */
	BRUNEI_DARUSSALAM,
	/** 巴布亚新几内亚 */
	PAPUA_NEW_GUINEA,
	/** 汤加 */
	TONGA,
	/** 所罗门群岛 */
	SOLOMON_ISLANDS,
	/** 瓦努阿图 */
	VANUATU,
	/** 斐济 */
	FIJI,
	/** 帕劳 */
	PALAU,
	/** 库克群岛 */
	COOK_IS,
	/** 美属萨摩亚 */
	AMERICAN_SAMOA,
	/** 西萨摩亚 */
	WESTERN_SAMOA,
	/** 新喀里多尼亚 */
	NEW_CALEDONIA,
	/** 法属波利尼西亚 */
	FRENCH_POLYNESIA,
	/** 密克罗尼西亚 */
	MICRONESIA,
	/** 香港 */
	HONGKONG,
	/** 澳门 */
	MACAO,
	/** 柬埔寨 */
	CAMBODIA,
	/** 老挝 */
	LAOS,
	/** 孟加拉 */
	BANGLADESH,
	/** 台湾 */
	TAIWAN,
	/** 马尔代夫 */
	MALDIVES_IS,
	/** 黎巴嫩 */
	LEBANON,
	/** 约旦 */
	JORDAN,
	/** 叙利亚 */
	SYRIA,
	/** 伊拉克 */
	IRAQ,
	/** 科威特 */
	KUWAIT,
	/** 沙特阿拉伯 */
	SAUDI_ARABIA,
	/** 也门 */
	YEMEN,
	/** 阿曼 */
	OMAN,
	/** 巴勒斯坦 */
	PALESTINE,
	/** 迪拜 */
	DUBAI,
	/** 阿联酋 */
	UAE,
	/** 以色列 */
	ISRAEL,
	/** 巴林 */
	BAHRAIN,
	/** 卡塔尔 */
	QATAR,
	/** 不丹 */
	BHUTAN,
	/** 蒙古 */
	MONGOLIA,
	/** 尼泊尔 */
	NEPAL,
	/** 塔吉克斯坦 */
	TAJIKISTAN,
	/** 土库曼斯坦 */
	TURKMENISTAN,
	/** 阿塞拜疆 */
	AZERBAIJAN,
	/** 格鲁吉亚 */
	GEORGIA,
	/** 吉尔吉斯斯坦 */
	KYRGYZ_PUBLIC,
	/** 乌兹别克斯坦 */
	UZBEKISTAN,
	/** 巴哈马 */
	BAHAMAS,
	/** 安提瓜 */
	ANTIGUA,
	/** 英属维尔京群岛 */
	BRITISH_VIRGIN_IS,
	/** 美属维京群岛 */
	US_VIRGIN_IS,
	/** 百慕大 */
	BERMUDA,
	/** 特克斯和凯科斯群岛 */
	TURKS_AND_CAICOS_IS,
	/** 关岛 */
	GUAM,
	/** 波多黎各 */
	PUERTO_RICO,
	/** 特立尼达和多巴哥 */
	TRINIDAD_AND_TOBAGO,
	/** 牙买加 */
	JAMAICA,
	/** 多米尼加共和国 */
	DOMINICAN_REPUBLIC,
	/** 斯洛文尼亚 */
	SLOVENIA,
	/***/
	;
	private String value;

	private Operator() {
		this.value = this.name().toLowerCase();
	}

	private Operator(String value) {
		this.value = value.toLowerCase();
	}

	public String toString() {
		return value;
	}
	public static void main(String[] args) {
		List<Operator> list=Arrays.asList(Operator.values());
		list.forEach(item->{
			System.out.println(item.name()+"	"+item);
		});
	}
	public boolean equals(String value) {
		if (StringUtils.isEmpty(value)) {
			return false;
		}
		return this.value.equalsIgnoreCase(value);
	}
}
