package com.aaric.alimobile.helper.network;

import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * HttpClient Helper.
 * 
 * @author Aaric
 * 
 */
public final class HttpClientHelper {

	/**
	 * The singleton of HttpClient helper.
	 */
	private static HttpClientHelper singletonHttpClientHelper;

	/**
	 * Constants.
	 */
	private static final String CONTENT_CHARSET = "http.protocol.content-charset";
	private static final String CONTENT_ACCEPT_LANGUAGE = "Accept-Language";
	private static final String CONTENT_REFERER = "Referer";

	private static final String CONN_DIRECTIVE_VALUE = "keep-alive";
	private static final String CONTENT_ACCEPT_LANGUAGE_VALUE = "zh-CN,zh;q=0.8";
	private static final String CONTENT_TYPE_VALUE = "text/html;charset=UTF-8";
	private static final String CONTENT_ENCODING_VALUE = "gzip";
	private static final String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36";

	public static final int DEFAULT_CONNECTION_TIMEOUT = 5*1000;
	public static final int DEFAULT_SO_TIMEOUT = 15*1000;

	private HttpClient client;

	/**
	 * The private constructor.
	 */
	private HttpClientHelper() {
		super();
		HttpParams params = new BasicHttpParams();
		params.setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, DEFAULT_CONNECTION_TIMEOUT);
		params.setParameter(HttpConnectionParams.SO_TIMEOUT, DEFAULT_SO_TIMEOUT);
		params.setParameter(CONTENT_CHARSET, HTTP.UTF_8);
		SchemeRegistry schreg = new SchemeRegistry();
		schreg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schreg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
		ClientConnectionManager conman = new ThreadSafeClientConnManager(params, schreg);
		client = new DefaultHttpClient(conman, params);
	}

	/**
	 * Static factory methods.
	 * 
	 * @return
	 */
	public synchronized static HttpClientHelper getInstance() {
		if (null == singletonHttpClientHelper) {
			singletonHttpClientHelper = new HttpClientHelper();
		}
		return singletonHttpClientHelper;
	}

	/**
	 * Set proxy of HttpClient.
	 * 
	 * @param hostname
	 *            The proxy of hostname.
	 * @param port
	 *            The proxy of port.
	 */
	public void setProxy(String hostname, int port) {
		HttpHost httpHost = null;
		if (null != hostname) {
			httpHost = new HttpHost(hostname, port);
		}
		client.getParams()
				.setParameter(ConnRouteParams.DEFAULT_PROXY, httpHost);
	}

	/**
	 * Set proxy of HttpClient.
	 * 
	 * @param hostname
	 *            The proxy of hostname.
	 * @param port
	 *            The proxy of port.
	 * @param scheme
	 *            The proxy of scheme.
	 */
	public void setProxy(String hostname, int port, String scheme) {
		HttpHost httpHost = null;
		if (null != hostname) {
			httpHost = new HttpHost(hostname, port, scheme);
		}
		client.getParams()
				.setParameter(ConnRouteParams.DEFAULT_PROXY, httpHost);
	}

	/**
	 * Parse HttpGet object.
	 * 
	 * @param httpGet
	 *            The HttpGet object.
	 * @return
	 */
	private static HttpGet parseHttpGet(HttpGet httpGet) {
		if (null != httpGet) {
			httpGet.setHeader(HTTP.CONN_DIRECTIVE, CONN_DIRECTIVE_VALUE);
			httpGet.setHeader(CONTENT_ACCEPT_LANGUAGE,
					CONTENT_ACCEPT_LANGUAGE_VALUE);
			httpGet.setHeader(CONTENT_REFERER, httpGet.getURI().getHost());
			httpGet.setHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE_VALUE);
			httpGet.setHeader(HTTP.USER_AGENT, USER_AGENT_VALUE);
		}
		return httpGet;
	}

	/**
	 * Get HttpGet object.
	 * 
	 * @param url
	 *            The request address.
	 * @return
	 */
	private static HttpGet parseHttpGet(String url) {
		return parseHttpGet(new HttpGet(url));
	}

	/**
	 * Get network resource by GET.
	 * 
	 * @param url
	 *            The request address.
	 * @return
	 */
	public String doGet(String url) {
		String result = null;
		HttpGet httpGet = null;
		try {
			httpGet = parseHttpGet(url);
			HttpResponse response = client.execute(httpGet);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				result = EntityUtils.toString(response.getEntity());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != httpGet) {
				httpGet.abort();
			}
		}
		return result;
	}

	/**
	 * Get network resource by GET with InputStream.
	 * 
	 * @param url
	 *            The request address.
	 * @return
	 */
	public InputStream doInputStreamGet(String url) {
		InputStream result = null;
		HttpGet httpGet = null;
		try {
			httpGet = parseHttpGet(url);
			HttpResponse response = client.execute(httpGet);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				result = response.getEntity().getContent();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != httpGet) {
				httpGet.abort();
			}
		}
		return result;
	}

	/**
	 * Parse HttpPost object.
	 * 
	 * @param httpPost
	 *            The HttpPost object.
	 * @return
	 */
	private static HttpPost parseHttpPost(HttpPost httpPost) {
		if (null != httpPost) {
			httpPost.setHeader(HTTP.CONN_DIRECTIVE, CONN_DIRECTIVE_VALUE);
			httpPost.setHeader(CONTENT_ACCEPT_LANGUAGE,
					CONTENT_ACCEPT_LANGUAGE_VALUE);
			httpPost.setHeader(CONTENT_REFERER, httpPost.getURI().getHost());
			httpPost.setHeader(HTTP.CONTENT_ENCODING, CONTENT_ENCODING_VALUE);
			httpPost.setHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE_VALUE);
			httpPost.setHeader(HTTP.USER_AGENT, USER_AGENT_VALUE);
		}
		return httpPost;
	}

	/**
	 * Get HttpPost object.
	 * 
	 * @param url
	 *            The request address.
	 * @return
	 */
	private static HttpPost parseHttpPost(String url) {
		return parseHttpPost(new HttpPost(url));
	}

	/**
	 * Get network resource by POST.
	 * 
	 * @param url
	 *            The request address.
	 * @param args
	 *            The form parameters to submit.
	 * @return
	 */
	public String doPost(String url, Map<String, Object> args) {
		String result = null;
		HttpPost httpPost = null;
		try {
			httpPost = parseHttpPost(url);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if (null != args && 0 != args.size()) {
				Iterator<String> argsIterator = args.keySet().iterator();
				while (argsIterator.hasNext()) {
					String mapkey = argsIterator.next();
					nvps.add(new BasicNameValuePair(mapkey, args.get(mapkey)
							.toString()));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			}
			HttpResponse response = client.execute(httpPost);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				result = EntityUtils.toString(response.getEntity());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != httpPost) {
				httpPost.abort();
			}
		}
		return result;
	}

	/**
	 * Get network resource by POST with InputStream.
	 * 
	 * @param url
	 *            The request address.
	 * @param args
	 *            The form parameters to submit.
	 * @return
	 */
	public InputStream doInputStreamPost(String url, Map<String, Object> args) {
		InputStream result = null;
		HttpPost httpPost = null;
		try {
			httpPost = parseHttpPost(url);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if (null != args && 0 != args.size()) {
				Iterator<String> argsIterator = args.keySet().iterator();
				while (argsIterator.hasNext()) {
					String mapkey = argsIterator.next();
					nvps.add(new BasicNameValuePair(mapkey, args.get(mapkey)
							.toString()));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			}
			HttpResponse response = client.execute(httpPost);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				result = response.getEntity().getContent();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != httpPost) {
				httpPost.abort();
			}
		}
		return result;
	}

	/**
	 * Upload file to network.
	 * 
	 * @param url
	 *            The request address.
	 * @param fileName
	 *            The name of upload file.
	 * @param filePath
	 *            The path of upload file.
	 * @param contentType
	 *            The content type of upload file.
	 * @return
	 */
	public String doPost(String url, String fileName, String filePath,
			String contentType) {
		String result = null;
		HttpPost httpPost = null;
		try {
			File file = new File(filePath);
			if (null != file && file.exists() && null != contentType) {
				httpPost = parseHttpPost(url);
				HttpEntity httpEntity = new FileEntity(file, contentType);
				httpPost.setHeader("filename",
						URLEncoder.encode(fileName, HTTP.UTF_8));
				httpPost.setHeader(HTTP.CONTENT_LEN,
						String.valueOf(httpEntity.getContentLength()));
				httpPost.setEntity(httpEntity);
				HttpResponse response = client.execute(httpPost);
				if (HttpStatus.SC_OK == response.getStatusLine()
						.getStatusCode()) {
					result = EntityUtils.toString(response.getEntity());
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != httpPost) {
				httpPost.abort();
			}
		}
		return result;
	}

}
