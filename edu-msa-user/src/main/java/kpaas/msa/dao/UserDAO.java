package kpaas.msa.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kpaas.msa.common.CommonDAO;

@Repository("userDAO")
public class UserDAO extends CommonDAO {
	
	public Map<String, Object> getUserCount(Map<String, Object> paramMap) throws Exception {
		return getSqlSession().selectOne("kpaas.msa.service.impl.UserMapper.getUserCount", paramMap);
	}

	public List<Object> getUserList(Map<String, Object> paramMap) throws Exception {
		return getSqlSession().selectList("kpaas.msa.service.impl.UserMapper.getUserList", paramMap);
	}

	public List<Object> getUser(Map<String, Object> paramMap) throws Exception {
		return getSqlSession().selectList("kpaas.msa.service.impl.UserMapper.getUser", paramMap);
	}
	public Map<String, Object> checkLogin(Map<String, Object> paramMap) throws Exception {
		return getSqlSession().selectOne("kpaas.msa.service.impl.UserMapper.checkLogin", paramMap);
	}

	public int postUser(Map<String, Object> paramMap) throws Exception {

		return getSqlSession().insert("kpaas.msa.service.impl.UserMapper.postUser", paramMap);
	}

	public int putUser(Map<String, Object> paramMap) throws Exception {

		return getSqlSession().update("kpaas.msa.service.impl.UserMapper.putUser", paramMap);
	}

	public int deleteUser(Map<String, Object> paramMap) throws Exception {

		return getSqlSession().delete("kpaas.msa.service.impl.UserMapper.deleteUser", paramMap);
	}

}
