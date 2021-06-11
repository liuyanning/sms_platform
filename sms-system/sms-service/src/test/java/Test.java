public class Test {


	public static boolean isEmoji(char ch) {
		return !((ch == 0x0) || (ch == 0x9) || (ch == 0xA) || (ch == 0xD) || ((ch >= 0x20) && (ch <= 0xD7FF))
				|| ((ch >= 0xE000) && (ch <= 0xFFFD)) || ((ch >= 0x10000) && (ch <= 0x10FFFF)));
	}

	public static boolean containsEmoji(String content) {
		for (int i = 0; i < content.length(); i++) {
			if (isEmoji(content.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
//		Input input=new Input();
//		EnterpriseUser user=new EnterpriseUser();
//		user.setAllow_Begin_Time(DateTime.getDate("1970-01-01 01:00:00"));
//		user.setAllow_End_Time(DateTime.getDate("1970-01-01 04:00:00"));
//		timerSendMq(input,user);
		//String str = "中国";
		//System.out.println(containsEmoji(str));
		//DefaultMQProducer defaultMQProducer=new DefaultMQProducer();
		//defaultMQProducer.get
	}
}
