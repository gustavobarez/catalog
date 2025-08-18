package br.com.catalog.modules.service.aws;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@ApplicationScoped
public class CatalogSnsService {

    @Inject
    SnsClient snsClient;

    @ConfigProperty(name = "aws.sns.topic.catalog.arn")
    public String topicArn;

    public void publish(String message) {
        PublishRequest request = PublishRequest.builder()
                .topicArn(topicArn)
                .message(message)
                .build();
        snsClient.publish(request);
    }

}
