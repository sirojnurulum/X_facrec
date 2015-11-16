package brid.facrec;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class FaceAuthentication extends ActionBarActivity {
	byte[] gambarData;
	Bitmap gambar;
	private ImageButton ib1;
	private ImageView ivResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_face_authentication);
		gambarData = getIntent().getByteArrayExtra("gambarData");
		gambar = BitmapFactory
				.decodeByteArray(gambarData, 0, gambarData.length);
		ib1 = (ImageButton) findViewById(R.id.ib1);
		ivResult = (ImageView) findViewById(R.id.ivResult);
		ivResult.setImageBitmap(gambar);
		ib1.setImageBitmap(gambar);
		setAction();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.face_authentication, menu);
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

	private void setAction() {
		ib1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(FaceAuthentication.this, Profil.class);
				i.putExtra("gambarData", gambarData);
				startActivity(i);
			}
		});
	}
}
