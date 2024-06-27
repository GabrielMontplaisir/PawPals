package com.pawpals.interfaces;

import java.util.Map;

import jakarta.servlet.http.HttpServlet;

public abstract class FormValidation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected final String emailRegEx = "\\S+@\\S+\\.\\S+";
	
	protected boolean isEmpty(String value) {
		return value == null || value.equals("");
	}
	
	protected abstract String validateForm(Map<String,String[]> params);
}
