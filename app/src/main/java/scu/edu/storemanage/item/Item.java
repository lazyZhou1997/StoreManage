package scu.edu.storemanage.item;

import scu.edu.storemanage.tools.Date;

/**
 * Created by 周秦春 on 2017/4/4. 未测试
 */

public class Item {

    //属性
    private String ID;//商品ID
    private String barCode;//商品条形码
    private String name;//商品名称
    private Date purchaseDate;//商品买入日期
    private Date productDate;//商品生产日期
    private int qualityDate;//商品保质期
    private double costPrice;//商品进价
    private double sellingPrice;//商品售价
    private double quantity;//商品数量

    //方法

    /**
     *传入除商品参数构建一个Item对象
     * @param ID
     * @param barCode
     * @param name
     * @param purchaseDate
     * @param productDate
     * @param qualityDate
     * @param costPrice
     * @param sellingPrice
     * @param quantity
     */
    public Item(String ID,String barCode,String name,Date purchaseDate,Date productDate,int qualityDate,
                double costPrice,double sellingPrice,double quantity){
        this.ID = ID;
        this.barCode = barCode;
        this.name = name;
        this.purchaseDate = purchaseDate;
        this.productDate = productDate;
        this.qualityDate = qualityDate;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
    }

    /**
     * 获得商品ID
     * @return 商品ID
     */
    public String getID() {
        return ID;
    }

    /**
     * 设置商品ID
     * @param ID 商品ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * 返回商品条形码
     * @return 商品条形码
     */
    public String getBarCode() {
        return barCode;
    }

    /**
     * 设置商品条形码
     * @param barCode 商品条形码
     */
    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    /**
     * 获取商品名称
     * @return 商品名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置的商品名称
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取商品的进货日期
     * @return 进货日期
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * 设置商品的进货日期
     * @param purchaseDate 进货日期
     */
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * 获取商品的生产日期
     * @return 生产日期
     */
    public Date getProductDate() {
        return productDate;
    }

    /**
     * 设置商品的生产日期
     * @param productDate 生产日期
     */
    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    /**
     * 获取商品的保质期
     * @return 保质期
     */
    public int getQualityDate() {
        return qualityDate;
    }

    /**
     * 设置商品的保质期
     * @param qualityDate 保质期
     */
    public void setQualityDate(int qualityDate) {
        this.qualityDate = qualityDate;
    }

    /**
     * 获取商品进价
     * @return 商品进价
     */
    public double getCostPrice() {
        return costPrice;
    }

    /**
     * 设置商品进价
     * @param costPrice 商品进价
     */
    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    /**
     * 获取商品售价
     * @return 商品售价
     */
    public double getSellingPrice() {
        return sellingPrice;
    }

    /**
     * 设置商品售价
     * @param sellingPrice 商品售价
     */
    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    /**
     * 获取商品数量
     * @return 商品数量
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * 设置商品数量
     * @param quantity 商品数量
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * 通过比较商品的条形码判断商品是否相同
     * @param obj 要与本商品比较的商品
     * @return 相同为true，不同为false
     */
    @Override
    public boolean equals(Object obj) {

        //判断是否是商品对象
        if (!(obj instanceof Item)){
            return false;
        }

        //判断商品条形码是否相同
        return (( Item )obj).getBarCode().equals(barCode);

    }

    /**
     * 计算该商品的盈利
     * @return 商品盈利=单个商品利润×商品数量
     */
    public double countProfit(){
        //（售价-进价）×12
        return (sellingPrice-costPrice)*quantity;
    }

    /**
     * 计算商品的保质期到期日期
     * @return 商品保质期到期日期
     */
    public Date countOverdueDate(){
        //年份 = 生产日期年份+保质期/12
        int year = productDate.getYear()+qualityDate/12;
        //月份 = 生产日期月份+保质期%12
        int month = productDate.getMonth()+qualityDate%12;
        //某日 = 生产日期的某日
        int day = productDate.getDay();

        //构造日期对象，用于返回
        Date date = new Date(year,month,day);

        return  date;
    }

    /**
     * 计算商品总共花费的出现成本
     * @return 总成本
     */
    public double countTotalCostPrice(){

        return costPrice*quantity;
    }
}
