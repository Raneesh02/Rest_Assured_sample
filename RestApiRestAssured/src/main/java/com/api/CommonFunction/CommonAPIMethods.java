package com.api.CommonFunction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.apache.xpath.operations.And;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class CommonAPIMethods {
	
	private static ExtentTest logger = null;
	public static String FILENAME;
	public CommonAPIMethods(ExtentTest logger) {
		this.logger = logger;
	}
	
	public static String POST_API_Request(String URI,String header_content_type,
			String request_body, String methodName,String FileName) throws Exception {
		logger.log(LogStatus.PASS, "Execution for " + methodName + " has started");
		Response API_response = RestAssured.given()
				.config(RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())).header("Content-Type",header_content_type).and().header("username","ashikalimaner+1@gmail.com")
				.and().header("password","Vahidamaner@123").post(URI);
		if (API_response.getStatusCode() == 200) {
			logger.log(LogStatus.PASS, "ResponseCode for API is " + API_response.getStatusCode());
			
		} else {
			throw new Exception("ResponseCode for API is " + API_response.getStatusCode());
		}
		String APIResponse = API_response.asString();
		WriteAPI_Response_to_Jsonfile(FileName,APIResponse);
		logger.log(LogStatus.INFO, "<object type='application/json' data='" + FILENAME
				+ "' width='100%' height='100%' JSON.stringify(data)></object>");
		return API_response.asString();
	}
	public static void WriteAPI_Response_to_Jsonfile(String FileName,String API_ResponseData) throws Exception {
		String FILENAME="C:\\Users\\jmanera\\Documents\\Document\\Framework\\API_Projects\\API_Data\\"+FileName+".json";
		logger.log(LogStatus.INFO, "<object type='application/json' data='" + FILENAME
				+ "' width='100%' height='100%' JSON.stringify(data)></object>");
		File file = new File(FILENAME);
		if (file.exists()) {
			file.delete();
		} else {
			file.createNewFile();
		}
		FileWriter writer = new FileWriter(FILENAME);
		BufferedWriter buffer = new BufferedWriter(writer);
		buffer.write(API_ResponseData);
		buffer.close();
		
	}


}
