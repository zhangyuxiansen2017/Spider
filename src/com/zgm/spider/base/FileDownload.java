package com.zgm.spider.base;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author zhang
 * @date 2019年1月3日 上午9:49:19
 * 
*/
public class FileDownload {
	/**
	  * 文件下载
	  * @param url 链接地址
	  * @param path 要保存的路径及文件名
	  * @return
	  */
	 public static boolean download(String url,String path){
	   
	   boolean flag = false;
	   
	   CloseableHttpClient httpclient = HttpClients.createDefault();
	   RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000)
	       .setConnectTimeout(2000).build();

	   HttpGet get = new HttpGet(url);
	   get.setConfig(requestConfig);
	   
	   BufferedInputStream in = null;
	   BufferedOutputStream out = null;
	   try{
	     for(int i=0;i<3;i++){
	       CloseableHttpResponse result = httpclient.execute(get);
	       System.out.println(result.getStatusLine());
	       if(result.getStatusLine().getStatusCode() == 200){
	         in = new BufferedInputStream(result.getEntity().getContent());
	         File file = new File(path);
	         out = new BufferedOutputStream(new FileOutputStream(file));
	         byte[] buffer = new byte[1024];
	         int len = -1;
	         while((len = in.read(buffer,0,1024)) > -1){
	           out.write(buffer,0,len);
	         }
	         flag = true;
	         break;
	       }else if(result.getStatusLine().getStatusCode() == 500){
	         continue ;
	       }
	     }
	     
	   }catch(Exception e){
	     e.printStackTrace();
	     flag = false;
	   }finally{
	     get.releaseConnection();
	     try{
	       if(in != null){
	         in.close();
	       }
	       if(out != null){
	         out.close();
	       }
	     }catch(Exception e){
	       e.printStackTrace();
	       flag = false;
	     }
	   }
	   return flag;
	 }
}
