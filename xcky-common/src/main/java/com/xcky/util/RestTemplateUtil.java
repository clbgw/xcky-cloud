package com.xcky.util;

import com.alibaba.fastjson.JSONObject;
import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.model.wx.WxBaseResp;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.SSLContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * rest模板工具类
 *
 * @author lbchen
 */
@Component
@Slf4j
@SuppressWarnings("rawtypes")
public class RestTemplateUtil {
    /**
     * GET请求-直接URL
     *
     * @param url   URL地址
     * @param clazz 返回值类
     * @return 响应返回值
     */
    public ResponseEntity getReqByUrl(String url, Class clazz) {
        return remoteRequest(url, HttpMethod.GET, null, MediaType.TEXT_HTML, null, clazz);
    }

    /**
     * POST请求-表单提交方式
     *
     * @param url    URL地址
     * @param params 请求参数
     * @param clazz  返回值类
     * @return 响应返回值
     */
    public ResponseEntity postReqByForm(String url, Map<String, Object> params, Class clazz) {
        return remoteRequest(url, HttpMethod.POST, params, MediaType.APPLICATION_FORM_URLENCODED, null, clazz);
    }

    /**
     * POST请求-JSON提交方式
     *
     * @param url    URL地址
     * @param params 请求参数
     * @param clazz  返回值类
     * @return 响应返回值
     */
    public ResponseEntity postReqByJson(String url, Map<String, Object> params, Class clazz) {
        return remoteRequest(url, HttpMethod.POST, params, MediaType.APPLICATION_JSON, null, clazz);
    }

    /**
     * GET请求-直接URL附带请求头
     *
     * @param url       URL地址
     * @param clazz     返回值类
     * @param headerArr 请求头参数数组
     * @return 响应返回值
     */
    public ResponseEntity getReqByUrl(String url, Class clazz, String... headerArr) {
        Map<String, String> headerMap = dealHeaderMap(headerArr);
        return remoteRequest(url, HttpMethod.GET, null, MediaType.APPLICATION_FORM_URLENCODED, headerMap, clazz);
    }

    /**
     * POST请求-表单提交方式附带请求头
     *
     * @param url       URL地址
     * @param params    请求参数
     * @param clazz     返回值类
     * @param headerArr 请求头参数数组
     * @return 响应返回值
     */
    public ResponseEntity postReqByForm(String url, Map<String, Object> params, Class clazz, String... headerArr) {
        Map<String, String> headerMap = dealHeaderMap(headerArr);
        return remoteRequest(url, HttpMethod.POST, params, MediaType.APPLICATION_FORM_URLENCODED, headerMap, clazz);
    }

    /**
     * POST请求-JSON提交方式附带请求头
     *
     * @param url       URL地址
     * @param params    请求参数
     * @param clazz     返回值类
     * @param headerArr 请求头参数数组
     * @return 响应返回值
     */
    public ResponseEntity postReqByJson(String url, Map<String, Object> params, Class clazz, String... headerArr) {
        Map<String, String> headerMap = dealHeaderMap(headerArr);
        return remoteRequest(url, HttpMethod.POST, params, MediaType.APPLICATION_JSON, headerMap, clazz);
    }

    /**
     * 拼装请求头
     *
     * @param headerArr 请求头参数数组
     * @return map对象
     */
    private Map<String, String> dealHeaderMap(String... headerArr) {
        if (null == headerArr) {
            return null;
        }
        Integer headerLength = headerArr.length;
        if (headerLength % Constants.TWO == 1) {
            log.error("header参数必须是key-value成对出现: 请求头长度 = " + headerLength);
            return null;
        }
        Map<String, String> headerMap = new HashMap<>(8);
        for (int i = 0; i < headerLength; i = i + Constants.TWO) {
            headerMap.put(headerArr[i], headerArr[i + 1]);
        }
        return headerMap;
    }

