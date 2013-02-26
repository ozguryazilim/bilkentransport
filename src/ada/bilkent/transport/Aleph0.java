package ada.bilkent.transport;

import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ada.bilkent.transport.R.color;
import ada.bilkent.transport.locations.TM2;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Aleph0 extends BaseAdapter {
	int count = 0; /* starting amount */
	JSONArray h_ici, h_sonu;
	JSONObject location;
	int hour;
	private Activity activity;

	public int getCount() {
		return count;
	}

	public Object getItem(int pos) {
		return pos;
	}

	public long getItemId(int pos) {
		return pos;
	}

	LayoutInflater inflater;
	JSONObject data;

	public Aleph0(Activity a, JSONObject job) {
		activity = a;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		location = job;
		try {
			
			h_ici = job.getJSONArray("haftaici");
			h_sonu = job.getJSONArray("haftasonu");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date d = new Date();

		try {
			if (d.getDay() == 6 || d.getDay() == 0)
				hour = getNearHourPos(h_sonu);
			else
				hour = getNearHourPos(h_ici);
			
			if(TM2.location.equals("M")){
				h_sonu=fillM(h_ici);
				if (d.getDay() == 6 || d.getDay() == 0)
					hour=-1;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		count = (h_ici.length() > h_sonu.length()) ? h_ici.length() : h_sonu
				.length();

	}

	public View getView(int pos, View v, ViewGroup p) {
		if (v == null)
			v = inflater.inflate(R.layout.hour_row, null);

		TextView tv1 = (TextView) v.findViewById(R.id.hi_hour);
		TextView tv2 = (TextView) v.findViewById(R.id.hs_hour);
		try {
			if (pos >= h_ici.length())
				tv1.setText(R.string.tempty);
			else
				tv1.setText(h_ici.getString(pos));

			
			if (pos >= h_sonu.length() || h_sonu.length()==0)
				tv2.setText("-");
			else
				tv2.setText(h_sonu.getString(pos));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (pos % 2 == 1) {
			tv1.setTextColor(Color.parseColor("#e5001b"));
			tv2.setTextColor(Color.parseColor("#e5001b"));
		} else {
			tv1.setTextColor(Color.parseColor("#014b96"));
			tv2.setTextColor(Color.parseColor("#014b96"));
		}
		Date d = new Date();
		if (!(d.getDay() == 6 || d.getDay() == 0))
			if (pos == hour)
				tv1.setBackgroundColor(Color.parseColor("#DDDDDD"));
			else
				tv1.setBackgroundColor(Color.parseColor("#FFFFFF"));
		else if (pos == hour)
			tv2.setBackgroundColor(Color.parseColor("#DDDDDD"));
		else
			tv2.setBackgroundColor(Color.parseColor("#FFFFFF"));

		return v;
	}
	
	public JSONArray fillM(JSONArray j2){
		JSONArray jhsonu = new JSONArray();
		String []a1;
		
		for(int i=0;i<j2.length();i++){
			try {
				a1=j2.getString(i).split(":");
				a1[1]=(Integer.valueOf(a1[1])+10)+"";
				jhsonu.put(a1[0]+":"+a1[1]);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jhsonu;
	}

	public int getNearHourPos(JSONArray j) throws JSONException {
		
		Date d = new Date();
		int hour2 = d.getHours();
		if (hour2 > -1 && hour2 < 5)
			hour2 += 24;
		int size;
		hour2=hour2*100+d.getMinutes();
		
		String h;
		String []a;
		size = j.length();
		for (int i = 0; i < size; i++) {
			 a= j.getString(i).split(":");
			 
			int t_time = Integer.parseInt(a[0]);
			if (t_time > -1 && t_time < 5) {
				t_time += 24;
			}
			t_time=Integer.valueOf(t_time+a[1]);
		
			if (t_time > hour2) {
				return i;
			}
		}
		return 0;
	}
}
