package br.com.eprecise.application.inbound.state.outputs;

import br.com.eprecise.domain.entities.state.State;
import lombok.Value;

@Value()
public class StateRecordOutput {
    private String id;
    private String name;
    private String abbreviation;

    public static StateRecordOutput from(State source) {
        return new StateRecordOutput(
            source.getId().getUuid().toString(), 
            source.getName(), 
            source.getAbbreviation());
    } 
}
