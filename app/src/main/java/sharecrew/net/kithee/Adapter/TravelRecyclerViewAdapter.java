package sharecrew.net.kithee.Adapter;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import sharecrew.net.kithee.R;
import sharecrew.net.kithee.Travel;

public class TravelRecyclerViewAdapter extends RecyclerView.Adapter<TravelRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Travel> mList;

    private ImageView car;
    private TextView carTxt;

    public TravelRecyclerViewAdapter(ArrayList<Travel> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.travel_card, parent, false);

        car     = (ImageView) view.findViewById(R.id.car);
        carTxt  = (TextView) view.findViewById(R.id.car_desc2);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        String[] dep = mList.get(position).getDeparture().split("-");
        String depD = dep[2].split("T")[0] + "-" + dep[1] + "-" + dep[0];

        holder.deperatur.setText(depD);

        String[] ari = mList.get(position).getReturnDate().split("-");
        String ariD = ari[2].split("T")[0] + "-" + ari[1] + "-" + ari[0];

        holder.arival.setText(ariD);

        holder.airline.setText(mList.get(position).getAirline());
        holder.destination.setText(mList.get(position).getDestination());

        if(!mList.get(position).getCarPrice().equals("null")){
            holder.car.setText(mList.get(position).getCarPrice());
            String a = String.format("%.2f", (Double.parseDouble(mList.get(position).getCarPrice()) +
                    Double.parseDouble(mList.get(position).getFare())));
            holder.price.setText(a);
            car.setVisibility(View.VISIBLE);
            carTxt.setVisibility(View.VISIBLE);
        }else{
            holder.price.setText(mList.get(position).getFare());
            car.setVisibility(View.GONE);
            carTxt.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView price;
        TextView deperatur;
        TextView arival;
        TextView airline;
        TextView destination;
        TextView car;

        public ViewHolder(View v) {
            super(v);
            price       = (TextView) v.findViewById(R.id.price_value);
            destination = (TextView) v.findViewById(R.id.hotel_desc);
            deperatur   = (TextView) v.findViewById(R.id.city_desc);
            arival      = (TextView) v.findViewById(R.id.persons_desc);
            airline     = (TextView) v.findViewById(R.id.car_desc);
            car         = (TextView) v.findViewById(R.id.car_desc2);
        }
    }
}
