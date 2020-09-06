package com.bridge.androidtechnicaltest.utils

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.models.PupilsResponse
import com.bridge.androidtechnicaltest.network.NetworkUtils
import org.junit.*
import org.junit.runner.*
import retrofit2.Response
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
class NetworkUtilsTest {
	
	private lateinit var context: Context
	
	@Before
	fun init() {
		context = ApplicationProvider.getApplicationContext()
	}
	
	@Test
	fun networkUtils_Retrofit_BaseUrl_ReturnsEquals() {
		val baseUrl = "https://androidtechnicaltestapi-test.bridgeinternationalacademies.com/"
		val retrofitBaseUrl = NetworkUtils.retrofit.baseUrl()
		Assert.assertEquals(baseUrl, retrofitBaseUrl.toString())
	}
	
	@Test
	fun networkUtils_DefaultParameters_ReturnEquals() {
		val reason = NetworkUtils.getRequestFailReason()
		Assert.assertEquals(reason.first, context.getString(R.string.err_something_went_wrong))
		Assert.assertEquals(reason.second.localizedMessage, context.getString(R.string.err_msg_something_went_wrong))
	}
	
	@Test
	fun networkUtils_TestParameters_ReturnEquals() {
		val reason = NetworkUtils.getRequestFailReason(HttpURLConnection.HTTP_BAD_REQUEST)
		Assert.assertEquals(reason.first, context.getString(R.string.err_bad_request))
		Assert.assertEquals(reason.second.localizedMessage, context.getString(R.string.err_msg_bad_request))
	}
	
	@Test
	fun networkUtils_UnknownParameters_ReturnEquals() {
		val reason = NetworkUtils.getRequestFailReason(-10)
		Assert.assertEquals(reason.first, context.getString(R.string.err_something_went_wrong))
		Assert.assertEquals(reason.second.localizedMessage, context.getString(R.string.err_msg_something_went_wrong))
	}
	
	@Test
	fun networkUtils_Response_Success_ReturnsEquals() {
		val response = Response.success(PupilsResponse())
		Assert.assertEquals(true, NetworkUtils.isValidResponse(response))
	}
	
	@Test
	fun networkUtils_Response_Null_ReturnsEquals() {
		val response = Response.success(null)
		Assert.assertEquals(true, NetworkUtils.isValidResponse(response))
	}
	
	@Test
	fun networkUtils_Network_ReturnsEquals() {
		Assert.assertEquals(true, NetworkUtils.isNetworkAvailable())
	}
}