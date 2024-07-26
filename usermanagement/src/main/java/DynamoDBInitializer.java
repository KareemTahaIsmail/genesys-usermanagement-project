import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import genesys.project.usermanagement.entities.User;


// Run this to create the table 'User' in the local DynamoDB
public class DynamoDBInitializer {

    public static void main(String[] args) {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("dummyaccesskey", "dummysecretkey");

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();

        DynamoDBMapper mapper = new DynamoDBMapper(client, DynamoDBMapperConfig.DEFAULT);

        CreateTableRequest createTableRequest = mapper.generateCreateTableRequest(User.class)
                .withProvisionedThroughput(new ProvisionedThroughput(5L, 5L));

        try {
            client.createTable(createTableRequest);
            System.out.println("Table created successfully.");
        } catch (ResourceInUseException e) {
            System.out.println("Table already exists.");
        }
    }
}
