package davidmuir.mobileplaformdevelopmentcourseworkdavidmuir;

/**
 * Created by David on 28/03/2018.
 */


import android.graphics.Color;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

public class ReportItem extends FragmentActivity implements OnMapReadyCallback {

    private TextView titleText;
    private RadioGroup radioGroup;
    private TextView descriptionText;
    private TextView dateText;
    private TextView daysText;
    private TextView linkText;



    private GoogleMap mMap;
    ReportDisplay theItem;
    LatLng theLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_display);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        theItem = (ReportDisplay) getIntent().getSerializableExtra("ReportDisplay");
        titleText = (TextView)findViewById(R.id.TitleID);
        descriptionText = (TextView)findViewById(R.id.DescriptionID);
        linkText = (TextView)findViewById(R.id.LinkID);
        dateText = (TextView)findViewById(R.id.DateID);
        daysText = (TextView)findViewById(R.id.DaysID);

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);

        if (theItem!=null)
        {
            theLatLng = new LatLng(theItem.getLat(),theItem.getLon());
            titleText.setText(theItem.title);
            linkText.setText(theItem.link);
            if (theItem.getWorks() != null)
            {
                descriptionText.setText("Works: " + theItem.getWorks() +"\n \n"
                        + "Traffic Management: " + theItem.getManagement());

                if (theItem.getDiversionInfo()!=null)
                {
                    descriptionText.setText("Works: " + theItem.getWorks() + "\n \n"
                            + "Traffic Management: " + theItem.getManagement() + "\n \n"
                            + "Diversion: " + theItem.getDiversionInfo());
                }
                dateText.setText(theItem.startDate.toString() + " - " + theItem.endDate.toString());

            }
            else // if current incident
            {
                descriptionText.setText("Issue: " + theItem.desc);
                dateText.setText(theItem.startDate);
            }

            long days = theItem.getLengthOfRW();
            int iDays = (int)days;
            daysText.setText("Scheduled: " + String.valueOf(days) + " " + "days");

            if (iDays < 7)
            {
                daysText.setBackgroundColor(Color.GREEN);
            }
            else if (iDays >= 7 && iDays < 14)
            {
                daysText.setBackgroundColor(Color.rgb(255,191,0));
            }
            else if (iDays >= 14)
            {
                daysText.setBackgroundColor(Color.RED);
            }

        }

        RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.radioNormal)
                {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
                else if (checkedId == R.id.radioSat)
                {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }
            }


        };

        radioGroup.setOnCheckedChangeListener(checkedChangeListener);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng thePosition = theLatLng;
        mMap.addMarker(new MarkerOptions().position(thePosition).title(theItem.title));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(thePosition));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(thePosition,12.5f));
    }


}
