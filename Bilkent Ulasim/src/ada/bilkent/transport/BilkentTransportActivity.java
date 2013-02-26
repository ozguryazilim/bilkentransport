package ada.bilkent.transport;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;

public class BilkentTransportActivity extends Activity {
	/** Called when the activity is first created. */

	public static final String PREFS_NAME = "Ayarlar";
	public static boolean check = false;

	// It creates the application layout when user clicked the icon of the
	// application.
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		boolean isChecked = settings.getBoolean("hatirlatma", false);

		if (!check) {
			check = true;
			if (!isChecked) {
				String longMessage = getString(R.string.uyari);
				LayoutInflater inflater = getLayoutInflater();
				final View checkboxLayout = inflater.inflate(R.layout.splash,
						null);
				CheckBox cb = (CheckBox) checkboxLayout
						.findViewById(R.id.checkBox);
				cb.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						if (((CheckBox) v).isChecked()) {
							SharedPreferences.Editor editor = settings.edit();
							editor.putBoolean("hatirlatma", true);
							editor.commit();
						}
					}
				});
				TextView tv = (TextView) checkboxLayout
						.findViewById(R.id.message);
				tv.setText(longMessage);
				AlertDialog.Builder builder = new AlertDialog.Builder(this)
						.setTitle("Kullaným Þartlarý")
						.setView(checkboxLayout)
						.setPositiveButton("Kabul Ediyorum",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
									}
								});
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		}

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
		case R.id.exit:
			finish();
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