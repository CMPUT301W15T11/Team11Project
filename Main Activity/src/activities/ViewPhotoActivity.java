package activities;

import com.example.team11xtremexpensetracker.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

public class ViewPhotoActivity extends Activity {

	private ImageView receiptPhoto;
	private byte[] photo;
	private Bitmap bitmap;

	/* This activity displays the photo receipt for the expense item.
	 * It is only invoked in the event that there is a photo present.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_photo);
		receiptPhoto = (ImageView) findViewById(R.id.picView);
		photo = getIntent().getExtras().getByteArray("Photo");
		bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
		receiptPhoto.setImageBitmap(bitmap);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		getMenuInflater().inflate(R.menu.view_photo, menu);
		return true;
	}

}
