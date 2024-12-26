package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.payload.User;
import io.restassured.response.Response;
import api.endpoints.UserEndPoints_usingProperties;

public class UserTests_usingProperties {

	Faker faker;
	User userPayload;
	
	@BeforeClass
	public void setupData()
	{
		faker=new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setFirstname(faker.address().firstName());
		userPayload.setLastname(faker.address().lastName());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
				
	}
	
	@Test(priority=1)
	public void testPostUser()
	{
		Response res = UserEndPoints_usingProperties.createUser(userPayload);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(),200);
		
	}
	
	@Test(priority=2)
	public void testGetUserByName()
	{
		Response res = UserEndPoints_usingProperties.readUser(userPayload.getUsername());
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(),200);
	}
	
	@Test(priority=3, dependsOnMethods= {"testPostUser"})
	public void testUpdateUserByName()
	{
		// update data using payload
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setFirstname(faker.address().firstName());
		userPayload.setLastname(faker.address().lastName());
		
		Response res = UserEndPoints_usingProperties.updateUser(userPayload.getUsername(),userPayload);
		res.then().log().all();
		
		res.then().log().body().statusCode(200);
//		Assert.assertEquals(res.getStatusCode(),200);
//		res.then().log().body();
		
		//checking data after update
		Response responseAfterUpdate = UserEndPoints_usingProperties.readUser(userPayload.getUsername());
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		
	}
	
	@Test(priority=4)
	public void testDeleteUserByName()
	{
		Response res = UserEndPoints_usingProperties.deleteUser(userPayload.getUsername());
//		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(),200);
	}
}
