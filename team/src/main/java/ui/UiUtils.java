package ui;

import org.apache.commons.lang.StringUtils;

public class UiUtils {

    public static boolean isSmallLength(String name, String fieldName, int length) {

        if (name == null) {
            return true;
        }

        if (length < name.length()) {
            System.out.printf( "%s는%s문자이하로 입력해주세요.%n", fieldName, length);
            return false;
        }
        return true;
    }

    public static boolean isNumeric(String str, String fieldName) {
        // 숫자인지?
        if (!StringUtils.isNumeric(str)) {
            System.out.printf("%s는 숫자로 입력해주세요.%n", fieldName);
            return false;
        }
        return true;
    }

}