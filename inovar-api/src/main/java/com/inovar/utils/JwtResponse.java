package com.inovar.utils;

import java.util.List;
import java.util.Set;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Integer id;
	private List<String> roles;
	

	
	
	public JwtResponse(String accessToken, Integer id,  List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.roles = roles;

	}
}
