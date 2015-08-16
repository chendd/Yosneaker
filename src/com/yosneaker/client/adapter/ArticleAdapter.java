package com.yosneaker.client.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yosneaker.client.R;
import com.yosneaker.client.model.ArticleList;
import com.yosneaker.client.util.DateUtil;
import com.yosneaker.client.view.AssessStarView;
import com.yosneaker.client.view.RoundImageView;

/**
 * 测评adapter
 * @author chendd
 */
public class ArticleAdapter extends ArrayAdapter<ArticleList> {
	
	private LayoutInflater mInflater;
	
    public ArticleAdapter(Context context, ArrayList<ArticleList> Articles) {
        super(context, android.R.layout.simple_list_item_1, Articles);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	ViewHolder holder;
    	// if we weren't given a view, inflate one
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.xlistview_article_item, null);
            holder = new ViewHolder();
            holder.tv_comment_title = (TextView) convertView.findViewById(R.id.tv_comment_title);
            holder.tv_comment_date = (TextView) convertView.findViewById(R.id.tv_comment_date);
            holder.tv_comment_readers = (TextView) convertView.findViewById(R.id.tv_comment_readers);
            holder.iv_comment_cover = (ImageView) convertView.findViewById(R.id.iv_comment_cover);
            holder.riv_comment_head = (RoundImageView) convertView.findViewById(R.id.riv_comment_head);
            holder.asv_comment_assess_star = (AssessStarView) convertView.findViewById(R.id.asv_comment_assess_star);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        // configure the view for this Article
        ArticleList c = getItem(position);

        holder.tv_comment_title.setText(c.getArticleTitle());
        holder.tv_comment_readers.setText(""+(c.getArticleReadCount()==null?0:c.getArticleReadCount()));
        holder.tv_comment_date.setText(""+DateUtil.getIntervalDate(c.getArticleCreateTime()));
        holder.asv_comment_assess_star.setStarNumber(c.getArticleLevel()==null?0:c.getArticleLevel());
        //TODO iv_comment_cover,riv_comment_head
        ImageLoader.getInstance().displayImage(c.getArticleImages(), holder.iv_comment_cover);
        ImageLoader.getInstance().displayImage(c.getAccount().getAccountImages(), holder.riv_comment_head);
        
        return convertView;
    }
    
    static class ViewHolder {
        TextView tv_comment_title;
        TextView tv_comment_date;
        TextView tv_comment_readers;
        ImageView iv_comment_cover;
        RoundImageView riv_comment_head;
        AssessStarView asv_comment_assess_star;
    }
    
}
