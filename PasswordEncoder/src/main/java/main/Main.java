package main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import encode.BCryptEncoder;

public class Main {

	public static void main(String[] args) {
		
		ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/application-config.xml");
		
		BCryptEncoder encoder=context.getBean(BCryptEncoder.class);
		
		String origin="123123123";
		String hashed=encoder.encode(origin);
		
		System.out.println(hashed);
		System.out.println(encoder.matches(origin, hashed));		

	}

}
