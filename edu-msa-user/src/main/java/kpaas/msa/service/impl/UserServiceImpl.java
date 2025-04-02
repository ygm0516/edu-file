package kpaas.msa.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kpaas.msa.dao.UserDAO;
import kpaas.msa.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name = "userDAO")
	private UserDAO userDAO;

	public Map<String, Object> getUserCount(Map<String, Object> paramMap) throws Exception {
		return userDAO.getUserCount(paramMap);
	}

	public List<Object> getUserList(Map<String, Object> paramMap) throws Exception {
		return userDAO.getUserList(paramMap);
	}

	public List<Object> getUser(Map<String, Object> paramMap) throws Exception {
		return userDAO.getUser(paramMap);
	}
	
	public Map<String, Object> checkLogin(Map<String, Object> paramMap) throws Exception {
		return userDAO.checkLogin(paramMap);
	}

	public int postUser(Map<String, Object> paramMap) throws Exception {
		return userDAO.postUser(paramMap);
	}

	public int putUser(Map<String, Object> paramMap) throws Exception {
		return userDAO.putUser(paramMap);
	}

	public int deleteUser(Map<String, Object> paramMap) throws Exception {
		return userDAO.deleteUser(paramMap);
	}

}
