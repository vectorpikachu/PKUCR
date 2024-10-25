package PKUCRProject.PKUCR.backend.Utils;

public class DateUtils {
    /* 接受一个String类型的，给出年、月、日、时、分、秒
     * 形如：2024-05-06 11:11:11
     */

    public static String getYear(String date) {
        return date.substring(0, 4);
    }

    public static String getMonth(String date) {
        return date.substring(5, 7);
    }

    public static String getDay(String date) {
        return date.substring(8, 10);
    }

    public static String getHour(String date) {
        return date.substring(11, 13);
    }

    public static String getMinute(String date) {
        return date.substring(14, 16);
    }

    public static String getSecond(String date) {
        return date.substring(17, 19);
    }

}
