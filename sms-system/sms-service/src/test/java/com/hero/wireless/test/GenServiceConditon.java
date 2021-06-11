package com.hero.wireless.test;
import java.util.Arrays;

/**
 * 生成基本业务条件
 * 
 * @author volcano
 * @date 2019年9月17日上午4:01:51
 * @version V1.0
 */
public class GenServiceConditon {
	public static String genCriteria(Class<?> classze) {
		char[] instanceNameChars = classze.getSimpleName().toCharArray();
		instanceNameChars[0] += 32;
		String instanceName = String.valueOf(instanceNameChars);
		StringBuilder builder = new StringBuilder();
		String exampleClassName = classze.getSimpleName() + "Example";
		String exampleName = "example";
		builder.append(exampleClassName).append(" ").append(exampleName).append("=").append("new ")
				.append(exampleClassName).append("();\n");
		String criteriaName = "criteria";
		builder.append(exampleClassName).append(".Criteria ").append(criteriaName).append("=").append(exampleName)
				.append(".createCriteria();\n");
		Arrays.asList(classze.getDeclaredFields()).forEach(item -> {
			try {
				char[] filedChars = item.getName().toCharArray();
				filedChars[0] -= 32;
				String filedName = String.valueOf(filedChars);
				if (item.getType() == String.class) {
					builder.append("if (StringUtils.isNotEmpty(").append(instanceName).append(".get").append(filedName)
							.append("())){\n");
					builder.append("	criteria.and").append(filedName).append("EqualTo(").append(instanceNameChars)
							.append(".get").append(filedName).append("());\n");
					builder.append("}\n");
				}
				if (item.getType() == Integer.class) {
					builder.append("if (null!=").append(instanceName).append(".get").append(filedName).append("()){\n");
					builder.append("	criteria.and").append(filedName).append("EqualTo(").append(instanceNameChars)
							.append(".get").append(filedName).append("());\n");
					builder.append("}\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		builder.append("example").append(".setPagination(").append(instanceName).append(".getPagination());");
		return builder.toString();
	}
}
