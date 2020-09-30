import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.json.Json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.agogs.languagelayer.api.APIConsumer;
import com.github.agogs.languagelayer.api.LanguageLayerAPIConsumer;
import com.github.agogs.languagelayer.model.APIResult;
import com.github.agogs.languagelayer.model.Batch;
import com.github.agogs.languagelayer.model.QueryParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject;



public class GoogleSearch {

	public static void main(String[] args) throws IOException {
		
		//File file = new File("C:\\Selenium\\chromedriver.exe");
		
		HashMap<String, Integer> web_index = new HashMap<String, Integer>();

		//System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
		
		
		WebDriver driver = new ChromeDriver();
		
		var url = "https://www.cpp.edu/";
		
		//JSONObject obj = new JSONObject();
		APIConsumer con = new LanguageLayerAPIConsumer("http://api.languagelayer.com", "7ec8de2ed45584cbb1ea0fbf6c6f5ae0");
		QueryParams params = new QueryParams().query("I am fine?");
		APIResult result = con.detect(params);
		System.out.println(new ObjectMapper().writeValueAsString(result.getResults().get(0)));
		//System.out.println(new ObjectMapper().writeValueAsString(result.getResults()));
		
		
		

		//driver.get(url);
		
		//String context = driver.getPageSource(); 
		//System.out.println(context);

		/* single web link
		WebElement elementName = driver.findElement(By.xpath("//a"));
		System.out.println("value of href attribute :" + elementName.getAttribute("href"));
		*/
		
		/* Multiple web links
		List<WebElement> elementNames = driver.findElements(By.xpath("//a"));
		
		for(int i=0; i< elementNames.size(); i++) {
			
			System.out.println("Crawling Result :"+ i + "--" + elementNames.get(i).getAttribute("href"));
		}
		*/
		
		/*
		File file = new File("D://CPP//CS5180_IR//repository1.txt");
		  
		//Create the file
		try {
			if (file.createNewFile())
			{
			    System.out.println("File is created!");
			} else {
			    System.out.println("File already exists.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		Queue<String> queue = new LinkedList<>();
		var count = 0;
		
		
		queue.add(url);

		//System.out.println(queue.element());		
		

		while(!queue.isEmpty() && count <= 3) {
			
			//System.out.println("51561561561");
			
			var size = queue.size();
			
			while(size-- != 0) {
				
				String temp_url = queue.element();
				var count02 = 0;  //RCHDBG
				
				queue.remove();
				
				driver.get(temp_url);
				
				System.out.println("Current Crawling Website : " + temp_url);
				
				List<WebElement> elementNames = driver.findElements(By.xpath("//a"));
				
				for(int i=0; i< elementNames.size() && count02 < 3; i++) {
					
					try {
						queue.add(elementNames.get(i).getAttribute("href"));
						System.out.println("Crawling Result :"+ i + "--" + elementNames.get(i).getAttribute("href"));
					}
					catch(Exception e) {
						System.out.println("EXCEPT");
					}
					
					count02++;

				}
				
				web_index.put(temp_url, elementNames.size());
			}
			
			count++;

		}
		
		
		File file = new File("D:\\CPP\\CS5180_IR\\project01\\report.csv");
		FileWriter csvWriter = new FileWriter(file);
		
		
		for (Entry<String, Integer> entry : web_index.entrySet()) {
		    String key = entry.getKey();
		    Integer val = entry.getValue();
		    
		    csvWriter.append(key);
		    csvWriter.append(",");
		    
			csvWriter.append(String.valueOf(val));
			
			csvWriter.append("\n");
		    
		}
		
		csvWriter.flush();
		csvWriter.close();
		


		//driver.close();
		//driver.quit();

	}
	
	

}



