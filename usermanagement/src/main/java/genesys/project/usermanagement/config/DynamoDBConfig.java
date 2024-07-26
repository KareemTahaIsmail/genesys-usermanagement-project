package genesys.project.usermanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

// Marks this class as a configuration class for Spring
@Configuration
// Enables DynamoDB repositories and specifies the base package to scan for repositories
@EnableDynamoDBRepositories(basePackages = "genesys.project.usermanagement.repos")
public class DynamoDBConfig {

    // Injects the value of 'amazon.dynamodb.endpoint' from application properties
    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    // Injects the value of 'amazon.aws.accesskey' from application properties
    @Value("${amazon.aws.accesskey}")
    private String amazonAWSAccessKey;

    // Injects the value of 'amazon.aws.secretkey' from application properties
    @Value("${amazon.aws.secretkey}")
    private String amazonAWSSecretKey;

    // Defines a Spring bean for AmazonDynamoDB
    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        // Creates and returns an AmazonDynamoDB client with the specified credentials and endpoint
        return AmazonDynamoDBClientBuilder.standard()
                // Sets the AWS credentials provider
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey)))
                // Configures the endpoint for the DynamoDB client
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, "us-west-2"))
                .build();
    }
}
