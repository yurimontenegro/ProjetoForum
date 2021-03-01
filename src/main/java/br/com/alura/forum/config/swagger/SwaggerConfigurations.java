package br.com.alura.forum.config.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.alura.forum.model.Usuario;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfigurations {

@Bean
public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2) //DOCUMENTAÇÃO SWAGGER_2
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.alura.forum")) //TODAS AS CLASSES FORAM LIBERADAS PARA SEREM LIDAS;
                .paths(PathSelectors.ant("/**")) //ANÁLISE LIBERADA PARA TODAS AS URLS;
                .build() //CONSTRUIR O OBJETO;
                .ignoredParameterTypes(Usuario.class) //IGNOREM TODAS AS URLS QUE TRABALHAM COM O USUÁRIO, POIS EXISTEM DADOS CONFID.
                
                .globalOperationParameters(Arrays.asList( //CONFIGURANDO PARÂMETROS GLOBAIS
                		new ParameterBuilder()
                        .name("Authorization") //NOME DO PARÂMETRO;
                        .description("Header para Token JWT") //DESCRIÇÃO DO PARÂMETRO;
                        .modelRef(new ModelRef("string")) //TIPO DO PARÂMETRO = STRING;
                        .parameterType("header") //TIPO DE PARÂMETRO = CABEÇALHO;
                        .required(false) //EXISTEM ENDEREÇOS QUE NÃO PRECISAM DO CABEÇALHO;
                        .build())); //CONSTRUIR O OBJETO;
    }
}