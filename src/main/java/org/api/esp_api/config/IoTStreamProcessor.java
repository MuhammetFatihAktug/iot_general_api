package org.api.esp_api.config;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class IoTStreamProcessor {

    private StreamsBuilderFactoryBean streamsBuilderFactoryBean;

    @Autowired
    public IoTStreamProcessor(StreamsBuilderFactoryBean streamsBuilderFactoryBean) {
        this.streamsBuilderFactoryBean = streamsBuilderFactoryBean;
    }

    @PostConstruct
    public void start() throws Exception {
        StreamsBuilder builder = streamsBuilderFactoryBean.getObject();
        assert builder != null;
        KStream<String, String> stream = builder.stream("iot-input-topic");
        stream.mapValues(this::processIoTData).to("iot-output-topic");
    }

    private String processIoTData(String value) {
        return "Processed: " + value;
    }
}
