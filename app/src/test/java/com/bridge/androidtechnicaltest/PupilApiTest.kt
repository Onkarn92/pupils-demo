package com.bridge.androidtechnicaltest

import com.bridge.androidtechnicaltest.models.Pupil
import com.bridge.androidtechnicaltest.models.PupilsResponse
import com.bridge.androidtechnicaltest.network.PupilApi
import com.bridge.androidtechnicaltest.utils.DEFAULT_LONG
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit.*

@Suppress("PrivatePropertyName")
class PupilApiTest {
	
	private val mockWebServer = MockWebServer()
	private lateinit var apiService: PupilApi
	private val BASE_URL = "https://androidtechnicaltestapi-test.bridgeinternationalacademies.com/"
	
	@Before
	fun setup() {
		mockWebServer.start()
		val client = OkHttpClient().newBuilder().apply {
			connectTimeout(30, SECONDS)
			readTimeout(30, SECONDS)
			writeTimeout(30, SECONDS)
			addInterceptor {chain ->
				chain.proceed(chain.request().newBuilder().addHeader("User-Agent", "Pupils-Android/1.0.0").build())
			}
		}.build()
		apiService = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(client).build()
				.create(PupilApi::class.java)
	}
	
	@After
	fun close() {
		mockWebServer.shutdown()
	}
	
	@Test
	fun testSuccessResponse() {
		val pupilsResponse = apiService.getPupils(page = 1).execute()
		Assert.assertEquals(true, pupilsResponse.isSuccessful)
	}
	
	@Test
	fun testFailureResponse() {
		val pupilsResponse = apiService.getPupils(page = -1).execute()
		Assert.assertEquals(false, pupilsResponse.isSuccessful)
	}
	
	@Test
	fun testResponseSize() {
		val pupilsResponse = apiService.getPupils(page = 1).execute()
		Assert.assertEquals(5, pupilsResponse.body()?.items?.size)
	}
	
	@Test
	fun testMockResponse() {
		val list = arrayListOf<Pupil>()
		list.add(Pupil(pupilId = 1, name = "Name 1"))
		list.add(Pupil(pupilId = 2, name = "Name 2"))
		list.add(Pupil(pupilId = 3, name = "Name 3"))
		list.add(Pupil(pupilId = 4, name = "Name 4"))
		list.add(Pupil(pupilId = 5, name = "Name 5"))
		val pupilsResponse = PupilsResponse(list, 1, list.size, 1)
		val response = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(Gson().toJson(pupilsResponse))
		mockWebServer.enqueue(response)
		Assert.assertEquals("HTTP/1.1 200 OK", response.status)
	}
	
	@Test
	fun testValidResponse() {
		val pupilsResponse = apiService.getPupils(page = 1).execute()
		pupilsResponse.body()?.items?.forEach {
			Assert.assertEquals(true, it.pupilId > DEFAULT_LONG)
		}
	}
}