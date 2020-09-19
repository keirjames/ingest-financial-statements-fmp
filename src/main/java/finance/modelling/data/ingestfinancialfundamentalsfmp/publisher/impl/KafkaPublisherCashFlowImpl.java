package finance.modelling.data.ingestfinancialfundamentalsfmp.publisher.impl;

import finance.modelling.data.ingestfinancialfundamentalsfmp.client.dto.FmpCashFlowsDTO;
import finance.modelling.data.ingestfinancialfundamentalsfmp.publisher.contract.KafkaPublisher;
import finance.modelling.fmcommons.data.logging.LogPublisher;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

import static finance.modelling.fmcommons.data.helper.api.publisher.PublisherHelper.buildProducerRecordWithTraceIdHeader;

public class KafkaPublisherCashFlowImpl implements KafkaPublisher<FmpCashFlowsDTO> {

    private final KafkaTemplate<String, Object> template;

    public KafkaPublisherCashFlowImpl(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    public void publishMessage(String topic, FmpCashFlowsDTO payload) {
        String traceId = UUID.randomUUID().toString();
        template.send(buildProducerRecordWithTraceIdHeader(topic, payload.getSymbol(),payload, traceId));
        LogPublisher.logInfoDataItemSent(FmpCashFlowsDTO.class, topic, traceId);
    }
}
