package com.yosneaker.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ArticleItem implements Serializable{
    private Integer itemId;

    private String itemTitle;

    private Integer itemLevel;

    private String itemContent;

    private String itemImages;

    private Integer itemArticleId;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle == null ? null : itemTitle.trim();
    }

    public Integer getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(Integer itemLevel) {
        this.itemLevel = itemLevel;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent == null ? null : itemContent.trim();
    }

    public String getItemImages() {
        return itemImages;
    }

    public void setItemImages(String itemImages) {
        this.itemImages = itemImages == null ? null : itemImages.trim();
    }

    public Integer getItemArticleId() {
        return itemArticleId;
    }

    public void setItemArticleId(Integer itemArticleId) {
        this.itemArticleId = itemArticleId;
    }
    
    public ArrayList<String> getImagesList(){
    	ArrayList<String> result = new ArrayList<String>();
    	if(itemImages!=null&&itemImages.length()>0){
    		result.addAll(Arrays.asList(itemImages.split(",")));
    	}
    	return result;
    }
    
    public void setImagesList(List<String> images){
		if (images != null && images.size() > 0) {
			StringBuffer items = new StringBuffer();
			for (String item : images) {
				items.append(item + ",");
			}
			String temp = items.toString();
			this.itemImages = temp.substring(0, temp.lastIndexOf(","));
		}
    }

	@Override
	public String toString() {
		return "ArticleItem [itemId=" + itemId + ", itemTitle=" + itemTitle
				+ ", itemLevel=" + itemLevel + ", itemContent=" + itemContent
				+ ", itemImages=" + itemImages + ", itemArticleId="
				+ itemArticleId + "]";
	}
}