package br.com.eprecise.domain.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseError {
    private String title;   
    private Integer statusCode;
    private List<String> messages;  

    public ResponseError addMessage(String message) {
        if (Objects.isNull(messages)) {
            messages = new ArrayList<>();
        }
        messages.add(message);
        return this;
    }
}
