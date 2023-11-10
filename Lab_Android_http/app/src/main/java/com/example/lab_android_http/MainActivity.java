package com.example.lab_android_http;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.lab_android_http.adpter.PhotoAdapter;
import com.example.lab_android_http.model.PhotoModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<PhotoModel> photoList;
    private PhotoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        photoList = new ArrayList<>();
        adapter = new PhotoAdapter(photoList, this);
        recyclerView.setAdapter(adapter);

        new FetchPhotosTask().execute();
    }

    private class FetchPhotosTask extends AsyncTask<Void, Void, List<PhotoModel>> {
        @Override
        protected List<PhotoModel> doInBackground(Void... voids) {
            List<PhotoModel> photoList = new ArrayList<>();

            try {
                URL url = new URL("http://10.24.35.194/api_android/photo.json");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }

                inputStream.close();
                String jsonString = stringBuilder.toString();

                Gson gson = new Gson();
                Type listType = new TypeToken<List<PhotoModel>>() {}.getType();
                photoList = gson.fromJson(jsonString, listType);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return photoList;
        }

        @Override
        protected void onPostExecute(List<PhotoModel> photos) {
            photoList.addAll(photos);
            adapter.notifyDataSetChanged();
        }
    }
}
