package sharecrew.net.kithee.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sharecrew.net.kithee.Adapter.TravelRecyclerViewAdapter;
import sharecrew.net.kithee.R;
import sharecrew.net.kithee.Travel;
import sharecrew.net.kithee.Utility.HTTPostRequest;
import sharecrew.net.kithee.Utility.Uti;

public class TravelFragment extends Fragment implements ParseFetchedData, View.OnClickListener{

    private RecyclerView mRecycer;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayout;

    private ArrayList<Travel> mTravelList;

    private String mPrice     = "";
    private String mDeparture = "";
    private String mArival    = "";
    private String mDaysofT   = "2";
    private String mCar       = "false";

    public static TravelFragment newInstance(String mPrice){
        TravelFragment f = new TravelFragment();

        Bundle args = new Bundle();
        args.putString("price", mPrice);

        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrice = getArguments().getString("price");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel, container, false);

        RelativeLayout dep = (RelativeLayout) view.findViewById(R.id.filter_dep);
        dep.setOnClickListener(this);

        RelativeLayout arr = (RelativeLayout) view.findViewById(R.id.filter_arr);
        arr.setOnClickListener(this);

        mRecycer = (RecyclerView) view.findViewById(R.id.list);
        mLayout = new LinearLayoutManager(view.getContext());
        mRecycer.setLayoutManager(mLayout);

        final RelativeLayout car = (RelativeLayout) view.findViewById(R.id.filter_loc);
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                car.setBackgroundColor(getResources().getColor(R.color.colorPrimary, getActivity().getTheme()));
                mCar = "true";
                exec();
            }
        });

        mRecycer.setHasFixedSize(true);
        mRecycer.setLayoutManager(mLayout);

        // maxfare, departuredate, returndate, lengthofstay
        exec();

        return view;
    }

    public void exec(){
        new Async(this).execute(mPrice, mDeparture, mArival, mDaysofT, mCar);
    }

    @Override
    public void onDataFetch(ArrayList<Travel> list) {
        mTravelList = list;

        mAdapter = new TravelRecyclerViewAdapter(mTravelList);
        mRecycer.setAdapter(mAdapter);
    }

    @Override
    public void onClick(final View v) {

        LayoutInflater inflater = LayoutInflater.from(v.getContext());
        final View promptsView = inflater.inflate(R.layout.popup_display, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
        alertDialogBuilder.setView(promptsView);

        if(v.getId() == R.id.filter_dep){

            alertDialogBuilder
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK, so save the mSelectedItems results somewhere
                            // or return them to the component that opened the dialog
                            DatePicker dp = (DatePicker) promptsView.findViewById(R.id.datePicker);
                            String cal = String.format("%d-%02d-%d", dp.getYear(), dp.getMonth()+1, dp.getDayOfMonth());
                            System.out.println(cal);

                            mDeparture = cal;
                            exec();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
        }else if(v.getId() == R.id.filter_arr){
            alertDialogBuilder
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            DatePicker dp = (DatePicker) promptsView.findViewById(R.id.datePicker);

                            String cal = String.format("%d-%02d-%d", dp.getYear(), dp.getMonth()+1, dp.getDayOfMonth());
                            System.out.println(cal);

                            mArival = cal;
                            exec();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
        }

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.setTitle("Pick a date...");
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);
    }

    public class Async extends AsyncTask<String, Void, ArrayList<Travel>>{

        ProgressDialog pd;
        ParseFetchedData mListener;

        public Async(ParseFetchedData mListener){
            this.mListener = mListener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(getContext(), "Please Wait...", "We're fetching your travels!", true);
        }

        @Override
        protected ArrayList<Travel> doInBackground(String... params) {

            ArrayList<Travel> list = new ArrayList<>();

            HTTPostRequest send = new HTTPostRequest();
            String returnData = send.fetchData(Uti.sendData(params[0], params[1], params[2], params[3], params[4]));

            try{
                JSONArray jsonArray   = new JSONArray(returnData);
                JSONObject jsonObject;

                for(int i = 0; i < jsonArray.length(); i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    String destination  = jsonObject.get("DestinationLocation").toString();
                    String departure    = jsonObject.get("DepartureDateTime").toString();
                    String returnDate   = jsonObject.get("ReturnDateTime").toString();
                    String fare         = jsonObject.get("Fare").toString();
                    String fareCar      = jsonObject.get("Car").toString();
                    String airline      = jsonObject.get("AirlineCodes").toString();

                    list.add(new Travel(destination, departure, returnDate, fare, airline, fareCar));
                    System.out.println(fare);
                }

                return list;

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Travel> list) {
            super.onPostExecute(list);
            pd.dismiss();
            mListener.onDataFetch(list);
        }
    }
}


