package ada.bilkent.transport;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BilkentTransportWidgetConfigure extends Activity {

	int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

	final CharSequence[] items = { "Do�u - Merkez", "Do�u - S�hhiye",
			"Do�u - Tunus", "Merkez - Do�u", "Merkez - S�hhiye",
			"Merkez - Tunus", "Tunus - Do�u", "Tunus - Merkez",
			"S�hhiye - Do�u", "S�hhiye - Merkez", "Merkez Ring" };
	protected boolean[] _selections = new boolean[items.length];
	protected Button _optionsButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widgetconf);

		_optionsButton = (Button) findViewById(R.id.WidgetItems);
		_optionsButton.setText("Rotalar");
		_optionsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				TextView selectedRoutes = (TextView) findViewById(R.id.WidgetSelected);
				selectedRoutes.setText("");
				showDialog(0);
			}
		});

		Button WOK = (Button) findViewById(R.id.WidgetOK);
		WOK.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final Context context = BilkentTransportWidgetConfigure.this;

				AppWidgetManager appWidgetManager = AppWidgetManager
						.getInstance(context);

				RemoteViews views = new RemoteViews(context.getPackageName(),
						R.layout.widget);
				appWidgetManager.updateAppWidget(mAppWidgetId, views);
				BilkentTransportWidget.updateAppWidget(context,
						appWidgetManager, mAppWidgetId);

				Toast.makeText(
						context,
						"BilkentTransportWidget.onClick(): "
								+ String.valueOf(mAppWidgetId),
						Toast.LENGTH_LONG).show();

				Intent resultValue = new Intent();
				resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
						mAppWidgetId);
				setResult(RESULT_OK, resultValue);
				finish();
			}
		});

		Button WCA = (Button) findViewById(R.id.WidgetCancel);
		WCA.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		setResult(RESULT_CANCELED);

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}
		if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
			finish();
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		return new AlertDialog.Builder(this)
				.setTitle("Rotalar:")
				.setMultiChoiceItems(items, _selections,
						new DialogSelectionClickHandler())
				.setPositiveButton("Tamam", new DialogButtonClickHandler())
				.create();
	}

	public class DialogSelectionClickHandler implements
			DialogInterface.OnMultiChoiceClickListener {
		public void onClick(DialogInterface dialog, int clicked,
				boolean selected) {
		}
	}

	public class DialogButtonClickHandler implements
			DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int clicked) {
			switch (clicked) {
			case DialogInterface.BUTTON_POSITIVE:
				printSelectedRoutes();
				break;
			}
		}
	}

	protected void printSelectedRoutes() {
		for (int i = 0; i < items.length; i++) {
			if (_selections[i]) {
				TextView selectedRoutes = (TextView) findViewById(R.id.WidgetSelected);
				selectedRoutes.append("\n" + items[i]);
			}
		}

	}
}
