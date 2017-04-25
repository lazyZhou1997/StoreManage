package scu.edu.storemanage.tools;

import java.util.Comparator;

import scu.edu.storemanage.item.Item;

/**
 * Created by 周秦春 on 17-4-25.
 */

/**
 * 用于ArrayList中排序，首先根据生产日期排，然后根据购买日期排,生产日期所占权重更大。
 */
public class ItemComparator implements Comparator {
    @Override
    public int compare(Object o, Object t1) {
        //生产日期
        Date itemProductDate1 = ((Item)o).getProductDate();
        Date itemProductDate2 = ((Item)t1).getProductDate();

        //购买日期
        Date itemPurchaseDate1 = ((Item)o).getPurchaseDate();
        Date itemPurchaseDate2 = ((Item)t1).getPurchaseDate();

        if (itemProductDate1.getYear()>itemProductDate2.getYear()){
            return 1;//生产日期-年更大
        }else if (itemProductDate1.getYear()<itemProductDate2.getYear()){
            return -1;//生产日期-年更小
        }else if (itemProductDate1.getMonth()>itemProductDate2.getMonth()){
            return 1;//生产日期-月更大
        }else if (itemProductDate1.getMonth()<itemProductDate2.getMonth()){
            return -1;//生产日期-月更小
        }else if (itemProductDate1.getDay()>itemProductDate2.getDay()){
            return 1;//生产日期-日更大
        }else if (itemProductDate1.getDay()<itemProductDate2.getDay()){
            return -1;//生产日期-日更小
        }else if (itemPurchaseDate1.getYear()>itemPurchaseDate2.getYear()){
            return 1;//购买日期-年更大
        }else if (itemPurchaseDate1.getYear()<itemPurchaseDate2.getYear()){
            return -1;//购买日期-年更小
        }else if (itemPurchaseDate1.getMonth()>itemPurchaseDate2.getMonth()){
            return 1;//购买日期-月更大
        }else if (itemPurchaseDate1.getMonth()<itemPurchaseDate2.getMonth()){
            return -1;//购买日期-月更小
        }else if (itemPurchaseDate1.getDay()>itemPurchaseDate2.getDay()){
            return 1;//购买日期-日更大
        }else if (itemPurchaseDate1.getDay()<itemPurchaseDate2.getDay()){
            return -1;//购买日期-日更小
        }else {
            return 0;//相等则返回0
        }

    }
}
