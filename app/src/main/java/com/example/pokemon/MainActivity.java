package com.example.pokemon;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private Button mBotaoBusca;
    private EditText mEditPokeId;
    private View fragPokemon;
    private static final String TAG = "MainActivity";

    public void setFragmentInfo(Pokemon p) throws IOException {
        Log.i(TAG, "Setando info");

        if(fragPokemon != null){
            fragPokemon.setVisibility(View.VISIBLE);
            TextView name = findViewById(R.id.tNome);
            URL url = new URL(p.getImgUrl());
            new DownloadImageTask((ImageView) findViewById(R.id.imgpoke))
                    .execute(p.getImgUrl());
            name.setText(p.getNome());
        }



    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBotaoBusca = (Button) findViewById(R.id.btnBusca);
        fragPokemon = findViewById(R.id.fragpoke);
        fragPokemon.setVisibility(View.INVISIBLE);

        mEditPokeId = (EditText) findViewById(R.id.editTextPokeName);

        mBotaoBusca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask<Void, Void, Pokemon> tar = new TarefaBuscaPokemon();
                tar.execute();
            }
        });

    }

    public class TarefaBuscaPokemon extends AsyncTask<Void, Void, Pokemon> {

        @Override
        protected Pokemon doInBackground(Void... params) {
            Pokemon p = null;
            if (mEditPokeId != null) {


                String pId = mEditPokeId.getText().toString();


                OkHttpClient client = new OkHttpClient();
                String url = "https://pokeapi.co/api/v2/pokemon/" + pId + '/';
                Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .build();

                Response response = null;

                try {
                    response = client.newCall(request).execute();
                    String responseBody = response.body().string();

                    // parse do item recebido
                    JSONObject corpoJson = new JSONObject(responseBody);
                    p = new Pokemon(
                            corpoJson.getString("name"),
                            corpoJson.getJSONObject("sprites").getString("front_default")
                    );
                    Log.i(TAG, "doInBackground: " + responseBody);
                    Log.i(TAG, "doInBackground/cidade: " + corpoJson.getString("name"));
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
            return p;
        }

        @Override
        public void onPostExecute(Pokemon resultado) {
            Log.i(TAG, "onPostExecute: metodo executado");
            if (resultado != null) {
                Log.i(TAG, "onPostExecute: dados recebidos: " + resultado.getNome());
                try {
                    setFragmentInfo(resultado);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }


    }
}