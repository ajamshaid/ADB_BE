package com.infotech.adb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${infotech.adb.oauth.clientId}")
    private String clientID;

    @Value("${infotech.adb.oauth.clientSecret}")
    private String clientSecret;

    @Value("${swagger.host.full.dns.auth.link}")
    private String authLink;

    @Bean
    public Docket api() {

        List<ResponseMessage> list = new java.util.ArrayList<>();
        list.add(new ResponseMessageBuilder().code(500).message("500 message")
                .responseModel(new ModelRef("Result")).build());
        list.add(new ResponseMessageBuilder().code(401).message("Unauthorized")
                .responseModel(new ModelRef("Result")).build());
        list.add(new ResponseMessageBuilder().code(406).message("Not Acceptable")
                .responseModel(new ModelRef("Result")).build());

        return new Docket(DocumentationType.SWAGGER_2)
                    .tags(new Tag("GD", "Goods Declaration"),
                new Tag("@TP", "Trader Profile"),
                new Tag("Logs", "Log Requests"),
                            new Tag("PSW", "PSW APIz"))
                .select()
//                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.infotech.adb.api"))
                .paths(PathSelectors.any()).build().securitySchemes(Collections.singletonList(securitySchema()))
                .securityContexts(Collections.singletonList(securityContext())).pathMapping("/")
                .useDefaultResponseMessages(false).apiInfo(apiInfo()).globalResponseMessage(RequestMethod.GET, list)
                .globalResponseMessage(RequestMethod.POST, list)
//                .globalOperationParameters(globalParameterList())
                ;
    }

    private List<Parameter> globalParameterList() {
        Parameter authTokenHeader = new ParameterBuilder()
                .name("X-SITE-ID") // name of the header
                .modelRef(new ModelRef("string")) // data-type of the header
                .required(true) // required/optional
                .parameterType("header") // for query-param, this value can be 'query'
                .description("User Site ID")
                .build();

        return Collections.singletonList(authTokenHeader);
    }

    private OAuth securitySchema() {
        List<AuthorizationScope> authorizationScopeList = newArrayList();
        authorizationScopeList.add(new AuthorizationScope("read", "read all"));
        authorizationScopeList.add(new AuthorizationScope("trust", "trust all"));
        authorizationScopeList.add(new AuthorizationScope("write", "access all"));

        List<GrantType> grantTypes = newArrayList();
        GrantType creGrant = new ResourceOwnerPasswordCredentialsGrant(authLink + "/oauth/token");

        grantTypes.add(creGrant);

        return new OAuth("oauth2schema", authorizationScopeList, grantTypes);

    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.ant("/user/**"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
        authorizationScopes[0] = new AuthorizationScope("read", "read all");
        authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
        authorizationScopes[2] = new AuthorizationScope("write", "write all");

        return Collections.singletonList(new SecurityReference("oauth2schema", authorizationScopes));
    }

    @Bean
    public SecurityConfiguration securityInfo() {
        return new SecurityConfiguration(clientID, clientSecret, "", "", "",
                ApiKeyVehicle.HEADER, "", " ");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("AD Broker").description("Data transfer Switch between " +
                "Pakistan Single Window and authorized dealers")
                .termsOfServiceUrl("https://www.infotechgroup.com")
                .contact(new Contact("InfoTech Group", "https://www.infotechgroup.com/",
                        "info@infotechgroup.com"))
//                .license("Open Source").licenseUrl("https://www.infotechgroup.com/")
                .version("1.0.0").build();
    }
}
