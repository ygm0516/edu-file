package kpaas.msa.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import kpaas.msa.common.CommonUtil;
import kpaas.msa.service.UserService;

/**
 * @author JaemooSong
 *
 */
@RestController
public class UserController {

	@Resource(name = "userService")
	private UserService userService;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public Map<String, Object> getUsers(@RequestParam(required = false) String searchType,
			@RequestParam(required = false) String searchValue, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer pagePerCount) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> resultData = new HashMap<String, Object>();

		Map<String, Object> userCount = null;
		List<Object> userList = null;

		// parameter Setting
		try {
			paramMap.put("searchType", searchType);
			paramMap.put("searchValue", searchValue);
			paramMap.put("page", CommonUtil.nvl(page, 1));
			paramMap.put("pagePerCount", CommonUtil.nvl(pagePerCount, 15));
		} catch (Exception e) {
			result.put("result", "ERROR");
			result.put("errMsg", "input parameter error.");
			e.printStackTrace();
			return result;
		}

		// Select UserList
		try {

			// select UserCount
			userCount = userService.getUserCount(paramMap);
			long count = 0;

			if (userCount != null) {
				count = (Long) userCount.get("COUNT");
				resultData.put("userCount", count);
			}

			// select UserList
			if (count != 0) {
				userList = (List<Object>) userService.getUserList(paramMap);
				resultData.put("userList", userList);
			}

			result.put("result", "SUCCESS");
			result.put("resultData", resultData);
		} catch (Exception e) {
			result.put("result", "ERROR");
			result.put("errMsg", e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public Map<String, Object> getUser(@PathVariable String userId) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> resultData = new HashMap<String, Object>();

		List<Object> userList = null;

		// parameter Setting
		paramMap.put("userId", userId);

		try {
			// select User
			userList = (List<Object>) userService.getUser(paramMap);
			if (userList != null && userList.size() == 1) {
				resultData = (Map<String, Object>) userList.get(0);
			} else {
				throw new Exception("일치하는 사용자가 없습니다.");
			}

			result.put("result", "SUCCESS");
			result.put("resultData", resultData);
		} catch (Exception e) {
			result.put("result", "ERROR");
			result.put("errMsg", e.getMessage());
			e.printStackTrace();
		}

		return result;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user/checkLogin", method = RequestMethod.POST)
	public Map<String, Object> checkLogin(HttpEntity<String> httpEntity) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> userConunt = null;
		Map<String, String> jsonMap = null;

		try {
			
			
			// parameter Setting
			jsonMap = mapper.readValue(httpEntity.getBody(), Map.class);
			paramMap.put("userId", jsonMap.get("userId"));
			paramMap.put("userPasswd",jsonMap.get("userPasswd"));
			
			// select User
			userConunt = userService.checkLogin(paramMap);
			if (userConunt == null || userConunt.isEmpty()) {
				throw new Exception("해당하는 사용자가 없습니다.");
			}

			result.put("result", "SUCCESS");
			result.put("resultData", userConunt);
		} catch (Exception e) {
			result.put("result", "ERROR");
			result.put("errMsg", e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public Map<String, Object> postUser(HttpEntity<String> httpEntity) {

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = httpEntity.getBody();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> resultData = new HashMap<String, Object>();

		// parameter Setting
		try {
			if (jsonString == null || "".equals(jsonString)) {
				jsonString = "{}";
			}
			Map<String, String> jsonMap = mapper.readValue(jsonString, Map.class);

			String userId = jsonMap.get("userId");
			String userPasswd = jsonMap.get("userPasswd");
			String userName = jsonMap.get("userName");

			// null String check
			if (CommonUtil.isEmptyString(userId)) {
				throw new Exception("사용자 ID는 필수 입력값입니다.");
			} else if (CommonUtil.isEmptyString(userPasswd)) {
				throw new Exception("사용자 패스워드는 필수 입력값입니다.");
			} else if (CommonUtil.isEmptyString(userName)) {
				throw new Exception("사용자 명은 필수 입력값입니다.");
			}

			paramMap.put("userId", userId);
			paramMap.put("userPasswd", userPasswd);
			paramMap.put("userName", userName);
		} catch (Exception e) {
			result.put("result", "ERROR");
			result.put("errMsg", e.getMessage());
			e.printStackTrace();

			return result;
		}

		try {

			int insertCount = userService.postUser(paramMap);
			if (insertCount != 1) {
				throw new Exception("사용자 생성에 실패하였습니다.");
			}

			resultData.put("userSeq", paramMap.get("userSeq"));

			result.put("result", "SUCCESS");
			result.put("resultData", resultData);
		} catch (Exception e) {
			result.put("result", "ERROR");
			result.put("errMsg", e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.PUT)
	public Map<String, Object> putUser(@PathVariable String userId, HttpEntity<String> httpEntity) {

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = httpEntity.getBody();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> resultData = new HashMap<String, Object>();

		// parameter Setting
		try {
			if (jsonString == null || "".equals(jsonString)) {
				jsonString = "{}";
			}
			Map<String, String> jsonMap = mapper.readValue(jsonString, Map.class);

			String userPasswd = jsonMap.get("userPasswd");
			String userName = jsonMap.get("userName");

			// 수정 요청 Data가 없는 경우
			if (CommonUtil.isEmptyString(userPasswd) && CommonUtil.isEmptyString(userName)) {
				result.put("result", "SUCCESS");
				return result;
			}

			paramMap.put("userId", userId);
			paramMap.put("userPasswd", userPasswd);
			paramMap.put("userName", userName);
		} catch (Exception e) {
			result.put("result", "ERROR");
			result.put("errMsg", e.getMessage());
			e.printStackTrace();

			return result;
		}

		try {

			int updateCount = userService.putUser(paramMap);
			if (updateCount != 1) {
				throw new Exception("사용자 수정에 실패하였습니다.");
			}

			result.put("result", "SUCCESS");
			result.put("resultData", resultData);
		} catch (Exception e) {
			result.put("result", "ERROR");
			result.put("errMsg", e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
	public Map<String, Object> deleteUser(@PathVariable String userId) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> resultData = new HashMap<String, Object>();

		// parameter Setting
		paramMap.put("userId", userId);

		try {

			int deleteCount = userService.deleteUser(paramMap);
			if (deleteCount == 0) {
				throw new Exception("사용자 삭제에 실패하였습니다.");
			}

			result.put("result", "SUCCESS");
			result.put("resultData", resultData);
		} catch (Exception e) {
			result.put("result", "ERROR");
			result.put("errMsg", e.getMessage());
			e.printStackTrace();
		}

		return result;
	}
}
