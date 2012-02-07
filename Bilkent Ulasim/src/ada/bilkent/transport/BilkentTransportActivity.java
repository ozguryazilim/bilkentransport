package ada.bilkent.transport;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class BilkentTransportActivity extends Activity {
	/** Called when the activity is first created. */

	// It creates the application layout when user clicked the icon of the
	// application.
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new ImageAdapter(this));
		gridview.setOnItemClickListener(itemClickListener);
	}

	// Option menu for MENU button.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.menu, menu);
		return true;
	}

	// When user clicked an option from menu, this method starts.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.info:
			showInfo();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// Pop-up info screen method for info item on menu
	public void showInfo() {
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
		alertBuilder.setMessage(R.string.info_content).setNegativeButton(
				"Tamam", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Action for 'Tamam' Button
						dialog.cancel();
					}
				});
		AlertDialog alert = alertBuilder.create();
		// Title for AlertDialog
		alert.setTitle("Hakkýnda");
		alert.show();
	}

	// This listener takes the user click on screens.
	private OnItemClickListener itemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View v,
				final int position, long id) {
			if (position == 0) {
				Intent intent = new Intent();
				intent = new Intent(BilkentTransportActivity.this,
						ada.bilkent.transport.locations.TM.class);
				startActivity(intent);
				finish();
			} else if (position == 1) {
				Intent intent = new Intent();
				intent = new Intent(BilkentTransportActivity.this,
						ada.bilkent.transport.locations.MT.class);
				startActivity(intent);
				finish();
			} else if (position == 2) {
				Intent intent = new Intent();
				intent = new Intent(BilkentTransportActivity.this,
						ada.bilkent.transport.locations.SM.class);
				startActivity(intent);
				finish();
			} else if (position == 3) {
				Intent intent = new Intent();
				intent = new Intent(BilkentTransportActivity.this,
						ada.bilkent.transport.locations.MS.class);
				startActivity(intent);
				finish();
			} else if (position == 4) {
				Intent intent = new Intent();
				intent = new Intent(BilkentTransportActivity.this,
						ada.bilkent.transport.locations.TD.class);
				startActivity(intent);
				finish();
			} else if (position == 5) {
				Intent intent = new Intent();
				intent = new Intent(BilkentTransportActivity.this,
						ada.bilkent.transport.locations.DT.class);
				startActivity(intent);
				finish();
			} else if (position == 6) {
				Intent intent = new Intent();
				intent = new Intent(BilkentTransportActivity.this,
						ada.bilkent.transport.locations.SD.class);
				startActivity(intent);
				finish();
			} else if (position == 7) {
				Intent intent = new Intent();
				intent = new Intent(BilkentTransportActivity.this,
						ada.bilkent.transport.locations.DS.class);
				startActivity(intent);
				finish();
			} else if (position == 8) {
				Intent intent = new Intent();
				intent = new Intent(BilkentTransportActivity.this,
						ada.bilkent.transport.locations.MD.class);
				startActivity(intent);
				finish();
			} else if (position == 9) {
				Intent intent = new Intent();
				intent = new Intent(BilkentTransportActivity.this,
						ada.bilkent.transport.locations.DM.class);
				startActivity(intent);
				finish();
			} else if (position == 10) {
				Intent intent = new Intent();
				intent = new Intent(BilkentTransportActivity.this,
						ada.bilkent.transport.locations.M.class);
				startActivity(intent);
				finish();
			}
			return;
		}
	};
}