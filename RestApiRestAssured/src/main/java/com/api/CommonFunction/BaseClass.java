package com.api.CommonFunction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseClass {

	public static ExtentReports report;
	public static ExtentTest logger;
	public String	TruncatePath;

	@BeforeSuite
	public void initialize()
	{
		updateConfig();
	}
	
	
	@BeforeTest
	public static void settings() {
		report = new ExtentReports("C:\\Users\\jmanera\\Documents\\Document\\Framework\\API_Reports\\API.html", true);

		logger = new ExtentTest("API Test", "Rest API Testing");

	}

	@AfterMethod
	public static void end() {
		report.endTest(logger);
		report.flush();

	}

	@AfterSuite
	public static void ending() throws Exception {

		report.close();
		replaceTextInReportFile();
		//sendEmail();
	}

	public static String getScreenShot(WebDriver wb, String screenShotName) throws IOException {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());

		TakesScreenshot scrShot = ((TakesScreenshot) wb);

		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

		String destination = System.getProperty("user.dir") + "\\Screenshot\\" + screenShotName + date + ".png";
		File DestFile = new File(destination);

		FileUtils.copyFile(SrcFile, DestFile);
		return destination;
	}

	private static void replaceTextInReportFile() {
		try {

			File htmlContent = new File("C:\\Users\\jmanera\\Documents\\Document\\Framework\\API_Reports\\API.html");
			Document doc = Jsoup.parse(htmlContent, "utf-8");
			doc.select("a").first().remove();
			doc.select("a").first().before("<a><span> API Report </span></a>");

			Elements ReportHeader = doc.getElementsByAttributeValue("class", "report-name");
			for (Element header : ReportHeader) {
				header.text("API Execution Report");
			}

			String project = "API";
			Element ReportHeader1 = doc.getElementsByTag("h5").first();

			ReportHeader1.html("<h5><b>" + project + " Test</b></font></h5>");

			Elements ReportHeader3 = doc.getElementsContainingOwnText("ExtentReports 2.0");
			for (Element header : ReportHeader3) {
				header.text("API  Report");
			}

			Elements ReportHeader2 = doc.getElementsContainingOwnText("v2.41.1");
			for (Element header : ReportHeader2) {
				header.text("v1.0");
			}

			String html = doc.html();
			Writer writer = new PrintWriter("C:\\Users\\jmanera\\Documents\\Document\\Framework\\API_Reports\\API.html");

			writer = new BufferedWriter(writer);
			writer.write(doc.html());
			writer.close();

		} catch (Exception e) {
			logger.log(LogStatus.ERROR, " Failed! -- " + e.getMessage().substring(0, 150));
		}
	}
	
	private static void sendEmail() {

		EmailReport er = new EmailReport();
				er.sendEmailWithReports();
			
	}
	
	
	public void updateConfig()
	{
		 String directorypath=System.getProperty("user.dir");
			directorypath=directorypath.replace("\\","/");
			String [] Path=directorypath.split("/Users");
			
	TruncatePath=Path[0];
	}
	
	
	
	
	
	}

