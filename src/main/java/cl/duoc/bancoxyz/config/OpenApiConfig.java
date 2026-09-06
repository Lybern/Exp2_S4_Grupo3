package cl.duoc.bancoxyz.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Banco XYZ - Backend for Frontend (BFF) API")
                        .version("1.0.0")
                        .description("Implementación del patrón arquitectónico Backend for Frontend (BFF) " +
                                "para Banco XYZ. Provee interfaces personalizadas y optimizadas para clientes Web, Móvil y Cajeros Automáticos (ATM).")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo - Grupo 3")
                                .url("https://github.com/Lybern/Exp2_S4_Grupo3"))
                        .license(new License().name("Apache 2.0")))
                .tags(List.of(
                        new Tag().name("BFF Móvil").description("Endpoints optimizados para dispositivos móviles (payloads compactos y bajo consumo de red)"),
                        new Tag().name("BFF Web").description("Endpoints enriquecidos para navegadores web (datos completos, historial extendido y métricas)"),
                        new Tag().name("BFF Cajero ATM").description("Endpoints seguros y transaccionales para operaciones críticas en cajeros automáticos")
                ));
    }
}
