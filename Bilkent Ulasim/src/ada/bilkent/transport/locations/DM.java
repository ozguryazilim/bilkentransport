package ada.bilkent.transport.locations;

import ada.bilkent.transport.BilkentTransportActivity;
import ada.bilkent.transport.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class DM extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dm);
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent = new Intent(DM.this, BilkentTransportActivity.class);
		startActivity(intent);
		finish();
	}

}
