package api.test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import static api.endpoints.UserEndPoints.deleteUser;


public class UserTest {
   public Logger logger;
    Faker faker;
    User userPayload;
    @BeforeClass
    public void setupData(){
        faker = new Faker();
        userPayload = new User();
        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password());
        userPayload.setPhone(faker.phoneNumber().cellPhone());

       logger = LogManager.getLogger(this.getClass());
    }
    @Test(priority =1)
    public void testPostUser(){
        logger.info("**Post Method");
        Response response =UserEndPoints.createUser(userPayload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200,"Validate create post request");
        logger.info("**Post Method done");
    }
  @Test(priority=2, dependsOnMethods = "testPostUser")
    public void testGetUserByName(){
      logger.info("**get Method");
       Response response =  UserEndPoints.readUser(userPayload.getUsername());
       response.then().log().all();
       Assert.assertEquals(response.getStatusCode(),200,"validate get  request");
      logger.info("**Get Method Done");
  }
  @Test(priority =3,dependsOnMethods = "testPostUser")
    public void testUpdateUserByName(){
      logger.info("**Put Method");
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());


        Response response = UserEndPoints.updateUser(userPayload.getUsername(),userPayload);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200, "validating update request");
      logger.info("**Put Method Done");
  //checking data after update

  }

    @Test(priority =4,dependsOnMethods = "testPostUser")
    public void testDeleteUserByName(){
        logger.info("**delete Method");
        Response response = UserEndPoints.deleteUser(userPayload.getUsername());
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200, "validating delete request");
        logger.info("**delete Method done");
    }

}
