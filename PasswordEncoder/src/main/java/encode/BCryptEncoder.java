package encode;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptEncoder{
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public String encode(String origin){
		return passwordEncoder.encode(origin);
	}
	
	public boolean matches(String origin,String hashed){
		return passwordEncoder.matches(origin, hashed);
	}	
	
}
