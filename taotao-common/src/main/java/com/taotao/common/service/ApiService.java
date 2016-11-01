package com.taotao.common.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.httpclient.HttpResult;

@Service
public class ApiService implements BeanFactoryAware{
    //在单例对象中如何使用多例对象？？？方法：所使用的多例对象不能注入，而是在每次使用时从容器中获取
//    @Autowired
//    private CloseableHttpClient httpClient;
    
    private BeanFactory beanFactory;
    
    @Autowired(required = false)
    private RequestConfig requestConfig;
    
    /**
     * GET请求地址，响应200，返回响应的内容，响应为404、500返回null 
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String doGet(String url) throws ClientProtocolException, IOException {
        //创建Http GET请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        try {
            //执行请求
            response = getHttpClient().execute(httpGet);
            //判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }finally{
            if (response != null) {
                response.close();
            }
        }
        return null;
    }
    
    /**
     * 带有参数的GET请求
     * 
     * @param url
     * @param params
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws URISyntaxException
     */
    public String doGet(String url, Map<String, String> params) throws ClientProtocolException, IOException, URISyntaxException {
        //定义请求的参数
        URIBuilder builder = new URIBuilder(url);
        for (Map.Entry<String, String> param : params.entrySet()) {
            builder.setParameter(param.getKey(), param.getValue());
        }
        return this.doGet(builder.build().toString());
    }
    
    /**
     * 带参数的POST请求
     * 
     * @param url
     * @param params
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public HttpResult doPost(String url, Map<String, String> params) throws ClientProtocolException, IOException{
        //创建Http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);//请求配置
        //请求参数
        if (null != params) {
            List<NameValuePair> parameters = new ArrayList<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            //构造一个form表单式的实体
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
            //将实体设置到httpPost对象中
            httpPost.setEntity(formEntity);
        }
        
        CloseableHttpResponse response = null;
        try {
            response = getHttpClient().execute(httpPost);
            return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), "UTF-8"));
        }finally{
            if (response != null) {
                response.close();
            }
        }
    }
    
    /**
     * 带参数的POST请求，参数类型为JSON字符串
     * 
     * @param url
     * @param json
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public HttpResult doPostJson(String url, String json) throws ClientProtocolException, IOException{
        //创建Http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);//请求配置
        //请求参数
        if (null != json) {
            //构造一个form表单式的实体
            StringEntity formEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            //将实体设置到httpPost对象中
            httpPost.setEntity(formEntity);
        }
        
        CloseableHttpResponse response = null;
        try {
            response = getHttpClient().execute(httpPost);
            return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), "UTF-8"));
        }finally{
            if (response != null) {
                response.close();
            }
        }
    }
    
    /**
     * 不带参数的POST请求
     * 
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public HttpResult doPost(String url) throws ClientProtocolException, IOException{
        return this.doPost(url, null);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        //该方法是在Spring容器初始化时会调用该方法，传人BeanFactory
        this.beanFactory = beanFactory;
    }
    
    private CloseableHttpClient getHttpClient(){
        return this.beanFactory.getBean(CloseableHttpClient.class);
    }
}
