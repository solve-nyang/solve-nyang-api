package com.ssafy.solvedpick.common.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    private final String accessKey;

    private final String secretKey;

    private final String region;

    public S3Config(
            @Value("${spring.cloud.aws.credentials.access-key}") String accessKey,
            @Value("${spring.cloud.aws.credentials.secret-key}") String secretKey,
            @Value("${spring.cloud.aws.region.static}")String region
    ) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.region = region;
    }

    @Bean
    public AmazonS3 amazonS3() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder.standard()
                .withClientConfiguration(new ClientConfiguration()
                        .withConnectionTimeout(5000)
                        .withSocketTimeout(5000))
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
}