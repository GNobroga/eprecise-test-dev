package br.com.eprecise.adapter.in.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.core.MultivaluedMap;

public class ParamUtils {
    
    public static Map<String, String> getParams(MultivaluedMap<String, String> source) {
        final Map<String, String> params = new HashMap<>();

        if (Objects.isNull(source)) {
            return params;
        }
        
        for (Map.Entry<String, List<String>> queryParameters : source.entrySet()) {
            // Adiciona o primeiro valor da lista ao map
            params.put(queryParameters.getKey(), queryParameters.getValue().get(0));
        }
        return params;
    }
}
