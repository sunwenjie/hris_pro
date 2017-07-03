/*
 * Created on 2005-6-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.kan.base.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Kevin Jin
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CharacterEncodingFilter implements Filter {
	protected String encoding = null;

	protected FilterConfig filterConfig = null;

	protected boolean ignore = true;

	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
		String value = filterConfig.getInitParameter("ignore");
		if (value == null) {
			this.ignore = true;
		} else if (value.equalsIgnoreCase("true")) {
			this.ignore = true;
		} else {
			this.ignore = false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (!ignore) {
			if (this.encoding != null) {
				request.setCharacterEncoding(this.encoding);
			}
		}
		chain.doFilter(request, response);
	}

}