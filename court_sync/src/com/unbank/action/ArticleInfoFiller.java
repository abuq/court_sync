package com.unbank.action;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;

import com.unbank.mybatis.entity.ArticleContent;
import com.unbank.mybatis.entity.ArticleInfo;
import com.unbank.mybatis.entity.CourtInfo;
import com.unbank.mybatis.factory.DynamicConnectionFactory;
import com.unbank.mybatis.mapper.ArticleContentMapper;
import com.unbank.mybatis.mapper.ArticleInfoMapper;
import com.unbank.mybatis.mapper.CourtInfoMapper;
import com.unbank.tools.SimpleTools;

public class ArticleInfoFiller {

	public void consumeInformation(CourtInfo information) {
		ArticleInfo articleInfo = fillArticle(information);
		ArticleContent articleContent = new ArticleContent();
		articleContent.setText(information.getContent());
		SqlSession sqlSession = DynamicConnectionFactory
				.getInstanceSessionFactory("spider").openSession();
		try {
			int crawlId = saveArticleInfo(articleInfo, sqlSession);
			saveArticleContent(articleContent, sqlSession, crawlId);
			sqlSession.commit(true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}

		updateCourtInfoTask(information);

	}

	private void updateCourtInfoTask(CourtInfo information) {
		SqlSession sqlSession = DynamicConnectionFactory
				.getInstanceSessionFactory("development").openSession();
		try {
			CourtInfoMapper courtInfoMapper = sqlSession
					.getMapper(CourtInfoMapper.class);
			information.setTask(2);
			courtInfoMapper.updateByPrimaryKeyWithBLOBs(information);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}

	}

	private void saveArticleContent(ArticleContent articleContent,
			SqlSession sqlSession, int crawlId) {
		ArticleContentMapper articleContentMapper = sqlSession
				.getMapper(ArticleContentMapper.class);
		articleContent.setCrawlId(crawlId);
		articleContentMapper.insertSelective(articleContent);
	}

	private int saveArticleInfo(ArticleInfo articleInfo, SqlSession sqlSession) {
		ArticleInfoMapper articleInfoMapper = sqlSession
				.getMapper(ArticleInfoMapper.class);
		articleInfoMapper.insertSelective(articleInfo);
		int crawlId = articleInfo.getCrawlId();
		return crawlId;
	}

	private ArticleInfo fillArticle(CourtInfo information) {
		ArticleInfo articleInfo = new ArticleInfo();
		System.out.println(information.getContent());
		articleInfo
				.setCrawlBrief(information.getContent().length() >= 100 ? information
						.getContent().substring(0, 100) : information
						.getContent());
		articleInfo.setCrawlTime(new Date());
		articleInfo.setCrawlTitle(information.getAnnouncementType());
		articleInfo.setCrawlViews(0);
		articleInfo.setFileIndex((byte) 7);
		articleInfo.setNewsTime(SimpleTools.stringToDate(
				information.getPostingDate(), "yyyy-MM-dd"));
		articleInfo.setTask((byte) 2);
		articleInfo.setUrl(information.getDetailurl());
		articleInfo.setWebName("法院公告网");
		articleInfo.setWebsiteId(5744);
		return articleInfo;

	}

}
