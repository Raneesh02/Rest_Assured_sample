package RestApiUsingRestAssured.RestApiRestAssured;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class AppTest {

	@Test
	public void VerifyCityInJsonResponse() {
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/Hyderabad");

		// First get the JsonPath object instance from the Response interface
		JsonPath jsonPathEvaluator = response.jsonPath();

		// Then simply query the JsonPath object to get a String value of the
		// node
		// specified by JsonPath: City (Note: You should not put $. in the Java
		// code)
		String city = jsonPathEvaluator.get("City");

		// Let us print the city variable to see what we got
		System.out.println("City received from Response " + city);

		// Validate the response
		Assert.assertEquals(city, "Hyderabad", "Correct city name received in the Response");

	}

	@Test
	public void DisplayAllNodesInWeatherAPI() {
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/Hyderabad");

		System.out.println("Response" + response.asString());

		// First get the JsonPath object instance from the Response interface
		JsonPath jsonPathEvaluator = response.jsonPath();

		// Let us print the city variable to see what we got
		System.out.println("City received from Response " + jsonPathEvaluator.get("City"));

		// Print the temperature node
		System.out.println("Temperature received from Response " + jsonPathEvaluator.get("Temperature"));

		// Print the humidity node
		System.out.println("Humidity received from Response " + jsonPathEvaluator.get("Humidity"));

		// Print weather description
		System.out.println("Weather description received from Response " + jsonPathEvaluator.get("Weather"));

		// Print Wind Speed
		System.out.println("City received from Response " + jsonPathEvaluator.get("WindSpeed"));

		// Print Wind Direction Degree
		System.out.println("City received from Response " + jsonPathEvaluator.get("WindDirectionDegree"));
	}

	@Test
	public void RegistrationSuccessful() {
		RestAssured.baseURI = "http://restapi.demoqa.com/customer";
		RequestSpecification request = RestAssured.given();

		JsonObject requestParams = new JsonObject();
		requestParams.addProperty("FirstName", "xxxxxxxxxxr"); // Cast
		requestParams.addProperty("LastName", "Singh");
		requestParams.addProperty("UserName", "sdimp2f2342xxxxxxxx3r2dd2011");
		requestParams.addProperty("Password", "passworxxxxd1");

		requestParams.addProperty("Email", "samplexxxxxxxxxxxxxxxxxxd9@gmail.com");
		System.out.println(requestParams.getAsString());
		request.body(requestParams.getAsString());
		Response response = request.post("/register");
		System.out.println(response.asString());
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 201);
		String successCode = response.jsonPath().get("SuccessCode");
		Assert.assertEquals(successCode, "OPERATION_SUCCESS", "Correct Success code was returned");
	}

	@Test
	public void RegistrationSuccessfullDeserializingJson() {
		RestAssured.baseURI = "http://restapi.demoqa.com/customer";
		RequestSpecification request = RestAssured.given();

		JsonObject requestParams = new JSONObject();
		requestParams.put("FirstName", "Virender"); // Cast
		requestParams.put("LastName", "Singh");
		requestParams.put("UserName", "63userf2d3d2011");
		requestParams.put("Password", "password1");
		requestParams.put("Email", "ed26dff39@gmail.com");

		request.body(requestParams.toJSONString());
		Response response = request.post("/register");

		ResponseBody body = response.getBody();
		System.out.println(response.body().asString());

		if (response.statusCode() == 200) {
			// Deserialize the Response body into RegistrationFailureResponse
			RegistrationFailureResponse responseBody = body.as(RegistrationFailureResponse.class);

			// Use the RegistrationFailureResponse class instance to Assert the
			// values of
			// Response.
			Assert.assertEquals("User already exists", responseBody.FaultId);
			Assert.assertEquals("FAULT_USER_ALREADY_EXISTS", responseBody.fault);
		} else if (response.statusCode() == 201) {
			// Deserialize the Response body into RegistrationSuccessResponse
			RegistrationSuccessResponse responseBody = body.as(RegistrationSuccessResponse.class);
			// Use the RegistrationSuccessResponse class instance to Assert the
			// values of
			// Response.
			Assert.assertEquals("OPERATION_SUCCESS", responseBody.SuccessCode);
			Assert.assertEquals("Operation completed successfully", responseBody.Message);
		}
	}
}

class RegistrationFailureResponse {

	String FaultId;
	String fault;
}

class RegistrationSuccessResponse {

	// Variable where value of SuccessCode node
	// will be copied
	// Note: The name should be exactly as the node name is
	// present in the Json
	public String SuccessCode;

	// Variable where value of Message node will
	// be copied
	// Note: The name should be exactly as the node name is
	// present in the Json
	public String Message;
}