package com.xiaoxin.wechat.entity.msg.resp;

import java.util.List;
import com.xiaoxin.wechat.entity.Article;;

/**
 * 
 * @标题: NewsMessage.java
 * @描述: 文本消息
 * @作者: chen changxiong
 * @日期: 2015-5-16 下午3:34:55
 * @版本: V1.0
 */

public class NewsMessage extends BaseMessage {
	// 图文消息个数，限制为10条以内
	private int ArticleCount;
	// 多条图文消息信息，默认第一个item为大图
	private List<Article> Articles;

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}
}