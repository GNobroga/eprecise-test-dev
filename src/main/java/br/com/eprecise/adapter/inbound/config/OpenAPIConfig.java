package br.com.eprecise.adapter.inbound.config;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(
        title="E-precise - Cities and States",
        version = "1.0.0",
        contact = @Contact(
            name = "Gabriel Cardoso Girarde",
            url = "https://github.com/GNobroga/eprecise-test-dev",
            email = "gabrielcardosogirarde@gmail.com"),
        description = "Uma API para gerenciar Estados e Cidades"
    )
)
public class OpenAPIConfig extends Application {}
