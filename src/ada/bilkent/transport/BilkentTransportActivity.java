package ada.bilkent.transport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

public class BilkentTransportActivity extends Activity {
	/** Called when the activity is first created. */
	public static String url = "http://ozguryazilim.bilkent.edu.tr/ulasim/json_text.php";
	public static JSONObject allJson;
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

		try {
			// String all = executeHttpGet(url);
			// Log.d("all", allSchedule);
			allJson = new JSONObject(allSchedule);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Toast.makeText(this, "inmedi", Toast.LENGTH_SHORT).show();

			e.printStackTrace();
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

			Intent intent = new Intent();
			intent = new Intent(BilkentTransportActivity.this,
					ada.bilkent.transport.locations.TM2.class);

			switch (position) {
			case 0:
				intent.putExtra("location", "TM");
				intent.putExtra("locationExtended", "Tunus-Merkez");
				break;
			case 1:
				intent.putExtra("location", "MT");
				intent.putExtra("locationExtended", "Merkez-Tunus");
				break;
			case 2:
				intent.putExtra("location", "SM");
				intent.putExtra("locationExtended", "Sýhhiye-Merkez");
				break;
			case 3:
				intent.putExtra("location", "MS");
				intent.putExtra("locationExtended", "Merkez-Sýhhiye");
				break;
			case 4:
				intent.putExtra("location", "TD");
				intent.putExtra("locationExtended", "Tunus-Doðu");
				break;
			case 5:
				intent.putExtra("location", "DT");
				intent.putExtra("locationExtended", "Doðu-Tunus");
				break;
			case 6:
				intent.putExtra("location", "SD");
				intent.putExtra("locationExtended", "Sýhhiye-Doðu");
				break;
			case 7:
				intent.putExtra("location", "DS");
				intent.putExtra("locationExtended", "Doðu-Sýhhiye");
				break;
			case 8:
				intent.putExtra("location", "MD");
				intent.putExtra("locationExtended", "Merkez-Doðu");
				break;
			case 9:
				intent.putExtra("location", "DM");
				intent.putExtra("locationExtended", "Doðu-Merkez");
				break;
			case 10:
				intent.putExtra("location", "M");
				intent.putExtra("locationExtended", "Merkez");
				break;
			default:
				return;
			}
			startActivity(intent);

			return;
		}
	};

	private String executeHttpGet(String url) throws Exception {
		BufferedReader in = null;
		String page = "";
		try {
			HttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
					"android");
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);

			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			page = sb.toString();

		} catch (Exception e) {
			page = "false";

		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return page;
	}

	private String allSchedule = "{\"transport\": { \"TM\" : { \"haftaici\":[\"00:00\",\"07:50\",\"08:00\",\"09:00\",\"10:00\",\"11:00\",\"12:00\",\"13:00\",\"14:00\",\"15:00\",\"16:00\",\"17:00\",\"18:00\",\"19:00\",\"20:00\",\"21:00\",\"22:00\",\"23:00\"],\"haftasonu\":[\"00:00\",\"01:30\",\"08:00\",\"09:00\",\"10:00\",\"11:00\",\"12:00\",\"13:00\",\"14:00\",\"15:00\",\"16:00\",\"17:00\",\"18:00\",\"19:00\",\"20:00\",\"21:00\",\"22:00\",\"23:00\"] },\"MT\" : { \"haftaici\":[\"07:25\",\"08:40\",\"09:40\",\"10:40\",\"11:40\",\"12:40\",\"13:40\",\"14:40\",\"15:40\",\"16:40\",\"17:40\",\"18:50\",\"19:50\",\"20:50\",\"21:50\",\"22:50\",\"23:50\"],\"haftasonu\":[\"08:40\",\"09:40\",\"10:40\",\"11:40\",\"12:40\",\"13:40\",\"14:40\",\"15:40\",\"16:40\",\"17:40\",\"18:50\",\"19:50\",\"20:50\",\"21:50\",\"22:50\",\"23:50\"] },\"TD\" : { \"haftaici\":[\"00:00\",\"07:50\",\"08:00\",\"09:00\",\"10:00\",\"11:00\",\"12:00\",\"13:00\",\"14:00\",\"15:00\",\"16:00\",\"17:00\",\"18:00\",\"19:00\",\"20:00\",\"21:00\",\"22:00\",\"23:00\"],\"haftasonu\":[\"00:00\",\"01:30\",\"08:00\",\"09:00\",\"10:00\",\"11:00\",\"12:00\",\"13:00\",\"14:00\",\"15:00\",\"16:00\",\"17:00\",\"18:00\",\"19:00\",\"20:00\",\"21:00\",\"22:00\",\"23:00\"] },\"DT\" : { \"haftaici\":[\"07:10\",\"08:40\",\"09:40\",\"10:40\",\"11:40\",\"12:40\",\"13:40\",\"14:40\",\"15:40\",\"16:40\",\"17:40\",\"18:30\",\"19:30\",\"20:30\",\"21:30\",\"22:30\",\"23:30\"],\"haftasonu\":[\"08:20\",\"09:20\",\"10:20\",\"11:20\",\"12:20\",\"13:20\",\"14:20\",\"15:20\",\"16:20\",\"17:20\",\"18:30\",\"19:30\",\"20:30\",\"21:30\",\"22:30\",\"23:30\"] },\"SM\" : { \"haftaici\":[\"07:50\",\"08:00\",\"09:00\",\"10:00\",\"11:00\",\"12:00\",\"13:00\",\"14:00\",\"15:00\",\"16:00\",\"17:00\",\"18:00\",\"19:00\",\"20:00\",\"21:00\"],\"haftasonu\":[\"08:00\",\"09:00\",\"10:00\",\"11:00\",\"12:00\",\"13:00\",\"14:00\",\"15:00\",\"16:00\",\"17:00\",\"18:00\",\"19:00\",\"20:00\",\"21:00\"] },\"MS\" : { \"haftaici\":[\"07:25\",\"08:40\",\"09:40\",\"10:40\",\"11:40\",\"12:40\",\"13:40\",\"14:40\",\"15:40\",\"16:40\",\"17:40\",\"18:50\",\"19:50\",\"20:50\",\"21:50\"],\"haftasonu\":[\"08:40\",\"09:40\",\"10:40\",\"11:40\",\"12:40\",\"13:40\",\"14:40\",\"15:40\",\"16:40\",\"17:40\",\"18:50\",\"19:50\",\"20:50\",\"21:50\"] },\"SD\" : { \"haftaici\":[\"07:50\",\"08:00\",\"09:00\",\"10:00\",\"11:00\",\"12:00\",\"13:00\",\"14:00\",\"15:00\",\"16:00\",\"17:00\",\"18:00\",\"19:00\",\"20:00\",\"21:00\"],\"haftasonu\":[\"08:00\",\"09:00\",\"10:00\",\"11:00\",\"12:00\",\"13:00\",\"14:00\",\"15:00\",\"16:00\",\"17:00\",\"18:00\",\"19:00\",\"20:00\",\"21:00\"] },\"DS\" : { \"haftaici\":[\"07:10\",\"08:40\",\"09:40\",\"10:40\",\"11:40\",\"12:40\",\"13:40\",\"14:40\",\"15:40\",\"16:40\",\"17:40\",\"18:30\",\"19:30\",\"20:30\",\"21:30\"],\"haftasonu\":[\"08:20\",\"09:20\",\"10:20\",\"11:20\",\"12:20\",\"13:20\",\"14:20\",\"15:20\",\"16:20\",\"17:20\",\"18:30\",\"19:30\",\"20:30\",\"21:30\"] },\"MD\" : { \"haftaici\":[\"08:00\",\"08:20\",\"08:40\",\"09:10\",\"10:00\",\"10:20\",\"10:40\",\"11:20\",\"11:50\",\"12:40\",\"13:20\",\"13:40\",\"14:20\",\"14:40\",\"15:40\",\"16:40\",\"17:30\",\"18:00\",\"19:00\",\"20:00\",\"21:00\",\"22:00\",\"23:00\"],\"haftasonu\":[\"00:00\",\"08:15\",\"09:15\",\"10:15\",\"11:15\",\"12:15\",\"13:15\",\"14:15\",\"15:15\",\"16:15\",\"17:15\",\"18:15\",\"19:15\",\"20:15\",\"21:15\",\"22:15\",\"23:15\"] },\"DM\" : { \"haftaici\":[\"08:00\",\"08:20\",\"08:40\",\"09:20\",\"10:20\",\"10:40\",\"11:20\",\"11:40\",\"12:00\",\"12:50\",\"13:20\",\"13:40\",\"14:40\",\"15:10\",\"15:40\",\"16:25\",\"16:40\",\"17:20\",\"17:40\",\"18:30\",\"19:30\",\"20:30\",\"21:30\",\"22:30\",\"23:30\"],\"haftasonu\":[\"07:45\",\"08:35\",\"09:35\",\"10:35\",\"11:35\",\"12:35\",\"13:35\",\"14:35\",\"15:35\",\"16:35\",\"17:35\",\"18:35\",\"19:35\",\"20:35\",\"21:35\",\"22:35\",\"23:35\"] },\"M\" : { \"haftaici\":[\"08:00\",\"08:20\",\"08:40\",\"09:00\",\"09:20\",\"09:40\",\"10:00\",\"10:20\",\"10:40\",\"11:00\",\"11:20\",\"11:40\",\"12:00\",\"12:20\",\"12:40\",\"13:00\",\"13:20\",\"13:40\",\"14:00\",\"14:20\",\"14:40\",\"15:00\",\"15:20\",\"15:40\",\"16:00\",\"16:20\",\"16:40\",\"17:00\",\"17:20\",\"17:40\",\"18:00\",\"18:20\"],\"haftasonu\":[] }}}";
}
