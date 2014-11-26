package com.example.testethreadprincipal;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {
	Button btnBuscarImagem;
	ImageView imgView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Linka os dados do ja va com o xml
		btnBuscarImagem = (Button) findViewById(R.id.btnBuscarImagem);
		imgView = (ImageView) findViewById(R.id.imgView);

	}

	public void buscarImagem( View view) {
		//Classe handler para manipular a thead principal
		final Handler handler = new Handler();
		
		//Thread para acesso a irtenet
		new Thread(){
			public void run() {
				try {
					//Conect com uma URL para pegar a imagem
					URL url = new URL("http://www.superdownloads.com.br/imagens/materias2013/marco/Augusto/Dezembro/Xposed/AndroidXposed_.jpg");
					HttpURLConnection connection;
					connection = (HttpURLConnection) url.openConnection();
					connection.setDoInput(true);
					connection.connect();
					
					//Converte os binarios em uma imagem Bitmap
					InputStream inputStream = connection.getInputStream();
					final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
					
					//manipula a Thread principal para mudar o imageview
					handler.post(new Runnable() {						
						@Override
						public void run() {
							// Muda a Imagem
							imgView.setImageBitmap(bitmap);
						}
					});
					
					
				
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
