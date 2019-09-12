package com.api.CommonFunction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;

public class ReadJsonFile {
	public static List<String> categories = new ArrayList<String>();
	public static List<String> categories_firstdata = new ArrayList<String>();

	@SuppressWarnings("unchecked")
	public static List<String> readJsonFileDynamic(String filePath, String jsonPath) {
		
		System.out.println("jsonpath - "+jsonPath);
		try 
		{
			
			String content = new String(Files.readAllBytes(Paths.get(filePath)));
			Configuration conf = Configuration.builder()
		            .jsonProvider(new GsonJsonProvider())
		            .mappingProvider(new GsonMappingProvider())
		            .build();
		
			DocumentContext context = JsonPath.using(conf).parse(content);
			
			categories = context.read(jsonPath, List.class);//List<String> 
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} 
		return categories;
		
	}
	
	
	
	
	
	
	public static String readJsonFileDynamic_firstentry(String filePath, String jsonPath) {
		String category_firstmatch="";
		try{
			categories_firstdata=readJsonFileDynamic(filePath, jsonPath);
			category_firstmatch=categories_firstdata.get(0).toString();
			
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			System.out.println("Jsonerror");
		} 
		return category_firstmatch;
	}	
	
	
	
	
	
	
	
	
	
	
	
}
