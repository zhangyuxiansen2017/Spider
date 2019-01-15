package com.zgm.spider.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author zhang
 * @date 2019年1月3日 上午9:49:03
 * 
*/
public class HtmlManage {
	public Document manage(String html){
		   Document doc = Jsoup.parse(html);
		   return doc;
		 }
		 
		 public Document manageDirect(String url) throws IOException{
		   Document doc = Jsoup.connect( url ).get();
		   return doc;
		 }
		 
		 public List<String> manageHtmlTag(Document doc,String tag ){
		   List<String> list = new ArrayList<String>();
		   
		   Elements elements = doc.getElementsByTag(tag);
		   for(int i = 0; i < elements.size() ; i++){
		     String str = elements.get(i).html();
		     list.add(str);
		   }
		   return list;
		 }
		 
		 public List<String> manageHtmlClass(Document doc,String clas ){
		   List<String> list = new ArrayList<String>();
		   
		   Elements elements = doc.getElementsByClass(clas);
		   for(int i = 0; i < elements.size() ; i++){
		     String str = elements.get(i).html();
		     list.add(str);
		   }
		   return list;
		 }
		 
		 public List<String> manageHtmlKey(Document doc,String key,String value ){
		   List<String> list = new ArrayList<String>();
		   
		   Elements elements = doc.getElementsByAttributeValue(key, value);
		   for(int i = 0; i < elements.size() ; i++){
		     String str = elements.get(i).html();
		     list.add(str);
		   }
		   return list;
		 }
}
