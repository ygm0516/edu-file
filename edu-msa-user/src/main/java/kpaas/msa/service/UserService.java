package kpaas.msa.service;

import java.util.List;
import java.util.Map;

public interface UserService {

	public Map<String, Object> getUserCount(Map<String, Object> paramMap) throws Exception;
	
	public List<Object> getUserList(Map<String, Object> paramMap) throws Exception;
	
	public List<Object> getUser(Map<String, Object> paramMap) throws Exception;
	
	public Map<String, Object> checkLogin(Map<String, Object> paramMap) throws Exception;

	public int postUser(Map<String, Object> paramMap) throws Exception;

	public int putUser(Map<String, Object> paramMap) throws Exception;

	public int deleteUser(Map<String, Object> paramMap) throws Exception;

}
