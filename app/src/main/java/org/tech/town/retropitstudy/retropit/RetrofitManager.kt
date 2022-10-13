package org.tech.town.retropitstudy.retropit


import android.util.Log
import com.google.gson.JsonElement
import org.tech.town.retropitstudy.utils.API
import org.tech.town.retropitstudy.utils.API.BASE_URL
import org.tech.town.retropitstudy.utils.Constants.TAG
import org.tech.town.retropitstudy.utils.RESPONSE_STATE
import retrofit2.Call
import retrofit2.Response


class RetrofitManager {

    companion object{
        val instance = RetrofitManager()
    }

    //레트로핏 인터페이스 가져오기
    private val iRetrofit : IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    //사진 검색 api 호출
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE, String) -> Unit){

        /*val term = searchTerm ?: "" searchTerm이 비어있으면 ""을 넣고 비어있지 않으면  it(term)을 반환 하라는 뜻 언랩핑 작업*/
        val term = searchTerm.let{
            it
        }?: ""

        /*val call = iRetrofit?.searchPhotos(searchTrem = trem) ?: return */
        val call = iRetrofit?.searchPhotos(searchTerm = term).let{
            it
        }?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            //응답 실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "RetrofitManager - onFailure() called / t: $t")

                completion(RESPONSE_STATE.FAIL, t.toString())
            }

            //응답 성공시
           override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "RetrofitManager - onResponse() called / response: ${response.body()}")

                completion(RESPONSE_STATE.OKAY, response.body().toString())
            }
        })
    }
}