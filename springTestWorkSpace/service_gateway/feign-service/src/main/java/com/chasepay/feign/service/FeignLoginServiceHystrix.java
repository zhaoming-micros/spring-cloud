package com.chasepay.feign.service;

import org.springframework.stereotype.Component;

@Component
public class FeignLoginServiceHystrix implements FeignLoginService {

	@Override
	public String login(String name) {
		return "login error";
	}

	@Override
	public String logout(String name) {
		return "logout error";
	}

}
