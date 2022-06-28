package Activities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class GitHub_RestAssured_Project {

    // Declare request specification
    RequestSpecification requestSpec;
    String sshKey;
    int keyId;

    @BeforeClass
    public void setUp() {
        // Create request specification
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization","token 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")
                .setBaseUri("https://api.github.com")
                .build();
        sshKey = "ssh-rsa xyz";
   }
    @Test(priority=1)
    public void postGit() {
       String reqBody = "{\"title\": \"RestAssured\", \"key\": \"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQCjApJamx18TPy0FbTleQKO+tdqK70jgkbMoaQf/72LY4WgGSmh593QKTA4lq4RH62mgi/2lMged8UqiW8UeKZAKtenGheGk/mx7bP2NmqZ0yYHOuVSUNQ/M7v7s3PAx5GLAvfFcOnCAGBiWcVzO6uWktTh7Lgd4pSMWEvkGexHvgazN7iUbV2HCBnEofn3pQAxgiZhh8qgDMc4gGF1NkAienGxdD3QTiEMLYiqmRpcs/AYJRvANfisBsorcwq4PVLn9H97Vj626KZuwbKv3IORCqFq4GDdBY1MviNMBKkn8u8zRN//2bV/JDx8Z+ZzFbjMnsNlp5x+S0IMgDjg+ZclUcL2j/kREABGCsf4WPwiGA+S8hvM5lBB0jVUHNxWATywd4U/9MdxwwhwgWdjWL07ihaHMUXPdvDxDmOIgBSwfcxFF5TnoXJMP86LwZ7XzgT+hb0wh3WhyxkV02qzkubKNFF/upz+lS2vrbOtP+Op+K224CQMA8PtEBVTtcxGrPU= gmx\"}";
        Response response = given().spec(requestSpec) // Use requestSpec
                .body(reqBody) // Send request body
                .when().post("/user/keys"); // Send POST request
        System.out.println("POST GIT Response--> "+response.getBody().asPrettyString());
        Reporter.log("POST GIT Response--> "+response.getBody().asPrettyString());

        keyId = response.then().extract().path("id");
        System.out.println("SSH Key Id--> "+keyId);
        Reporter.log("SSH Key Id--> "+keyId);
       // Assertions
        response.then().statusCode(201);
    }

    @Test(priority=2)
    public void getGit() {
        Response response = given().spec(requestSpec) // Use requestSpec
                           .when().get("/user/keys"); // Send GET request
        System.out.println("GET GIT Response--> "+response.getBody().asPrettyString());
        Reporter.log("GET GIT Response--> "+response.getBody().asPrettyString());
        // Assertions
        response.then().statusCode(200);
    }

    @Test(priority = 3)
    public void deleteGit(){
        Response response = given().spec(requestSpec)
                .when().pathParam("keyId", keyId)
                .when().delete("/user/keys/{keyId}");
        System.out.println("DELETE GIT Response -->"+response.getBody().asPrettyString());
        Reporter.log("DELETE GIT Response"+response.getBody().asPrettyString());
        //Assertions
        response.then().statusCode(204);
    }
}
