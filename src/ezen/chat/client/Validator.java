package ezen.chat.client;

/**
 * AMS 프로젝트에서 공통적으로 사용하는 유효성 검증 유틸리티 클래스
 * @author 김기정
 * @Date   2023. 1. 26.
 */
public class Validator { 
	
	// 문자열이 Null 이거나 빈문자열인지 검증
	public static boolean isEmpty(String text) {
		return text == null || text.trim().length() == 0;
	}
	
	// 문자열이 숫자형식인지
	public static boolean isNumber(String text) {
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if(!Character.isDigit(c)){
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		System.out.println(isNumber("3400667"));
		System.out.println(isNumber("34-667"));
	}

}








