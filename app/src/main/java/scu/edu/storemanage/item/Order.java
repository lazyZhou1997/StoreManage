package scu.edu.storemanage.item;

import scu.edu.storemanage.tools.Date;

/**
 * Created by Admin on 2017/4/4.
 */

public class Order {

    private int ID;//订单ID
    private Date date;//销售日期
    private double quantity;//销售数量
    private String itemID;//商品ID
    private String customerID;//顾客ID
    private double actsellPrice;//实际销售价格
    private double profit;//此单利润

    /**
     * 构建Order对象
     *
     * @param date
     * @param quantity
     * @param itemID
     * @param customerID
     * @param actsellPrice
     * @param profit
     */
    public Order(int ID, Date date, double quantity, String itemID, String customerID,
                 double actsellPrice, double profit) {
        this.ID = ID;
        this.date = date;
        this.quantity = quantity;
        this.itemID = itemID;
        this.customerID = customerID;
        this.actsellPrice = actsellPrice;
        this.profit = profit;
    }

    /**
     * 获得订单ID
     *
     * @return 订单ID
     */
    public int getID() {
        return ID;
    }

    /**
     * 设置订单ID
     *
     * @param ID 订单ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * 获得订单日期
     *
     * @return 订单日期
     */
    public Date getDate() {
        return date;
    }

    /**
     * 设置订单日期
     *
     * @param date 订单日期
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * 获得订单的商品数量
     *
     * @return 商品数量
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * 设置订单的商品数量
     *
     * @param quantity 商品数量
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * 获得商品ID
     *
     * @return 商品ID
     */
    public String getItemID() {
        return itemID;
    }

    /**
     * 设置订单的商品ID
     *
     * @param itemID 订单的商品ID
     */
    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    /**
     * 获得订单的顾客ID
     *
     * @return 顾客ID
     */
    public String getCustomerID() {
        return customerID;
    }

    /**
     * 设置订单的顾客ID
     *
     * @param customerID 顾客ID
     */
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    /**
     * 获得商品的实际售卖价格
     *
     * @return 实际收买单价
     */
    public double getActsellPrice() {
        return actsellPrice;
    }

    /**
     * 设置订单的商品的实际售卖单价
     *
     * @param actsellPrice 商品的实际售卖单价
     */
    public void setActsellPrice(double actsellPrice) {
        this.actsellPrice = actsellPrice;
    }

    /**
     * 获得此单商品的利润
     *
     * @return 此单商品的利润
     */
    public double getProfit() {
        return profit;
    }

    /**
     * 设置此单商品的利润
     *
     * @param profit 此单商品的利润
     */
    public void setProfit(double profit) {
        this.profit = profit;
    }

    /**
     * 根据订单的ID，判断两个订单是否相同
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {

        //判断传入的是否是Order对象
        if (!(obj instanceof Order)) {
            return false;
        }

        //判断两个订单对象的ID是否相同
        if (((Order) obj).getID() == ID) {

            return true;
        }

        return false;
    }
}
