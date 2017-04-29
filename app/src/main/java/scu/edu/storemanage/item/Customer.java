package scu.edu.storemanage.item;

/**
 * Created by 周秦春 on 2017/4/4. 未测试
 */

public class Customer {
    private int ID;//顾客ID
    private String name;//顾客名字
    private String telNumber;//顾客电话
    private String address;//顾客地址
    private double integral;//顾客积分

    /**
     * 通过传入顾客的参数来建立一个顾客对象
     *
     * @param name      顾客名字
     * @param telNumber 顾客电话号码
     * @param address   //顾客地址
     * @param integral  //顾客积分
     */
    public Customer(int ID, String name, String telNumber, String address, double integral) {

        this.ID = ID;
        this.name = name;
        this.telNumber = telNumber;
        this.address = address;
        this.integral = integral;
    }

    /**
     * 获得客户的ID
     *
     * @return 客户ID
     */
    public int getID() {
        return ID;
    }

    /**
     * 设置客户的ID
     *
     * @param ID 客户ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * 获取客户的名字
     *
     * @return 客户的名字
     */
    public String getName() {
        return name;
    }

    /**
     * 设置客户的名字
     *
     * @param name 客户的名字
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获得客户的电话号码
     *
     * @return 客户的电话号码
     */
    public String getTelNumber() {
        return telNumber;
    }

    /**
     * 设置客户的电话码
     *
     * @param telNumber 客户的电话号码
     */
    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    /**
     * 设置客户的地址
     *
     * @return 客户地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置客户的地址
     *
     * @param address 客户的地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获得客户的积分
     *
     * @return 客户的积分
     */
    public double getIntegral() {
        return integral;
    }

    /**
     * 设置客户的积分
     *
     * @param integral 客户的积分
     */
    public void setIntegral(double integral) {
        this.integral = integral;
    }

    /**
     * 判断两个客户是否相同，电话号码相同就相同
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {

        //判断传入的是否都是Customer对象
        if (!(obj instanceof Customer)) {
            return false;
        }

        //判断姓名是否相同，相同即返回true
        if (((Customer) obj).getName().equals(name)) {
            return true;
        }

        return false;
    }
}
