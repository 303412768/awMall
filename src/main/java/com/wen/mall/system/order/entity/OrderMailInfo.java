package com.wen.mall.system.order.entity;

import lombok.Data;

import java.util.List;

@Data
public class OrderMailInfo {
    private Order order;
    private Address address;
    private List<OrderDetail> orderDetails;
    private String baseImagePath;

    public OrderMailInfo(Order order, Address address, List<OrderDetail> orderDetails) {
        this.order = order;
        this.address = address;
        this.orderDetails = orderDetails;
    }

    public OrderMailInfo(Order order, Address address, List<OrderDetail> orderDetails, String baseImagePath) {
        this.order = order;
        this.address = address;
        this.orderDetails = orderDetails;
        this.baseImagePath = baseImagePath;
    }

    public String geteMailTitle() {
        return "订单编号：" + order.getOrderNo();
    }

    public String getMailContent() {
        StringBuilder builder = new StringBuilder();
        builder.append("<html><body>");
        builder.append("<h5>订单编号：").append(order.getOrderNo()).append("</h5>");
        builder.append("<h5>商品金额：").append(order.getTotal()).append("元</h5>");
        builder.append("<h5>收货人：").append(address.getName()).append("</h5>");
        builder.append("<h5>收货人地址：").append(address.getAddress()).append("</h5>");
        builder.append("<h5>收货人手机号：").append(address.getTel()).append("</h5>");
        builder.append("<h5>收货人身份证：").append(address.getIdentity()).append("</h5>");
        builder.append("<h5>收货人微信号：").append(address.getWxId()).append("</h5>");

        builder.append("<table border=\"1\">");
        builder.append("<tr>");
        builder.append("<td>商品名称</td>");
        builder.append("<td>规格</td>");
        builder.append("<td>快递信息</td>");
        builder.append("<td>图片</td>");
        builder.append("<td>销售价格</td>");
        builder.append("<td>数量</td>");
        builder.append("</tr>");

        orderDetails.forEach(obj->{
            builder.append("<tr>");
            builder.append("<td>").append(obj.getGoodsName()).append("</td>");
            builder.append("<td>").append(obj.getSpecification()).append("</td>");
            builder.append("<td>").append(obj.getPostInfo()).append("</td>");
            builder.append("<td><img width='80px'  src='"+baseImagePath+obj.getMainPic()+"' /></td>");
            builder.append("<td>").append(obj.getPrice()).append("</td>");
            builder.append("<td>").append(obj.getQuantity()).append("</td>");
            builder.append("</tr>");
        });
        builder.append("</table>");

        builder.append("</body></html>");
        return builder.toString();
    }
}
