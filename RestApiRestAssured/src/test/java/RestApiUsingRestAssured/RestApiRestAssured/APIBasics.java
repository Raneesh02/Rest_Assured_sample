package RestApiUsingRestAssured.RestApiRestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class APIBasics {
	{
		RestAssured.baseURI = "https://reqres.in/";
	}

	@Test
	public void getListOfUsers() {

		Response response = given().pathParams("type", "api", "username", "users").queryParam("page", "2")
				.get("/{type}/{username}");
		response.then().body("per_page", equalTo(3)).and().body("page", equalTo(2));
		JsonPath jsonEvaluator = response.jsonPath();
		System.out.println(jsonEvaluator.get("data"));
		List<Object> dataArray = jsonEvaluator.getList("data");
		List<String> ids = jsonEvaluator.get("data.id");
		List<String> avatarAll = jsonEvaluator.get("data.avatar");

		for (String string : avatarAll) {
			Assert.assertEquals(string.startsWith("https://s3.amazonaws.com/uifaces/faces/twitter/"), true);
		}
		Assert.assertEquals(dataArray.size(), 3);
		Assert.assertEquals(ids.size(), 3);

	}

	@Test(dataProvider = "singlUserData")
	public void getSingleUser(String id) {
		Response response = given().pathParams("type", "api", "username", "users", "id", id)
				.get("/{type}/{username}/{id}");
		response.then().statusCode(200);
		JsonPath jsonEvaluator = response.jsonPath();
		System.out.println(jsonEvaluator.get("data.first_name"));
	}

	@Test
	public void getSingleUserNotFound() {
		Response response = given().pathParams("type", "api", "username", "users", "id", "23")
				.get("/{type}/{username}/{id}");
		response.then().statusCode(404);
	}

	@Test(dataProvider = "singlUserData")
	public void postSingleUser(String id) {
		JsonObject createUserDetails = new JsonObject();
		createUserDetails.addProperty("name", "morpheus" + id);
		createUserDetails.addProperty("job", "leader" + id);
		Response response = given().pathParams("type", "api", "username", "users").body(createUserDetails.toString())
				.post("/{type}/{username}");
		response.then().statusCode(201);
	}

	@Test(dataProvider = "singlUserData")
	public void updateSingleUser(String id) {
		int userId = 2;
		JsonObject createUserDetails = new JsonObject();
		createUserDetails.addProperty("name", "morpheus");
		createUserDetails.addProperty("job", "leader" + id);
		Response response = given().pathParams("type", "api", "username", "users", "userId", userId)
				.body(createUserDetails.toString()).put("/{type}/{username}/{userId}");
		System.out.println(response.asString());
		JsonPath resPath = response.jsonPath();
		Date todayDate = Calendar.getInstance().getTime();
		SimpleDateFormat s = new SimpleDateFormat("YYYY-MM-dd");
		resPath.getString("updatedAt").startsWith(s.format(todayDate));
		response.then().statusCode(200);
	}

	@Test(dataProvider = "singlUserData")
	public void patchSingleUser(String id) {
		int userId = 2;
		JsonObject createUserDetails = new JsonObject();
		createUserDetails.addProperty("name", "morpheus");
		createUserDetails.addProperty("job", "leader" + id);
		Response response = given().pathParams("type", "api", "username", "users", "userId", userId)
				.body(createUserDetails.toString()).patch("/{type}/{username}/{userId}");
		System.out.println(response.asString());
		JsonPath resPath = response.jsonPath();
		Date todayDate = Calendar.getInstance().getTime();
		SimpleDateFormat s = new SimpleDateFormat("YYYY-MM-dd");
		resPath.getString("updatedAt").startsWith(s.format(todayDate));
		response.then().statusCode(200);
	}

	@Test(dataProvider = "singlUserData")
	public void deleteSingleUser(String id) {
		Response response = given().pathParams("type", "api", "username", "users", "userId", id)
				.delete("/{type}/{username}/{userId}");
		response.then().statusCode(204);
	}

	@Test()
	public void postRegisterUser() {
		JsonObject createUserDetails = new JsonObject();
		createUserDetails.addProperty("email", "eve.holtaaaaaaaaa@reqres.in");
		createUserDetails.addProperty("password", "pistol");
		System.out.println(createUserDetails.toString());
		Response response = given().pathParams("type", "api", "resource", "register").body(createUserDetails.toString())
				.post("/{type}/{resource}");
		System.out.println(response.asString());
		response.then().statusCode(200);
	}

	@Test()
	public void postLoginUser() {
		JsonObject createUserDetails = new JsonObject();
		createUserDetails.addProperty("email", "eve.holtaaaaaaaaa@reqres.in");
		createUserDetails.addProperty("password", "cityslicka");
		System.out.println(createUserDetails.toString());
		Response response = given().pathParams("type", "api", "resource", "login").body(createUserDetails.toString())
				.post("/{type}/{resource}");
		System.out.println(response.asString());
		response.then().statusCode(200);
	}

	@DataProvider
	public Object[] singlUserData() {
		Object[] a = { "1", "2", "3", "4", "5" };
		// Object[] a = { "1" };
		return a;
	}

	public static void main(String[] args) throws ParseException {
		Date todayDate = Calendar.getInstance().getTime();
		SimpleDateFormat s = new SimpleDateFormat("YYYY-MM-dd");
		System.out.println(s.format(todayDate));

	}
}
