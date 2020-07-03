package Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import Main.Model.ReportModel;



@SpringBootApplication
public class Main implements CommandLineRunner{

	public static void main(String[] args) {
		
		SpringApplication.run(Main.class, args).close();

	}

	public void run(String... args) throws IOException {
		FileInputStream file = new FileInputStream(new File("datos.xlsx"));

		@SuppressWarnings("resource")
		Workbook workbook = new XSSFWorkbook(file);
		
		Sheet sheet = workbook.getSheetAt(0);
		 	
		String[] keys = {"id","user_name","report_body","report_area"};
		
		ReportModel report = new ReportModel();
		
		String reporte = "";
		int cont=0;
		int j = 0;
		int rc =0;

		for (Row row : sheet) {  
			
	    for (Cell cell : row) {
	
		    	if (rc != 0) {

			    	if (cell.getCellTypeEnum().toString().equals("STRING")) {
			    		
		    		
		    			if (keys[j].equals("user_name")) {						
		    				report.setUser_name(cell.getStringCellValue());

						}	
	    				if (keys[j].equals("report_body")) {
		    				report.setReport_body(cell.getStringCellValue());
						}
		    			if (keys[j].equals("report_area")) {						

		    				report.setReport_area(cell.getStringCellValue());
						}	
												
			    	}
			    	
			    	if (cell.getCellTypeEnum().toString().equals("NUMERIC")) {
			    			
		    			if (keys[j].equals("id")) {
		    				report.setId( (int) cell.getNumericCellValue());
						}
			    			
			    	}
			    	j=j+1;
				}
		    	
		    }	
		    j=0;
		    if (rc!=0) {
		    	cont=cont+1;
		    	String resultado = Insertions(report);		    	
		    	String b = cont+"-ReportModel:---user:  "+report.getUser_name()+"  Request response Code: "+resultado;		    	
		    	reporte = reporte +"\n"+ b;
			}
		    rc=rc+1;
	}
		  	    FileWriter fileWriter = new FileWriter("resultados.txt", true);
		  	    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		  	    bufferedWriter.write(reporte + "\n");
		  	    bufferedWriter.flush();
		  	    bufferedWriter.close();
		  	    fileWriter.close();	
	}
	
	public String Insertions(ReportModel o) {
		
		String status = "";
	/*	RestTemplate restTemplate = new RestTemplate();
		String fooResourceUrl
		  = "https://endpoint_to_insert_data";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Accept", "application/json");
		headers.add("Authorization", "TOKEN");
		HttpEntity<ReportModel> request = new HttpEntity<ReportModel>(o,headers);
		try {
			ReportModel p = restTemplate.postForObject(fooResourceUrl, request, ReportModel.class);
			
		} catch (HttpStatusCodeException exception) {
			
		   status = String.valueOf(exception.getStatusCode().value());
		   
		}		*/
		return status;
		}
}
