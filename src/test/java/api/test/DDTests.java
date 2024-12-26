package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;
/*If Dataprovider is in same package then no need to mention dataProviderClass, since
 it is in different package we are mentioning dataProviderClass and import statement */
public class DDTests {

	@Test(priority=1, dataProvider="Data", dataProviderClass=DataProviders.class)
	public void testPostuser(String userID, String userName, String fname, String lname, String useremail, String pwd, String phone)
// the order mentioned in the parameters should be same as in excel sheet	
	{
		User userPayload=new User();
		
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(userName);
		userPayload.setFirstname(fname);
		userPayload.setLastname(lname);
		userPayload.setEmail(useremail);
		userPayload.setPassword(pwd);
		userPayload.setPhone(phone);
		
		Response res = UserEndPoints.createUser(userPayload);
		Assert.assertEquals(res.getStatusCode(),200);
		
	}
	
	@Test(priority=2, dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void testDeleteUserByName(String userName)
	{
		Response res = UserEndPoints.deleteUser(userName);
		Assert.assertEquals(res.getStatusCode(),200);
	}
}
