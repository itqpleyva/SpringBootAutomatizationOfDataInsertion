# SpringBootAutomatizationOfDataInsertion
Automation of a data insertion process consuming a rest service and using an excel file as a data source

Main dependencies:

          <parent>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-parent</artifactId>
              <version>2.2.2.RELEASE</version>
              <relativePath/>
          </parent>
          <properties>
              <java.version>1.8</java.version>
              <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
          </properties>
           <dependencies>
           <dependency>
              <groupId>org.apache.poi</groupId>
              <artifactId>poi</artifactId>
              <version>3.15</version>
          </dependency>
          <dependency>
              <groupId>org.apache.poi</groupId>
              <artifactId>poi-ooxml</artifactId>
              <version>3.15</version>
          </dependency>
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-web</artifactId>
            </dependency>
           </dependencies>
           
  ReportModel class

        public class ReportModel {

        private int id;
        private String user_name;
        private String report_body;
        private String report_area;
	
        // getters and setters
        
  Main Code
  
            @SpringBootApplication
          public class Main implements CommandLineRunner{

            public static void main(String[] args) {

              SpringApplication.run(Main.class, args).close();

            }

            public void run(String... args) throws IOException {
            
              FileInputStream file = new FileInputStream(new File("datos.xlsx")); //Defining data source

              @SuppressWarnings("resource")
              Workbook workbook = new XSSFWorkbook(file);

              Sheet sheet = workbook.getSheetAt(0); // Getting first page of excel

              String[] keys = {"id","user_name","report_body","report_area"}; //Definig String array with excel columns name, it can be done reading the first excel row

              ReportModel report = new ReportModel();// creating Report Model Instance

              String reporte = "";
              int cont=0;//defining count for logs enumeration
              int j = 0;//defining count to iterate over keys array
              int rc =0;//defining count to exclude first row

              for (Row row : sheet) {  //iterating every row

                for (Cell cell : row) { //iterating every cell in the actual row

                    if (rc != 0) { //excluding first row because contain columns names

                      if (cell.getCellTypeEnum().toString().equals("STRING")) {//getting the value of every String cell


                        if (keys[j].equals("user_name")) {						
                          report.setUser_name(cell.getStringCellValue());//setting the user_name

                      }	
                        if (keys[j].equals("report_body")) {
                          report.setReport_body(cell.getStringCellValue());
                      }
                        if (keys[j].equals("report_area")) {						

                          report.setReport_area(cell.getStringCellValue());
                      }	

                      }

                      if (cell.getCellTypeEnum().toString().equals("NUMERIC")) { //getting the value of every Numeric cell

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
                    String resultado = Insertions(report);	// calling the method to insert data using REST SERVICE	    
                    
                    String b = cont+"-ReportModel:---user:  "+report.getUser_name()+"  Request response Code: "+resultado;	//creating a log of insertion results	    	
                    reporte = reporte +"\n"+ b;
                }
                  rc=rc+1;
            }
                      FileWriter fileWriter = new FileWriter("resultados.txt", true);//saving insertions logs in local file resultados.txt
                      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                      bufferedWriter.write(reporte + "\n");
                      bufferedWriter.flush();
                      bufferedWriter.close();
                      fileWriter.close();	
            }

            public String Insertions(ReportModel o) {

              String status = "";
              RestTemplate restTemplate = new RestTemplate();
              String fooResourceUrl
                = "https://endpoint_to_insert_data";// put your real endpoint to insert data
              HttpHeaders headers = new HttpHeaders();
              headers.setContentType(MediaType.APPLICATION_JSON);
              headers.add("Accept", "application/json");
              headers.add("Authorization", "TOKEN");//if endpoint is secured with access token
              HttpEntity<ReportModel> request = new HttpEntity<ReportModel>(o,headers);
              try {
                ReportModel p = restTemplate.postForObject(fooResourceUrl, request, ReportModel.class);

              } catch (HttpStatusCodeException exception) {

                 status = String.valueOf(exception.getStatusCode().value());
              }		
              return status;
              }
          }
          
 Data Source Excel
        
                id user_name	 report_body	                             report_area
                1	 user_name2	 this is the report body of reportmodel 1	 IT
                2	 user_name3	 this is the report body of reportmodel 2	 IT
                3	 user_name4	 this is the report body of reportmodel 3	 OS
                4	 user_name5	 this is the report body of reportmodel 4	 OS

Results logs txt

                1-ReportModel:---user:  user_name2  Request response Code: 200 
                2-ReportModel:---user:  user_name3  Request response Code: 200 
                3-ReportModel:---user:  user_name4  Request response Code: 200 
                4-ReportModel:---user:  user_name5  Request response Code: 200 
                
