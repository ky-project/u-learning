package com.ky.ulearning.common.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * HttpClient工具类
 *
 * @author luyuhao
 * @since 2020/3/14 12:16
 */
@Slf4j
public class HttpClientUtil {

    /**
     * 默认30000毫秒
     */
    private final static int SOCKET_TIMEOUT = 30000;

    /**
     * 默认30000毫秒
     */
    private final static int CONNECT_TIMEOUT = 30000;

    /**
     * 连接池，最大连接数
     */
    private final static int POOL_MAX_TOTAL = 600;

    /**
     * 每个路由最大连接数，即每个host的最大连接数
     */
    private final static int POOL_MAX_PER_ROUTE = 300;

    private List<HttpRequestInterceptor> requestInterceptorList = new ArrayList<>();

    private List<HttpResponseInterceptor> responseInterceptorList = new ArrayList<>();

    private HttpClientBuilder builder;

    private CloseableHttpClient httpclient;

    public void init() {
        SSLContext sslContext = null;

        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            //加载客户端证书
            keyStore.load(null, null);
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                /**
                 * 信任所有，忽略对服务端的证书验证
                 */
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
        } catch (KeyStoreException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        } catch (CertificateException e) {
            log.error(e.getMessage(), e);
        } catch (KeyManagementException e) {
            log.error(e.getMessage(), e);
        }
        // ALLOW_ALL_HOSTNAME_VERIFIER:这个主机名证明器基本上是关闭主机名验证，实现的是一个空操作，并且不会抛出javax.net.ssl.SSLException一场
        SSLConnectionSocketFactory sslConnectionSocketFactory =
                new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1"},
                        null, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        //初始化连接池
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(POOL_MAX_TOTAL);
        cm.setDefaultMaxPerRoute(POOL_MAX_PER_ROUTE);

        this.builder = HttpClients.custom().setConnectionManager(cm).setSSLSocketFactory(sslConnectionSocketFactory);
        fillRequestInterceptors(builder);
        fillResponseInterceptors(builder);

        httpclient = builder.build();
    }

    private void fillResponseInterceptors(HttpClientBuilder builder) {
        if (CollectionUtils.isEmpty(requestInterceptorList)) {
            return;
        }
        for (HttpResponseInterceptor httpResponseInterceptor : responseInterceptorList) {
            builder.addInterceptorLast(httpResponseInterceptor);
        }
    }

    private void fillRequestInterceptors(HttpClientBuilder builder) {
        if (CollectionUtils.isEmpty(requestInterceptorList)) {
            return;
        }
        for (HttpRequestInterceptor httpRequestInterceptor : requestInterceptorList) {
            builder.addInterceptorLast(httpRequestInterceptor);
        }
    }

    /**
     * get请求
     *
     * @param uri 请求路径
     */
    public String getForString(String uri) {
        return getForString(uri, "UTF-8");
    }

    private String getForString(String uri, String charSet) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpGet get = new HttpGet(uri);
        try {
            httpClient = getHttpClient(uri);
            response = execute(httpClient, get);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, charSet);
                if (log.isDebugEnabled()) {
                    log.debug("get, uri = " + uri + ", result = " + result);
                }
                return result;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            close(response);
        }
        return null;
    }

    private CloseableHttpResponse execute(CloseableHttpClient client, HttpRequestBase base) {
        //设置超时时间
        setTimeout(base);
        try {
            CloseableHttpResponse response = client.execute(base);
            int status = response.getStatusLine().getStatusCode();

            if (status == HttpStatus.SC_OK) {
                return response;
            } else {
                //错误的响应码
                URI uri = base.getURI();
                if (null != uri) {
                    log.error("HOST:{}, PORT:{}, PATH:{}, STATUS:{}", uri.getHost(), uri.getPort(), uri.getPath(), status);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private void setTimeout(HttpRequestBase base) {
        RequestConfig config = RequestConfig.custom()
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .build();
        base.setConfig(config);
    }

    private CloseableHttpClient getHttpClient(String uri) {
        if (StringUtil.isEmpty(uri)) {
            return null;
        }
        return httpclient;
    }

    private void close(CloseableHttpResponse response) {
        try {
            if (response != null) {
                response.close();
            }
        } catch (IOException e) {
            log.error("HttpResponse关闭失败!", e);
        }
    }

    public List<HttpRequestInterceptor> getRequestInterceptorList() {
        return requestInterceptorList;
    }

    public void setRequestInterceptorList(List<HttpRequestInterceptor> requestInterceptorList) {
        this.requestInterceptorList = requestInterceptorList;
    }

    public List<HttpResponseInterceptor> getResponseInterceptorList() {
        return responseInterceptorList;
    }

    public void setResponseInterceptorList(List<HttpResponseInterceptor> responseInterceptorList) {
        this.responseInterceptorList = responseInterceptorList;
    }

    public void addRequestInterceptorList(HttpRequestInterceptor httpRequestInterceptor) {
        this.requestInterceptorList.add(httpRequestInterceptor);
    }

    public void addResponseInterceptorList(HttpResponseInterceptor httpResponseInterceptor) {
        this.responseInterceptorList.add(httpResponseInterceptor);
    }
}
