package com.pedro.latihanapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;

import com.pedro.latihanapi.Adapter.CoronaAdapter;
import com.pedro.latihanapi.Model.CoronaModel;
import com.pedro.latihanapi.Network.ApiEndpoint;
import com.pedro.latihanapi.Network.ApiService;
import com.spark.imageslider.ImageSlider;
import com.spark.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    private ApiEndpoint apiService = ApiService.getUrl().create(ApiEndpoint.class);

    private List<CoronaModel.Result> result = new ArrayList<>();

    private RecyclerView recyclerView;
    private CoronaAdapter coronaAdapter;
    private ImageSlider imageSlider;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShowCorona();
        setupView();
        setupRecycleView();
        getSupportActionBar().hide();





    }


    @Override
    protected void onStart() {
        super.onStart();

        imageSlider = findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://cdn.pixabay.com/photo/2020/03/14/09/08/mask-4930050__340.jpg" , "Stay safe") );
        slideModels.add(new SlideModel("https://cdn.pixabay.com/photo/2020/07/27/06/16/person-5441453__340.jpg" , "Stay healthy") );
        slideModels.add(new SlideModel("https://cdn.pixabay.com/photo/2020/03/30/09/15/corona-4983590__340.jpg" , "Stay at home") );
        slideModels.add(new SlideModel("https://cdn.pixabay.com/photo/2020/03/15/18/36/wash-4934590__340.jpg" , "stay healthy wash your hands") );

        imageSlider.setImageList(slideModels);
    }

    private void setupView(){
        recyclerView = findViewById(R.id.recycleview);

    }


    private void setupRecycleView(){
     coronaAdapter = new CoronaAdapter(result);
     RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
     recyclerView.setLayoutManager(layoutManager);
     recyclerView.setAdapter(coronaAdapter);


    }



  private void ShowCorona(){
      Call<CoronaModel> coronaModels = apiService.getData("indonesia" , "provinsi");
      coronaModels.enqueue(new Callback<CoronaModel>() {
          @Override
          public void onResponse(Call<CoronaModel> call, Response<CoronaModel> response) {
              if (response.isSuccessful()){
                  CoronaModel coronaModel = response.body();
                  List<CoronaModel.Result> results = coronaModel.getList_data();
                  Log.d(TAG , results.toString());

                  coronaAdapter.setData(results);
              }

          }

          @Override
          public void onFailure(Call<CoronaModel> call, Throwable t) {
              Log.d(TAG , t.toString());

          }
      });

  }


}