package com.yosneaker.client.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.yosneaker.client.app.YosneakerAppState;
import com.yosneaker.client.model.Account;
import com.yosneaker.client.model.Article;
import com.yosneaker.client.model.Comment;

public class HttpClientUtil {

    private static AsyncHttpClient client = new AsyncHttpClient();
    private static SyncHttpClient syncClient = new SyncHttpClient();
    
    static
    {
        client.setTimeout(Constants.HTTP_TIME_OUT);   //设置链接超时，如果不设置，默认为10s
    }

    /**
	 * 判断网络状态
	 */
	public static boolean isNetWorkConnected(Context context){
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
	
		return (info!=null&&info.isConnected());
	}
    
    /**
     * 返回 服务器地址+命名空间的拼接串(即完整请求地址)
     * @param relativeUrl
     * @return
     */
    public static String getAbsoluteUrl(String relativeUrl) {
        return Constants.HTTP_BASE_URL + relativeUrl;
    }

    /**
     * 发布测评
     * @param article
     * @param responseHandler
     */
    public static void publishArticle(Article article,JsonHttpResponseHandler responseHandler) {
    	try {
			StringEntity stringEntity = new StringEntity(JSON.toJSONString(article));
			syncClient.post(YosneakerAppState.getInstance().getContext(),getAbsoluteUrl("store/articles"),stringEntity,"application/json",responseHandler);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * 获取热门测评
     * @param page 当前页
     * @param rows 返回条数,默认十条
     * @param responseHandler
     */
    public static void getPublicArticle(int page,int rows,JsonHttpResponseHandler responseHandler) {
    	String httpUrl = getAbsoluteUrl("store/articles/public");
    	if (page == 0 || rows == 0) {
			client.get(httpUrl, responseHandler);
		}else {
			RequestParams params = new RequestParams();
			params.put("page", page);
			params.put("rows", rows);
			client.get(httpUrl, params, responseHandler);
		}
    }
    
    /**
     * 获取测评详情
     * @param articleid 测评id
     * @param responseHandler
     */
    public static void getArticleDetial(int articleid,JsonHttpResponseHandler responseHandler) {
    	String httpUrl = getAbsoluteUrl("store/articles/")+articleid;
    	client.get(httpUrl, responseHandler);
    }
    
    /**
     * 添加评论
     * @param comment 评论
     * @param responseHandler
     */
    public static void addComment(Comment comment,JsonHttpResponseHandler responseHandler) {
    	Gson gson = new Gson();
    	try {
			StringEntity stringEntity = new StringEntity(gson.toJson(comment));
			client.post(YosneakerAppState.getInstance().getContext(),getAbsoluteUrl("store/articles"),stringEntity,"application/json",responseHandler);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * 根据测评ID分页获取评论列表
     * @param articleID
     * @param page
     * @param rows
     * @param responseHandler
     */
    public static void getCommentsByArticleID(int articleID,int page,int rows,JsonHttpResponseHandler responseHandler) {
    	String httpUrl = getAbsoluteUrl("store/articles/")+articleID;
    	String suffixUrl = "/comments";
    	if (page != 0 && rows != 0) {
    		suffixUrl= suffixUrl+"?"+"page="+page+"&row="+rows;
		}
    	client.get(httpUrl+suffixUrl, responseHandler);
    }
    
    /**
     * 上传图片到服务器
     * @param bitmap
     * @param responseHandler
     */
    public static void uploadResources(Bitmap bitmap,AsyncHttpResponseHandler responseHandler) {
    	RequestParams params = new RequestParams();  
    	params.put("image", BitmapUtil.bitmap2Base64Str(YosneakerAppState.getInstance().getContext(), bitmap));
    	client.post(getAbsoluteUrl("upload/resources/picture.json"), params,responseHandler);
    }
    
    /**
     * 获取图片
     * @param imageUrl
     * @param responseHandler
     */
    public static void getResources(String imageUrl,AsyncHttpResponseHandler responseHandler) {
    	String httpUrl = getAbsoluteUrl("upload/resources/generic/")+imageUrl;
    	client.get(httpUrl, responseHandler);
    }
    
    /**
     * 获取品牌标签
     * @param responseHandler
     */
    public static void getBrands(JsonHttpResponseHandler responseHandler) {
    	String httpUrl = getAbsoluteUrl("store/brands");
    	client.get(httpUrl, responseHandler);
    }
    
    /**
     * 根据品牌ID和关键字模糊查询型号列表
     * @param brandId
     * @param keyword
     * @param responseHandler
     */
    public static void getBrandByKeyword(int brandId,String keyword,JsonHttpResponseHandler responseHandler) {
    	String httpUrl = getAbsoluteUrl("store/brands/")+brandId;
    	String suffixUrl = "/models";
    	if (!TextUtils.isEmpty(keyword)) {
    		suffixUrl= suffixUrl+"?"+"keyword="+keyword;
		}
    	client.get(httpUrl+suffixUrl, responseHandler);
    }
    
    /**
     * 对鞋子型号的意向,可以是已入(1),和想入(0)
     * @param intentionModelId 鞋子型号ID
     * @param intentionAccountId 用户id
     * @param intentionArticleType 已入(值为1),和想入(值为0)
     * @param responseHandler
     */
    public static void getModelIntent(int intentionModelId,int intentionAccountId,int intentionArticleType,JsonHttpResponseHandler responseHandler) {
    	RequestParams params = new RequestParams();
		params.put("intentionModelId", intentionModelId);
		params.put("intentionAccountId", intentionAccountId);
		params.put("intentionArticleType", intentionArticleType);
		client.get(getAbsoluteUrl("store/models/action/want"), params, responseHandler);
    }

	public static void login(Account account,
			JsonHttpResponseHandler jsonHttpResponseHandler,Context context) {
		StringEntity entity = null;
		try {
			entity = new StringEntity(JSON.toJSONString(account));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.post(context,getAbsoluteUrl("store/register"),entity, "application/json", jsonHttpResponseHandler);
	}
    
	 private static String invoke(DefaultHttpClient httpclient,  
	            HttpUriRequest httpost) {  
	          
	        HttpResponse response = sendRequest(httpclient, httpost);  
	        String body = paseResponse(response);  
	          
	        return body;  
	    }  
	  
	    private static String paseResponse(HttpResponse response) {  
	        HttpEntity entity = response.getEntity();  
	          
	        String charset = EntityUtils.getContentCharSet(entity);  
	          
	        String body = null;  
	        try {  
	            body = EntityUtils.toString(entity);  
	        } catch (ParseException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	          
	        return body;  
	    }  
	  
	    private static HttpResponse sendRequest(DefaultHttpClient httpclient,  
	            HttpUriRequest httpost) {  
	        HttpResponse response = null;  
	          
	        try {  
	            response = httpclient.execute(httpost);  
	        } catch (ClientProtocolException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return response;  
	    }  
	  
	    private static HttpPost postForm(String url, Map<String, String> params){  
	          
	        HttpPost httpost = new HttpPost(url);  
	        List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
	          
	        Set<String> keySet = params.keySet();  
	        for(String key : keySet) {  
	            nvps.add(new BasicNameValuePair(key, params.get(key)));  
	        }  
	          
	        try {  
	            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));  
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	        }  
	          
	        return httpost;  
	    }  
}
