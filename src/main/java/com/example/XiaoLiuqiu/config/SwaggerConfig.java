package com.example.XiaoLiuqiu.config;
//package com.example.quiz.Config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.oas.annotations.EnableOpenApi;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//@Configuration //�� spring ���� class ���t�m��
//@EnableOpenApi //�q�L�������ӱҥ� swagger
//@EnableWebMvc //�ޤJ DelegatingWebMvcConfiguration �t�m���A�ñҥ� spring MVC
//public class SwaggerConfig {
//	
//	// ���f�T���A�]�A ���D�B�����B�����B�p���H��
//	private ApiInfo DEFAULT_API_INFO = new ApiInfoBuilder() //
//			.title("Quiz Restful API") // ���D
//			.description("Quiz API") // ����
////			.termsOfServiceUrl("urn:tos") // �A�ȱ��ں��}�A�q�{ urn:tos
////			.contact(new Contact("DEFAULT", "", "")) // �p���H
////			.license("Apache 2.0") //
////			.version("v3") // ����
////			.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0.txt") //
//			.build();
//
//	@Bean
//	public Docket api() {
//		// ��^���ɷ��n�T��
//		return new Docket(DocumentationType.SWAGGER_2) //
//				.apiInfo(DEFAULT_API_INFO)//
//				.select()//
//				.apis(RequestHandlerSelectors.basePackage("com.example.quiz.controller"))//
//				.paths(PathSelectors.any())//
//				.build();
//	}
//}
