package kpaas.msa.filter;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class AuthFilter extends ZuulFilter {

	@Value("${ApiMasterKey}")
	String apiMasterKey;

	@Value("${ApiKeySalt}")
	String salt;

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return FilterConstants.PRE_DECORATION_FILTER_ORDER;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {

		RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest request = context.getRequest();
		
		String apiKey = request.getHeader("apiKey");
		String timestamp = request.getHeader("timestamp");

		String makeKey = null;

		if (timestamp != null && !"".equals(timestamp)) {
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			md.update(salt.getBytes());
			md.update(timestamp.getBytes());
			makeKey = String.format("%064x", new BigInteger(1, md.digest()));
		}

		if (apiKey == null || "".equals(apiKey) 
			|| (!apiKey.equals(apiMasterKey)) && !apiKey.equals(makeKey)) {
			context.setSendZuulResponse(false);
			context.setResponseBody("API key not authorized");
			context.getResponse().setHeader("Content-Type", "text/plain;charset=UTF-8");
			context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
		}

		return null;
	}
}
