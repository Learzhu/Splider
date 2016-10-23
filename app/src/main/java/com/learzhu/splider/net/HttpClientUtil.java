package com.learzhu.splider.net;


import android.util.Log;

import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by wz on 2016/04/19.
 */
public class HttpClientUtil {
    public final static String Method_POST = "POST";
    public final static String Method_GET = "GET";

    /**
     * multipart/form-data类型的表单提交
     *
     * @param form 表单数据
     */
    public static String submitForm(MultipartForm form) {
        // 返回字符串
        String responseStr = "";

        // 创建HttpClient实例
/*        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient httpClient = httpClientBuilder.build();*/
        // CloseableHttpClient httpClient= HttpClients.createDefault();

        HttpClient httpClient = new DefaultHttpClient();
        try {
            // 实例化提交请求
            HttpPost httpPost = new HttpPost(form.getAction());

            // 创建MultipartEntityBuilder
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();

            Log.e("HttpClientUtil", "111");
            // 追加普通表单字段
            Map<String, String> normalFieldMap = form.getNormalField();
            for (Iterator<Entry<String, String>> iterator = normalFieldMap.entrySet().iterator(); iterator.hasNext(); ) {
                Entry<String, String> entity = iterator.next();
                entityBuilder.addPart(entity.getKey(), new StringBody(entity.getValue(), ContentType.create("text/plain", Consts.UTF_8)));
            }
            Log.e("HttpClientUtil", "222");
            // 追加文件字段
            Map<String, File> fileFieldMap = form.getFileField();
            for (Iterator<Entry<String, File>> iterator = fileFieldMap.entrySet().iterator(); iterator.hasNext(); ) {
                Entry<String, File> entity = iterator.next();
                entityBuilder.addPart(entity.getKey(), new FileBody(entity.getValue()));
            }
            Log.e("HttpClientUtil", "333");
            // 设置请求实体
            httpPost.setEntity(entityBuilder.build());
            Log.e("HttpClientUtil", "444");
            // 发送请求
            HttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            // 取得响应数据
            HttpEntity resEntity = response.getEntity();
            Log.e("HttpClientUtil", "结果：" + Integer.toString(statusCode));
            if (200 == statusCode) {
                if (resEntity != null) {
                    responseStr = EntityUtils.toString(resEntity);
                }
            }
        } catch (Exception e) {
            System.out.println("提交表单失败，原因：" + e.getMessage());
        } finally {
            try {
                httpClient.getConnectionManager().shutdown();
            } catch (Exception ex) {
            }
        }
        return responseStr;
    }

    /**
     * 表单字段Bean
     */
    public class MultipartForm implements Serializable {
        /**
         * 序列号
         */
        private static final long serialVersionUID = -2138044819190537198L;

        /**
         * 提交URL
         **/
        private String action = "";

        /**
         * 提交方式：POST/GET
         **/
        private String method = "POST";

        /**
         * 普通表单字段
         **/
        private Map<String, String> normalField = new LinkedHashMap<String, String>();

        /**
         * 文件字段
         **/
        private Map<String, File> fileField = new LinkedHashMap<String, File>();

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public Map<String, String> getNormalField() {
            return normalField;
        }

        public void setNormalField(Map<String, String> normalField) {
            this.normalField = normalField;
        }

        public Map<String, File> getFileField() {
            return fileField;
        }

        public void setFileField(Map<String, File> fileField) {
            this.fileField = fileField;
        }

        public void addFileField(String key, File value) {
            fileField.put(key, value);
        }

        public void addNormalField(String key, String value) {
            normalField.put(key, value);
        }
    }


}