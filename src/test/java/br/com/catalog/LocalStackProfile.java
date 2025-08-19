package br.com.catalog;

import java.util.HashMap;
import java.util.Map;
import io.quarkus.test.junit.QuarkusTestProfile;

public class LocalStackProfile implements QuarkusTestProfile {
    @Override
    public Map<String, String> getConfigOverrides() {
        Map<String, String> configs = new HashMap<>();
        configs.put("quarkus.profile", "test");
        configs.put("quarkus.sns.aws.credentials.type", "static");
        configs.put("quarkus.sns.aws.credentials.static-provider.access-key-id", "test");
        configs.put("quarkus.sns.aws.credentials.static-provider.secret-access-key", "test");
        configs.put("quarkus.sns.aws.region", "us-east-1");

        configs.put("quarkus.sqs.aws.credentials.type", "static");
        configs.put("quarkus.sqs.aws.credentials.static-provider.access-key-id", "test");
        configs.put("quarkus.sqs.aws.credentials.static-provider.secret-access-key", "test");
        configs.put("quarkus.sqs.aws.region", "us-east-1");

        configs.put("quarkus.s3.aws.credentials.type", "static");
        configs.put("quarkus.s3.aws.credentials.static-provider.access-key-id", "test");
        configs.put("quarkus.s3.aws.credentials.static-provider.secret-access-key", "test");
        configs.put("quarkus.s3.aws.region", "us-east-1");

        configs.put("quarkus.sns.endpoint-override", "http://localhost:4566");
        configs.put("quarkus.sqs.endpoint-override", "http://localhost:4566");
        configs.put("quarkus.s3.endpoint-override", "http://localhost:4566");

        return configs;
    }
}