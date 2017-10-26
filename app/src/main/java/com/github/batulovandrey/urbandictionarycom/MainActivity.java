package com.github.batulovandrey.urbandictionarycom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.batulovandrey.urbandictionarycom.bean.BaseResponse;
import com.github.batulovandrey.urbandictionarycom.bean.DefinitionResponse;
import com.github.batulovandrey.urbandictionarycom.service.ApiClient;
import com.github.batulovandrey.urbandictionarycom.service.UrbanDictionaryService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Andrey Batulov on 26/10/2017
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UrbanDictionaryService service = ApiClient.getRetrofit().create(UrbanDictionaryService.class);
        Call<BaseResponse> call = service.getDefine("wat");
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body() != null) {
                    List<DefinitionResponse> definitionResponses = response.body().getDefinitionResponses();
                    for (DefinitionResponse definitionResponse : definitionResponses) {
                        System.out.println(definitionResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}