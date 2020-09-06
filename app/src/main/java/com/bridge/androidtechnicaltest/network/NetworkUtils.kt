package com.bridge.androidtechnicaltest.network

import android.content.Context
import android.net.ConnectivityManager
import com.bridge.androidtechnicaltest.App
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.utils.DEFAULT_INT
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit.*

object NetworkUtils {
	
	const val CODE_NO_INTERNET: Int = 0
	const val TYPE_JSON = "application/json"
	const val REQUEST_ID_GET_ALL = "639b5ce5-2de6-4391-93da-bca4b84047a6"
	const val REQUEST_ID_POST = "31a70555-484e-4157-8648-31d8ff18247a"
	const val REQUEST_ID_DELETE = "a609d47a-8153-482e-89cd-5f73b5612037"
	private const val BASE_URL = "https://androidtechnicaltestapi-test.bridgeinternationalacademies.com/"
	
	private val BAD_REQUEST = Utils.getString(R.string.err_bad_request) to Throwable(Utils.getString(R.string.err_msg_bad_request))
	private val UNAUTHORIZED = Utils.getString(R.string.err_unauthorized) to Throwable(Utils.getString(R.string.err_msg_unauthorized))
	private val PAGE_NOT_FOUND = Utils.getString(R.string.err_page_not_found) to Throwable(Utils.getString(R.string.err_msg_page_not_found))
	private val TIMEOUT = Utils.getString(R.string.err_timeout) to Throwable(Utils.getString(R.string.err_msg_timeout))
	private val MAINTENANCE_BREAK = Utils.getString(R.string.err_maintenance_break) to Throwable(Utils.getString(R.string.err_msg_maintenance_break))
	
	/**
	 * Creates single instance of retrofit.
	 *
	 * @return retrofit instance.
	 */
	val retrofit: Retrofit by lazy {
		val client = OkHttpClient().newBuilder().apply {
			connectTimeout(30, SECONDS)
			readTimeout(30, SECONDS)
			writeTimeout(30, SECONDS)
			addInterceptor {chain ->
				chain.proceed(chain.request().newBuilder().addHeader("User-Agent", "Pupils-Android/1.0.0").build())
			}
		}.build()
		Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(client).build()
	}
	
	/**
	 * Check for response validity with following conditions:
	 * NotNull, Non-empty body, [Response.isSuccessful]
	 *
	 * @param response object to be examined.
	 *
	 * @return true if response is valid, otherwise false.
	 */
	fun isValidResponse(response: Response<*>): Boolean = response.isSuccessful
	
	/**
	 * Identify failure reason based on HTTP status codes.
	 * If no status code was provided or in case of unidentified status code, It will return default message.
	 *
	 * @param code requires to identify exact reason of failure request.
	 * @param throwable provided by [Retrofit] call.
	 */
	fun getRequestFailReason(
			code: Int = DEFAULT_INT,
			throwable: Throwable? = null
	) = when (code) {
		CODE_NO_INTERNET -> Utils.getString(R.string.err_request_fail) to Throwable(Utils.getString(R.string.err_no_internet))
		HttpURLConnection.HTTP_BAD_REQUEST, HttpURLConnection.HTTP_BAD_METHOD, HttpURLConnection.HTTP_UNSUPPORTED_TYPE -> BAD_REQUEST
		HttpURLConnection.HTTP_UNAUTHORIZED -> UNAUTHORIZED
		HttpURLConnection.HTTP_NOT_FOUND, HttpURLConnection.HTTP_NOT_ACCEPTABLE -> PAGE_NOT_FOUND
		HttpURLConnection.HTTP_CLIENT_TIMEOUT, HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> TIMEOUT
		HttpURLConnection.HTTP_INTERNAL_ERROR, HttpURLConnection.HTTP_BAD_GATEWAY, HttpURLConnection.HTTP_UNAVAILABLE -> MAINTENANCE_BREAK
		else -> Utils.getString(R.string.err_something_went_wrong) to (throwable ?: Throwable(Utils.getString(R.string.err_msg_something_went_wrong)))
	}
	
	/**
	 * Check for internet connection availability.
	 *
	 * @return true if device is currently connected to the internet (WiFi or Mobile Data), otherwise false.
	 */
	@Suppress("DEPRECATION")
	fun isNetworkAvailable(): Boolean {
		val networkInfo = (App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?)?.activeNetworkInfo
		return networkInfo != null && networkInfo.isConnected
	}
}