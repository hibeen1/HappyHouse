<<<<<<< HEAD
package com.ssafy.vue.service;

import java.util.Map;

public interface JwtService {

	<T> String create(String key, T data, String subject);
	Map<String, Object> get(String key);
	String getUserId();
	boolean isUsable(String jwt);
	
}
=======
package com.ssafy.vue.service;

import java.util.Map;

public interface JwtService {

	<T> String create(String key, T data, String subject);
	Map<String, Object> get(String key);
	String getUserId();
	boolean isUsable(String jwt);
	
}
>>>>>>> 8dc7dd045e0223a381a2fd61cc44650e6dfb224f