    /**
     * 执行退款
     *
     * @param mchId    商户ID
     * @param url      请求URL
     * @param xmlStr   退款参数
     * @param certPath 证书路径
     * @return
     */
    public Map<String, String> doRefund(String mchId, String url, String xmlStr, String certPath) {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try (FileInputStream inputStream = new FileInputStream(new File(certPath));) {
            KeyStore keyStore = KeyStore.getInstance(Constants.PKCS12);
            keyStore.load(inputStream, mchId.toCharArray());
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, mchId.toCharArray())
                    .build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
            HttpPost httpost = new HttpPost(url);
            httpost.setEntity(new StringEntity(xmlStr, Constants.CHARSET));
            response = httpclient.execute(httpost);
            org.apache.http.HttpEntity entity = response.getEntity();
            String jsonStr = EntityUtils.toString(response.getEntity(), Constants.CHARSET);
            System.out.println(jsonStr);
            EntityUtils.consume(entity);

            String contentType = response.getHeaders(Constants.CONTENT_TYPE)[0].getValue();
            if (MediaType.TEXT_XML_VALUE.equals(contentType) || jsonStr.startsWith(Constants.XML_TAG)) {
                return WxPayUtil.xmlToMap(jsonStr);
            } else {
                Map<String, String> resultMap = new HashMap<>(4);
                resultMap.put("result", jsonStr);
                return resultMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (null != httpclient) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 远程请求微信接口
     *
     * @param url    微信API接口URL
     * @param xmlStr XML字符串
     * @param sslsf  SSL连接套接字工厂
     * @return 微信接口返回值
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> remoteRequestForWx(String url, String xmlStr, SSLConnectionSocketFactory sslsf) {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        if (null != sslsf) {
            httpClientBuilder.setSSLSocketFactory(sslsf);
        }
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
        httpPost.setConfig(requestConfig);
        httpPost.addHeader(Constants.CONTENT_TYPE, MediaType.TEXT_XML_VALUE);
        StringEntity postEntity = new StringEntity(xmlStr, Constants.CHARSET);
        httpPost.setEntity(postEntity);
        HttpResponse httpResponse;
        try {
            httpResponse = httpClient.execute(httpPost);
            org.apache.http.HttpEntity httpEntity = httpResponse.getEntity();
            String result = EntityUtils.toString(httpEntity, Constants.CHARSET);
            log.error(result);
            String contentType = httpResponse.getHeaders(Constants.CONTENT_TYPE)[0].getValue();
            if (MediaType.TEXT_XML_VALUE.equals(contentType) || result.startsWith(Constants.XML_TAG)) {
                return WxPayUtil.xmlToMap(result);
            } else if (MediaType.APPLICATION_JSON_VALUE.equals(contentType)) {
                return JSONObject.parseObject(result, Map.class);
            } else {
                Map<String, String> resultMap = new HashMap<>(4);
                resultMap.put("result", result);
                return resultMap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 远程请求方法
     *
     * @param url        请求URL
     * @param httpMethod http方法 {@link org.springframework.http.HttpMethod}
     * @param params     请求参数
     * @param mediaType  媒体类型 {@link org.springframework.http.MediaType}
     * @param headerMap  请求头Map
     * @param clazz      返回值类
     * @return 响应返回值
     */
    @SuppressWarnings("unchecked")
    private ResponseEntity remoteRequest(String url,
                                         HttpMethod httpMethod,
                                         Map<String, Object> params,
                                         MediaType mediaType,
                                         Map<String, String> headerMap,
                                         Class clazz) throws BizException {
        HttpHeaders headers = new HttpHeaders();
        if (null == httpMethod) {
            httpMethod = HttpMethod.GET;
        }
        if (null == mediaType) {
            mediaType = MediaType.APPLICATION_FORM_URLENCODED;
        }
        if (null != headerMap) {
            for (String headerKey : headerMap.keySet()) {
                if (!StringUtils.isEmpty(headerKey)) {
                    headers.set(headerKey, headerMap.get(headerKey));
                }
            }
        }
        headers.setContentType(mediaType);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params, headers);
        // 执行HTTP请求
        ResponseEntity response = null;
        try {
            response = getRestTemplate().exchange(url, httpMethod, requestEntity, clazz);
        } catch (RestClientException e) {
            throw new BizException(ResponseEnum.SERVER_ERROR, "REST REQ ERROR: url = " + url + ", errMsg = " + e.getMessage());
        }
        judgeResp(response);
        return response;
    }

    /**
     * 获取RestTemplate 对象
     *
     * @return restTemplate对象
     */
    private RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    /**
     * 判断响应对象是否为正确的状态码
     *
     * @param responseEntity 响应实体类
     */
    public void judgeResp(ResponseEntity responseEntity) {
        if (null == responseEntity) {
            return;
        }
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            throw new BizException(ResponseEnum.NETWORK_ERROR, "网络请求错误");
        }
        Object obj = responseEntity.getBody();
        WxBaseResp wxBaseResp = JSONObject.parseObject(String.valueOf(obj), WxBaseResp.class);
        if(null != wxBaseResp && null != wxBaseResp.getErrcode()) {
            if (wxBaseResp.getErrcode() == 48001) {
                throw new BizException(ResponseEnum.API_UNAUTH, null);
            } else if (wxBaseResp.getErrcode() == 40001) {
                throw new BizException(ResponseEnum.TOKEN_IS_INVALID, null);
            }
        }
    }

    /**
     * 获取小程序二维码图片
     *
     * @param url       请求微信服务器的URL
     * @param paramJson 入参
     * @param imgPath   图片路径
     * @return 图片路径
     */
    public String getWxMiniQrcode(String url, String paramJson, String imgPath) {
        InputStream inputStream;
        byte[] data;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.addHeader(Constants.CONTENT_TYPE, "application/json; charset=utf-8");
        httppost.setHeader(Constants.ACCEPT, Constants.JSON_APPLICATION);
        try {
            StringEntity s = new StringEntity(paramJson, Charset.forName(Constants.CHARSET));
            s.setContentEncoding(Constants.CHARSET);
            httppost.setEntity(s);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                response.getEntity();
                org.apache.http.HttpEntity entity = response.getEntity();
                if (entity != null) {
                    inputStream = entity.getContent();
                    data = readInputStream(inputStream);
                } else {
                    return "";
                }
                String contentTypeValue = entity.getContentType().getValue();
                if (contentTypeValue.startsWith(Constants.JSON_APPLICATION)) {
                    return new String(data);
                }
                byte2image(data, imgPath);
                return imgPath;
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 将字节数组转化为图片
     *
     * @param data
     * @param path
     */
    private void byte2image(byte[] data, String path) {
        File imageFile = new File(path);
        //创建输出流
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(imageFile);
            //写入数据
            outStream.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将流保存为数据数组
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    private static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}
