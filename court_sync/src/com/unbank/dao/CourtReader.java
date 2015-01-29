package com.unbank.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import com.unbank.mybatis.entity.CourtInfo;
import com.unbank.mybatis.entity.CourtInfoExample;
import com.unbank.mybatis.factory.DynamicConnectionFactory;
import com.unbank.mybatis.mapper.CourtInfoMapper;

public class CourtReader {
	private static Log logger = LogFactory.getLog(CourtReader.class);

	public List<CourtInfo> readCourtInfos(int task, int limit) {
		List<CourtInfo> courtInfos = null;
		SqlSession sqlSession = DynamicConnectionFactory
				.getInstanceSessionFactory("development").openSession();
		try {
			CourtInfoMapper courtInfoMapper = sqlSession
					.getMapper(CourtInfoMapper.class);
			CourtInfoExample example = new CourtInfoExample();
			example.or().andTaskEqualTo(task);
			example.setOrderByClause("id desc limit " + limit);
			courtInfos = courtInfoMapper.selectByExampleWithBLOBs(example);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("", e);
		} finally {
			sqlSession.close();
		}
		return courtInfos;

	}
}
