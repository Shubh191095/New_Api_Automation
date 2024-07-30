package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;
//Below are the 2 static package from the rest assured
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;



public class TC_VideoGameAPI {
	//Try to run all 5 types of API
	//1st is GET request
	//Implementation notes->Returns all video games in the DB
	
	@Test (priority=1)//Test annotation Also add test NG libraries
	public void test_getAllVideoGames()//Test NG Method And its name
	{
		given ()  //given session we don't have any prereuiset
		
		.when()   //Inside when we send get request
		       .get("http://localhost:8080/app/videogames")//This URL gives some response
		//Inside then part will give the validation
		.then()
		.statusCode(200);	//Validating the status code //Should be 200
		//This is 1st method will give all the video games
	} 
//===============================================================================================	
	//POST Request
	//Implementation notes->Add a new video game to the DB
    //In this case Body is present so we need to add in Hash Map
	
	//(In Java, a HashMap stores key-value pairs and allows efficient retrieval based on keys. 
	//It supports different data types for keys and values, including char and int,
	//wrapped in their respective wrapper classes.)
	
	@Test (priority=2)
	public void test_addNewVideoGame() //Test method for POST
	{
		//If we have add new video game so we have add some data 
		//So specify the data we can create the one Hash Map variable
		HashMap data =new HashMap (); //Hash Map variable (Import Hash map from java.util)
			  data.put("id", "100");
			  data.put("name", "shubham");
			  data.put("releaseDate", "2024-07-24T07:52:17.994Z");
			  data.put("reviewScore", "5");
			  data.put("category", "Adventure");
			  data.put("rating", "Universal");
			  
			  Response res=//For storing the response inside the variable (Contains the response)
					  given ()//In given session we need to specify prerequisite
					  .contentType ("application/json")//For sending the date and there format//In GET not present because absences of body
		              .body (data) //Also attached the body is data mention in hashmap
		.when()//Going the send the request
		.post ("http://localhost:8080/app/videogames")//Inside the post we need to send specified URL
		//After sending this URL we can verify specify few things
		.then ()
		.log().body()//Log the response on the console window
		//Logging: .log().body() is used for debugging and checking the response content.
		.extract().response();//Verify the response
			  //This method extract the response 
			  //And that response store in above given variable (Response res=)
			  
			  //creating the string variable
			  String jsonString=res.asString();//This will convert the response into the string format
			  //In response we are expecting the status code "Record Added Successfully"
			  Assert.assertEquals(jsonString.contains("Record Added Successfully"),true);
	}
//===============================================================================================	
	//GET Request
		//Implementation notes->Returns the details of single game by id
	
	@Test (priority=3)
	public void test_getVedeoGame() //Test method for GET
	{
		given ()//If we don't have in given then just avoid
		.when()
		.get ("http://localhost:8080/app/videogames/100")
		.then()
		.statusCode(200)//Status Code Validations
		.log().body()//
		.body("videoGame.id",equalTo("100"))//"videoGame.id" this is Tag/Path--100(Excepting)
		.body("videoGame.name",equalTo("shubham"));//videoGame.name this is Tag/Path --shubham(Excepting)
	}	
//================================================================================================	
	//PUT Request
	//Implementation notes-Update the existing record smiler to the post request
	//By using this request we are able to edit the existing record from the video game Data Base
	
	@Test (priority=4)
	public void test_UpdateVideoGame() //Test method for PUT
	{
	HashMap data =new HashMap ();
	//Same record update the game record
	  data.put("id", "100");
	  data.put("name", "rahate");
	  data.put("releaseDate", "2024-07-24T07:52:17.994Z");
	  data.put("reviewScore", "4");
	  data.put("category", "Adventure");
	  data.put("rating", "Universal");
	  
	  given()//In given we need to specify two things content type and body
	  .contentType("application/json")//What type of contend we are sending -jSon format
	  .body(data)//Update the body
	  .when()
	  .put("http://localhost:8080/app/videogames/100")//URL for PUT Request//Specify which record ww will updated
	  .then()
	  .statusCode(200)
	  .log().body()//Print the update data in the console
	  .body("videoGame.id",equalTo("100"))//This two updated think should be part of our body
	  .body("videoGame.name",equalTo("rahate"));
	  //This will go and update the record
	  
	}
//===============================================================================================	
	//For Delete Request
	//Implementation notes-Delete the video game from the DB By ID
	@Test(priority=5)
	public void test_DeleteVideoGame()
	{
		Response res =  //Response variable
		//send the Delete request
		given()//inside the given do don't need anything
		.when()//Inside the when we send the delete request
		.delete("http://localhost:8080/app/videogames/100")//Send the Delete URL and 100 is a video game id
		
		//For validation
		.then()
		.statusCode(200)//Validate this
		.log().body()//Also log the body
		//Validate the message "Record Deleted Successfully" which is present in my Response
		//So check this message store the response inside one variable so
		.extract().response(); //This response store into the variable (Response res =)
		
		//Convert response into the string format
		String jsonString=res.asString();//Purpose: This line converts the response object res to a string format.
		Assert.assertEquals(jsonString.contains("Record Deleted Successfully"),true);
			//Assertion use for Actual we are expecting is -> "Record Deleted Successfully"
		//Purpose: This line asserts that the response body contains a specific text, "Record Deleted Successfully".
	}
	}
