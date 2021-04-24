package com.xcky.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 账单计算工具类
 *
 * @author lbchen
 */
public class OrderComputUtil {
    public static void main(String[] args) {
        LocalDate startDate = LocalDate.of(2020, 10, 30);
        LocalDate endDate = LocalDate.of(2022, 1, 1);
        List<PaymentMethod> paymentMethodList = new ArrayList<>();
        paymentMethodList.add(new PaymentMethod("支付宝", 10, 0, 20));
        paymentMethodList.add(new PaymentMethod("交通银行", 25, 1, 19));
        paymentMethodList.add(new PaymentMethod("建设银行", 5, 0, 25));
        
        dealOrder(startDate, endDate, paymentMethodList);
    }
    
    private static void dealOrder(LocalDate startDate, LocalDate endDate, List<PaymentMethod> paymentMethodList) {
        if (!startDate.isBefore(endDate)) {
            System.out.println("请输入正确的日期");
            return;
        }
        int paymentMethodSize = paymentMethodList.size();
        if (paymentMethodSize < 1) {
            System.out.println("请选择支付方式");
            return;
        }
        
        LocalDate[] localDates = new LocalDate[paymentMethodSize];
        String[] cardNames = new String[paymentMethodSize];
        for (int i = 0; i < paymentMethodSize; i++) {
            PaymentMethod paymentMethod = paymentMethodList.get(i);
            cardNames[i] = paymentMethod.getName();
        }
        
        List<OrderDate> orderDateList = new ArrayList<>();
        OrderDate orderDate = null;
        Integer recordIndex = 0;
        while (startDate.isBefore(endDate)) {
            for (int i = 0; i < paymentMethodSize; i++) {
                PaymentMethod paymentMethod = paymentMethodList.get(i);
                localDates[i] = orderComput(startDate, paymentMethod.getOutDate(), paymentMethod.getCurMonth(), paymentMethod.getRefundDate());
            }
            
            LocalDate maxDate = localDates[0];
            int cardNameIndex = 0;
            for (int i = 1; i < localDates.length ; i++) {
                if (maxDate.isBefore(localDates[i])) {
                    cardNameIndex = i;
                    maxDate = localDates[i];
                }
            }
            
            if (null == orderDate) {
                orderDate = new OrderDate();
                orderDate.setName(cardNames[cardNameIndex]);
                orderDate.setLocalDate(maxDate);
                orderDate.setComoutDate(startDate);
                orderDate.setEndDate(startDate);
                orderDateList.add(orderDate);
                recordIndex++;
            } else {
                if (!cardNames[cardNameIndex].equals(orderDate.getName())) {
                    OrderDate orderDateNew = new OrderDate();
                    orderDateNew.setName(cardNames[cardNameIndex]);
                    orderDateNew.setLocalDate(maxDate);
                    orderDateNew.setComoutDate(startDate);
                    orderDateNew.setEndDate(startDate);
                    orderDateList.add(orderDateNew);
                    recordIndex++;
                    orderDate = orderDateNew;
                } else {
                    if (recordIndex > 0) {
                        orderDateList.get(recordIndex - 1).setEndDate(startDate);
                    }
                }
            }
            startDate = startDate.plusDays(1);
            ;
        }
        
        for (OrderDate od : orderDateList) {
            System.out.println("------------------------------------");
            System.out.println(od.toString());
        }
        System.out.println("------------------------------------");
    }
    
    @Getter
    @Setter
    @AllArgsConstructor
    static class PaymentMethod {
        private String name;
        private int outDate;
        private int curMonth;
        private int refundDate;
    }
    
    @Getter
    @Setter
    static class OrderDate {
        private String name;
        private LocalDate localDate;
        private LocalDate comoutDate;
        private LocalDate endDate;
        
        @Override
        public String toString() {
            return "支付方式:" + name + ", 还款日:" + localDate + ", 开始日期:" + comoutDate + ", 结束日期:" + endDate;
        }
    }
    
    /**
     * 计算当前日期的还款日
     *
     * @param localDate  当前日期
     * @param outDate    出账日
     * @param curMonth   当月为0,次月为1
     * @param refundDate 还款日
     * @return 下一个还款日
     */
    private static LocalDate orderComput(LocalDate localDate, int outDate, int curMonth, int refundDate) {
        int day = localDate.getDayOfMonth();
        if (day < outDate) {
            localDate = localDate.plusMonths(0 + curMonth);
        } else {
            localDate = localDate.plusMonths(1 + curMonth);
        }
        return LocalDate.of(localDate.getYear(), localDate.getMonthValue(), refundDate);
    }
}
