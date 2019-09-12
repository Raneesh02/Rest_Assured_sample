package com.api.CommonFunction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.google.gson.JsonObject;
import com.ost.OSTSDK;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class CommonAPIFunction {
	public static OSTSDK ostObj;

	public static com.ost.services.v1_1.Manifest services;
	private static ExtentTest logger = null;

	public JsonObject response;

	public HashMap<String, Object> params;
	
	
	public static String FILENAME=null;
	

	public CommonAPIFunction(ExtentTest logger) {
		this.logger = logger;
		getServices();
	}
	public void  getServices()
	{
		HashMap<String, Object> sdkConfig = new HashMap<String, Object>();
       /* sdkConfig.put("apiEndpoint", ReadJsonFile.readJsonFileDynamic_firstentry(" C:\\Users\\jmanera\\Documents\\Document\\Framework\\API_Projects\\API_Data\\APITestData.json", "$..apiEndpoint")); 
        sdkConfig.put("apiKey",  ReadJsonFile.readJsonFileDynamic_firstentry(" C:\\Users\\jmanera\\Documents\\Document\\Framework\\API_Projects\\API_Data\\APITestData.json", "$..apiKey"));
        sdkConfig.put("apiSecret",ReadJsonFile.readJsonFileDynamic_firstentry(" C:\\Users\\jmanera\\Documents\\Document\\Framework\\API_Projects\\API_Data\\APITestData.json", "$..apiSecret")); */
		
	        sdkConfig.put("apiEndpoint", "https://sandboxapi.ost.com/v1.1"); 
	        sdkConfig.put("apiKey", "61dd6ba61166026976fa"); // replace with the API Key you obtained earlier
	        sdkConfig.put("apiSecret", "56f316df720e7c75c75066612b4f66961df22a18c95bf56de951da17b4089156"); // replace with the API Secret you obtained earlier
       
        OSTSDK ostObj = new OSTSDK(sdkConfig);
        services = (com.ost.services.v1_1.Manifest) ostObj.services;
      
	}
	
	
	
	
	public void createUser(String name,String FileName) throws Exception 
	{
		logger.log(LogStatus.PASS, "Execution for Create User API has started");
		com.ost.services.v1_1.Users userService = services.users;
		params = new HashMap<String, Object>();
		params.put("name", name);
		try {
			response = userService.create(params);
		} catch (IOException e) {

			logger.log(LogStatus.FAIL, " " + e.getMessage());
		}
		WriteAPI_Response_to_Jsonfile(FileName, response.toString());
		
		
	}
	
	
	
	public static void WriteAPI_Response_to_Jsonfile(String FileName,String API_ResponseData) throws Exception {
		 FILENAME="C:\\Users\\jmanera\\Documents\\Document\\Framework\\API_Projects\\API_Data\\"+FileName+".json";
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
