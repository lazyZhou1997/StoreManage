package scu.edu.storemanage.tools;

import android.text.format.Time;

/**
 * Created by 周秦春 on 2017/4/4. 未测试
 */

public class Date {

    //属性
    private int year;//年
    private int month;//月
    private int day;//日

    //方法

    /**
     * 通过传入年、月、日构造一个Date对象
     *
     * @param year  年
     * @param month 月
     * @param day   日
     */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * 传入特定的字符串，创建Date对象：字符串类型“年_月_日”
     * @param stDate 传入的字符串类型
     */
    public Date(String stDate){
        //将字符串解析
        String[] dates = stDate.split("-");

        this.year = Integer.parseInt(dates[0]);
        this.month = Integer.parseInt(dates[1]);
        this.day = Integer.parseInt(dates[2]);

    }

    /**
     * 获得当前日
     *
     * @return 当前日
     */
    public int getDay() {
        return day;
    }

    /**
     * 设置当前日
     *
     * @param day 当前日
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * 获得当前月
     *
     * @return 当前月
     */
    public int getMonth() {
        return month;
    }

    /**
     * 设置当前月
     *
     * @param month 当前月
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * 获得当前年
     *
     * @return 当前年
     */
    public int getYear() {
        return year;
    }

    /**
     * 设置当前年
     *
     * @param year 当前年
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * 判断当前日期与传入日期是否相同。
     *
     * @param obj 传入日期对象
     * @return 如果日期相同返回true, 如果日期不同返回false。
     */
    @Override
    public boolean equals(Object obj) {
        //判断是否都为日期对象
        if (!(obj instanceof Date)) {
            return false;
        }

        //日期相同返回true
        if (((Date) obj).getYear() == year &&
                ((Date) obj).getMonth() == month &&
                ((Date) obj).getDay() == day) {
            return true;
        }

        return false;
    }

    /**
     * 以'年_月_日'的形式返回当前日期
     *
     * @return 年_月_日
     */
    @Override
    public String toString() {
        return "" + year + "-" + month + "-" + day;
    }

    /**
     * 返回传入的日期比当前日期多多少天,年按365、月按30天计算
     *
     * @param date 多多少天
     * @return
     */
    public long overDate(Date date) {

        return (date.getYear() - year) * 365 + (date.getMonth() - month) + (date.getDay() - day);
    }

    /**
     * 返回系统当前时间
     * @return 系统当前时间
     */
    public static String getCurrentTime(){

        //取得系统的当前日期
        Time time = new Time();
        time.setToNow();
        String currentDate = time.year+"-"+(time.month+1)+"-"+time.monthDay;

        return currentDate;
    }
}
