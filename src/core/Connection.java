package core;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import java.nio.file.Paths;

/**
 * Centralized database connection manager
 * Provides MongoDB client and datastore access throughout the application
 * Reads configuration from src/core/config.xml
 */
public class Connection {
    private static Connection instance;
    private MongoClient mongoClient;
    private Datastore datastore;
    private String databaseName = "gym_db";
    
    /**
     * Private constructor to enforce singleton pattern
     * Initializes MongoDB connection from XML config
     */
    private Connection() {
        initializeConnection();
    }
    
    /**
     * Get the singleton instance of Connection
     * @return Connection instance
     */
    public static Connection getInstance() {
        if (instance == null) {
            instance = new Connection();
        }
        return instance;
    }
    
    /**
     * Initialize MongoDB connection from XML config file
     */
    private void initializeConnection() {
        String connectionString;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(Paths.get("src/core/config.xml").toFile());
            doc.getDocumentElement().normalize();
            
            NodeList nodeList = doc.getElementsByTagName("connectionString");
            if (nodeList.getLength() > 0) {
                connectionString = nodeList.item(0).getTextContent().trim();
            } else {
                System.err.println("Error: connectionString element not found in config.xml");
                throw new RuntimeException("Connection string not found in config.xml");
            }
        } catch (Exception e) {
            System.err.println("Error reading connection string from src/core/config.xml: " + e.getMessage());
            throw new RuntimeException("Failed to read database configuration", e);
        }
        
        // Create MongoDB client and datastore
        mongoClient = MongoClients.create(connectionString);
        datastore = Morphia.createDatastore(mongoClient, databaseName);

        // Map packages containing entity classes
        // datastore.getMapper().map(Member.class);
        datastore.ensureIndexes();

    }
    
    /**
     * Get the MongoDB Datastore instance
     * @return Datastore instance
     */
    public Datastore getDatastore() {
        return datastore;
    }
    
    /**
     * Get the MongoDB Client instance
     * @return MongoClient instance
     */
    public MongoClient getMongoClient() {
        return mongoClient;
    }
    
    /**
     * Register an entity class with Morphia
     * @param entityClass The entity class to register
     */
    public void mapEntity(Class<?> entityClass) {
        datastore.getMapper().map(entityClass);
    }
    
    /**
     * Register all entities in a package
     * @param packageName The package name containing entity classes
     */
    public void mapPackage(String packageName) {
        datastore.getMapper().mapPackage(packageName);
    }
    
    /**
     * Close the MongoDB connection
     * Should be called when the application shuts down
     */
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}

// @Entity("members")
// class Member {
//     @Id
//     private ObjectId id;
    
//     @Property("memberId")
//     private String memberId;
//     private String name;
//     private String membershipType;
//     private String startDate;
//     private String endDate;
//     private String status;
    
//     public Member() {}
    
//     public Member(String memberId, String name, String membershipType, 
//                   String startDate, String endDate, String status) {
//         this.memberId = memberId;
//         this.name = name;
//         this.membershipType = membershipType;
//         this.startDate = startDate;
//         this.endDate = endDate;
//         this.status = status;
//     }
    
//     public ObjectId getId() { return id; }
//     public void setId(ObjectId id) { this.id = id; }
    
//     public String getMemberId() { return memberId; }
//     public void setMemberId(String memberId) { this.memberId = memberId; }
    
//     public String getName() { return name; }
//     public void setName(String name) { this.name = name; }
    
//     public String getMembershipType() { return membershipType; }
//     public void setMembershipType(String membershipType) { this.membershipType = membershipType; }
    
//     public String getStartDate() { return startDate; }
//     public void setStartDate(String startDate) { this.startDate = startDate; }
    
//     public String getEndDate() { return endDate; }
//     public void setEndDate(String endDate) { this.endDate = endDate; }
    
//     public String getStatus() { return status; }
//     public void setStatus(String status) { this.status = status; }
// }
