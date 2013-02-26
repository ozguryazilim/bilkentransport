package ada.bilkent.transport.locations;

import ada.bilkent.transport.BilkentTransportActivity;
import ada.bilkent.transport.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class DS extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ds);
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
        intent = new Intent(DS.this, BilkentTransportActivity.class);
        startActivity(intent);      
        finish();
	}

}