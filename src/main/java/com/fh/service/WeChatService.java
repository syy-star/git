package com.fh.service;

import java.util.Map;

public interface WeChatService {
    Map addWeChat(Integer orderId) throws Exception;

    Integer selectStatus(Integer orderId) throws Exception;
}
