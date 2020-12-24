package com.chung.board;

import com.chung.board.lecture.AppConfig;
import com.chung.board.lecture.member.MemberService;
import com.chung.board.lecture.member.MemberServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

import java.io.*;
import java.net.URLConnection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BoardApplicationTests {

	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

	@Test
	void contextLoads() {
		String[] beanDefinitionNames = ac.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			System.out.println("beanDefinitionName = " + beanDefinitionName);
		}
	}

	@Test
	void findApplicationBean() {
		String[] beanDefinitionNames = ac.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
			if(beanDefinition.getRole()==BeanDefinition.ROLE_APPLICATION){
				System.out.println("beanDefinitionName = " + beanDefinitionName);
			}
		}
	}

	@Test
	void getBeanTest() {
		MemberService bean = ac.getBean(MemberService.class);
		assertThat(bean).isInstanceOf(MemberServiceImpl.class);
	}

	@Test
	void throwExceptionsTest(){
		assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("hello"));
	}

	// BufferedInputStream과 그냥 inputStream 속도차이가 심각하게 남
	// 전자 0.241초 후자 5.7초...
	@Test
	void ResourceTest() throws Exception{
		String output = "C:\\Users\\user\\Desktop\\Java\\Spring강의\\스프링 핵심 원리 - 기본편\\test.jpg";
		Resource resource = ac.getResource("http://203.252.161.219:8080/images/store/S200925_1601027813495/1_1601027814652.jpg");

		BufferedInputStream bufferedInputStream=
				new BufferedInputStream(resource.getURL().openConnection().getInputStream());
		BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(new FileOutputStream(output));

		int fileByte;
		while((fileByte=bufferedInputStream.read())!=-1){
			bufferedOutputStream.write(fileByte);
		}
		bufferedInputStream.close();
		bufferedOutputStream.close();
	}

}
