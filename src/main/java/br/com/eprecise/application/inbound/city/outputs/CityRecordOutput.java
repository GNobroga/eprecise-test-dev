package br.com.eprecise.application.inbound.city.outputs;

import br.com.eprecise.application.inbound.state.outputs.StateRecordOutput;
import lombok.Value;

@Value
public class CityRecordOutput {
    private String cityId;
    private String cityName;
    private Long population;
    private StateRecordOutput state;
}
