package kpaas.msa.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kpaas.msa.common.CommonDAO;

@Repository("commentDAO")
public class CommentDAO extends CommonDAO {
	
	public Map<String, Object> getCommentCount(Map<String, Object> paramMap) throws Exception {
		return getSqlSession().selectOne("kpaas.msa.service.impl.CommentMapper.getCommentCount", paramMap);
	}

	public List<Object> getCommentList(Map<String, Object> paramMap) throws Exception {
		return getSqlSession().selectList("kpaas.msa.service.impl.CommentMapper.getCommentList", paramMap);
	}

	public int postComment(Map<String, Object> paramMap) throws Exception {

		return getSqlSession().insert("kpaas.msa.service.impl.CommentMapper.postComment", paramMap);
	}

	public int putComment(Map<String, Object> paramMap) throws Exception {

		return getSqlSession().update("kpaas.msa.service.impl.CommentMapper.putComment", paramMap);
	}

	public int deleteComment(Map<String, Object> paramMap) throws Exception {

		return getSqlSession().delete("kpaas.msa.service.impl.CommentMapper.deleteComment", paramMap);
	}

}
