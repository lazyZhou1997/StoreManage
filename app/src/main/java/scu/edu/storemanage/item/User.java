package scu.edu.storemanage.item;

/**
 * Created by 周秦春 on 2017/4/7.
 */

public class User {

    private String account;//账户
    private String password;//密码
    private String phonenumber = "";//电话号码

    /**
     * 构建User对象
     * @param account 账户
     * @param password 密码
     * @param phonenumber 电话号码
     */
    public User(String account, String password, String phonenumber) {
        this.account = account;
        this.password = password;
        this.phonenumber = phonenumber;
    }

    /**
     * 获得账户
     * @return 账户
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置账户
     * @param account 账户
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获得密码
     * @return 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获得电话
     * @return 电话
     */
    public String getPhonenumber() {
        return phonenumber;
    }

    /**
     * 设置电话
     * @param phonenumber 电话
     */
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    /**
     * 根据账户判断两个账户是否相同
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) {
            return false;
        }

        if (((User) o).getAccount().equals(account)){
            return true;
        }

        return false;
    }
}
