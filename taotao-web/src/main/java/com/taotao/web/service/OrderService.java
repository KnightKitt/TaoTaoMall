package com.taotao.web.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.httpclient.HttpResult;
import com.taotao.common.service.ApiService;
import com.taotao.web.bean.Order;

@Service
public class OrderService {
    
    @Autowired
    private ApiService apiService;
    
    @Value("${TAOTAO_ORDER_URL}")
    private String TAOTAO_ORDER_URL;
    
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 提交订单到订单系统
     * 
     * @param order
     * @return
     */
    public String submit(Order order) {
        String url = TAOTAO_ORDER_URL + "/order/create";
        
        try {
            String json = MAPPER.writeValueAsString(order);
            HttpResult httpResult = this.apiService.doPostJson(url, json);
            if (httpResult.getCode() == 200) {
                String body = httpResult.getBody();
                JsonNode jsonNode = MAPPER.readTree(body);
                if (jsonNode.get("status").asInt() == 200) {
                    //提交成功
                    return jsonNode.get("data").asText();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Order queryByOrderId(String orderId) {
        String url = TAOTAO_ORDER_URL + "/order/query/" + orderId;
        try {
            String jsonData = this.apiService.doGet(url);
            if (StringUtils.isNotEmpty(jsonData)) {
                return MAPPER.readValue(jsonData, Order.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
