package org.sms.action;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.math.NumberUtils;

public class ImportBalck {
	public static void main(String[] args) throws Exception {
		String path="D:\\black";
		File[] files=new File(path).listFiles();
		Arrays.asList(files).forEach(item->{
			System.out.println(item.getAbsolutePath());
		});
	}

	public static void partFile() throws Exception {
		int filecount = 1;
		while (true) {
			List<String> phoneNos = Files.lines(Paths.get("D:\\black\\putong.txt"), StandardCharsets.UTF_8)
					.skip((filecount - 1) * 1000000).limit(1000000).filter(item -> {
						if (item.length() != 11) {
							return true;
						}
						return !NumberUtils.isDigits(item);
					}).map(item -> item.replace("	", "")).collect(Collectors.toList());
			if (phoneNos.isEmpty()) {
				break;
			}
			Files.write(Paths.get("D:\\black\\" + filecount + ".txt"), phoneNos, Charset.forName("utf-8"),
					StandardOpenOption.CREATE);
			filecount++;
		}
	}
}
