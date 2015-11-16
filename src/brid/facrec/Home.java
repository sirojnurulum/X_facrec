package brid.facrec;

import java.io.ByteArrayOutputStream;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Home extends ActionBarActivity {
	private ImageView ivResult;
	private Button btnTakePhoto;
	private Button btnChooseImage;
	private Button btnSearch;
	private int resource = 0;
	private static int HASIL_LOAD = 1;
	private Bitmap gambar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		ivResult = (ImageView) findViewById(R.id.ivResult);
		btnTakePhoto = (Button) findViewById(R.id.btnTakePhoto);
		btnChooseImage = (Button) findViewById(R.id.btnChooseImage);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		setAction();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resource == 1) {
			if (resultCode == RESULT_OK && data != null) {
				gambar = (Bitmap) data.getExtras().get("data");
				ivResult.setImageBitmap(gambar);
			} else {
				Log.e("--->", "data kosong");
			}
		} else if (resource == 2) {
			if (requestCode == HASIL_LOAD && resultCode == RESULT_OK
					&& data != null) {
				Uri tmpGambar = data.getData();
				String[] path = { MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(tmpGambar, path,
						null, null, null);
				cursor.moveToFirst();
				int index = cursor.getColumnIndex(path[0]);
				String pathGambar = cursor.getString(index);
				cursor.close();
				gambar = BitmapFactory.decodeFile(pathGambar);
				ivResult.setImageBitmap(gambar);
			} else {
				Log.e("--->", "data kosong");
			}
		}
	}

	private void setAction() {
		btnTakePhoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				resource = 1;
				Intent intent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 0);
			}
		});
		btnChooseImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				resource = 2;
				Intent intent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				intent.setType("image/*");
				startActivityForResult(intent, HASIL_LOAD);
			}
		});
		btnSearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				gambar.compress(Bitmap.CompressFormat.JPEG, 50, stream);
				byte[] gambarData = stream.toByteArray();
				Intent i = new Intent(Home.this, FaceAuthentication.class);
				i.putExtra("gambarData", gambarData);
				startActivity(i);
			}
		});
	}
}
