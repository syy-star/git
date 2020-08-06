package com.fh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fh.TestMapper.OrderFromMapper;
import com.fh.TestMapper.WeChatMapper;
import com.fh.commons.enums.PayStatusEnum;
import com.fh.model.OrderFrom;
import com.fh.service.WeChatService;
import com.github.wxpay.sdk.FeiConfig;
import com.github.wxpay.sdk.WXPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeChatServiceImpl implements WeChatService {

    @Autowired
    private WeChatMapper weChatMapper;

    @Autowired
    private OrderFromMapper orderFromMapper;

    @Override
    public Map addWeChat(Integer orderId) throws Exception {
        //创建一个map讲对象放到map中
        Map rs=new HashMap();
        OrderFrom order=orderFromMapper.selectById(orderId);
        FeiConfig config = new FeiConfig();
        //得到微信支付对象
        WXPay wxpay = new WXPay(config);
        //设置请求参数
        Map<String, String> data = new HashMap<String, String>();
        //对订单信息描述
        data.put("body", "飞狐电商666-订单支付");
        //String payId = System.currentTimeMillis()+"";
        //设置订单号 （保证唯一 ）
        data.put("out_trade_no","weixin1_order_"+2134565433);
        //设置币种
        data.put("fee_type", "CNY");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        Date d=new Date();
        String dateStr = sdf.format(new Date(d.getTime() + 120000000));
        //设置二维码的失效时间
        data.put("time_expire", dateStr);
        //设置订单金额   单位分
        data.put("total_fee","1");
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        //设置支付方式
        data.put("trade_type", "NATIVE");// 此处指定为扫码支付
        // 统一下单
        Map<String, String> resp = wxpay.unifiedOrder(data);
        //用log4fj做记录
        System.out.println("下订单结果为:"+ JSONObject.toJSONString(resp));
        if("SUCCESS".equalsIgnoreCase(resp.get("return_code"))&&"SUCCESS".equalsIgnoreCase(resp.get("result_code"))){
            rs.put("code",200);
            rs.put("url",resp.get("code_url"));
            //更新订单状态
            order.setPayStatus(PayStatusEnum.PAY_STATUS_ING.getStatus());
            orderFromMapper.updateById(order);
        }else {
            rs.put("code",600);
            rs.put("info",resp.get("return_msg"));
        }
        //最后返回map到前台
        return rs;
    }

    @Override
    public Integer selectStatus(Integer orderId) throws Exception {
        FeiConfig config = new FeiConfig();
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no","weixin1_order_syy_"+orderId);
        // 查询支付状态
        Map<String, String> resp = wxpay.orderQuery(data);
        System.out.println("查询结果："+JSONObject.toJSONString(resp));
        if ("SUCCESS".equalsIgnoreCase(resp.get("return_code"))||"SUCCESS".equalsIgnoreCase(resp.get("result_code"))){
            if ("SUCCESS".equalsIgnoreCase(resp.get("trade_state"))){
                //更新订单状态 1 支付成功  2 支付中  3 未支付
                OrderFrom order=new OrderFrom();
                order.setId(orderId);
                order.setPayStatus(PayStatusEnum.PAY_STATUS_SUCCESS.getStatus());
                orderFromMapper.updateById(order);
                return 1;
            }else if("NOTPAY".equalsIgnoreCase(resp.get("trade_state"))){
                return 3;
            }else if("USERPAYING".equalsIgnoreCase(resp.get("trade_state"))){
                return 2;
            }
        }
        return 0;
    }

}
