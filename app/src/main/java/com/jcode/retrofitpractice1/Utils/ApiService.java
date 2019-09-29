package com.jcode.retrofitpractice1.Utils;

import com.jcode.retrofitpractice1.Models.Fact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by otimj on 9/28/2019.
 */
public interface ApiService {
	String BASE_URL = "https://cat-fact.herokuapp.com" + "/facts/";

	/**
	 * Method that gets One (1) random Fact from the server.
	 * @return Fact
	 */
	@GET("random")
	Call<Fact> getRandomFact();

	/**
	 * Method that gets One (1) random Fact from the server.
	 * Used to simulate server error due to bad request
	 * @return Fact
	 */
	@GET("randoms")
	Call<Fact> getRandomFactWithError();

	/**
	 * This method gets a list of random facts based on the passed parameters.
	 * @param amount Number of Facts to retrieve. If set to one, response will be a fact object. If many, response will be an array of Facts (Should be greater than 1 but less than or equal to 500)
	 * @param animalType  Type of animal the fact will describe. It is a Comma-separated String
	 * @return List of Facts
	 */
	@GET("random")
	Call<List<Fact>> getRandomFacts(@Query("amount") int amount, @Query("animal_type")String animalType);

	/**
	 * This method gets a random fact based on the passed parameters.
	 * @param animalType  Type of animal the fact will describe. It is a Comma-separated String
	 * @return Fact
	 */
	@GET("random?amount=1")
	Call<Fact> getRandomFact(@Query("animal_type")String animalType);

	/**
	 * This method returns a fact based on id passed in the path
	 * @param id Id of the fact
	 * @return Fact
	 */
	@GET("{id}")
	Call<Fact> getFactById(@Path("id") String id);
}