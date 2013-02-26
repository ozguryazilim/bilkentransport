package ada.bilkent.transport.locations;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ada.bilkent.transport.Aleph0;
import ada.bilkent.transport.BilkentTransportActivity;
import ada.bilkent.transport.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class TM2 extends Activity {

	Aleph0 adapter;
	ListView lv;
	TextView locationHeader;
	JSONObject tm;
	public static String location;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tm2);
		lv = (ListView) findViewById(R.id.list_tm);
		locationHeader = (TextView) findViewById(R.id.locationHeader);
		locationHeader.setText(getIntent().getStringExtra("locationExtended"));
		
		location=getIntent().getStringExtra("location");
		if(location.equalsIgnoreCase("M")){
			((TextView) findViewById(R.id.Haftaici)).setText("31. Lojman");
			((TextView) findViewById(R.id.Haftasonu)).setText("77. Yurt");
		}
		try {
			//tm = BilkentTransportActivity.allJson.getJSONObject("transport").getJSONObject(location);
			tm=listJson(BilkentTransportActivity.allJson.getJSONObject("transport").getJSONObject(location));
			adapter = new Aleph0(TM2.this, tm);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	
		lv.setAdapter(adapter);
	}
	
	public JSONObject listJson(JSONObject j){
		ArrayList<String> hici2=new ArrayList<String>();
		ArrayList<String> hsonu2=new ArrayList<String>();
		JSONArray hici=new JSONArray();
		JSONArray hsonu=new JSONArray();
		JSONObject j2=new JSONObject();
		
		String a1,a2;
		
		try {
			JSONArray jhi=j.getJSONArray("haftaici");
		for(int i=0;i<jhi.length();i++){
			a1=jhi.getString(i);
			a2 = String.copyValueOf(a1.toCharArray(), 0, 2);
			
			if(Integer.valueOf(a2)>=0 && Integer.valueOf(a2)<=4)
			{
				hici2.add(a1);
			}
			else{
				hici.put(a1);
			}
		}
		for(int i=0;i<hici2.size();i++){
			hici.put(hici2.get(i));
		}
		j2.put("haftaici", hici);		
		
			 jhi=j.getJSONArray("haftasonu");			
		
		for(int i=0;i<jhi.length();i++){
			a1=jhi.getString(i);
			a2 = String.copyValueOf(a1.toCharArray(), 0, 2);
			
			if(Integer.valueOf(a2)>=0 && Integer.valueOf(a2)<=4)
			{
				hsonu2.add(a1);
			}
			else{
				hsonu.put(a1);
			}
		}
		for(int i=0;i<hsonu2.size();i++){
			hsonu.put(hsonu2.get(i));
		}
		j2.put("haftasonu", hsonu);	
		
			return j2;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return j;
	}

}
