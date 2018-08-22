package kestone.com.kestone.Adapters.SetupFragment;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kestone.com.kestone.MODEL.Setup.RESPONSE.Data;
import kestone.com.kestone.MODEL.Setup.RESPONSE.Details;
import kestone.com.kestone.R;

/**
 * Created by karan on 8/25/2017.
 */

public class SetupAdapter extends RecyclerView.Adapter<SetupAdapter.Holder> {

    String[] s1 = {"Basic Set-Up", "Advanced Set-Up", "Pro Set-Up"};
    String[] s2 = {"1 Backdrop, 1 Screen Set-Up", "1 Backdrop, 2 Screen Set-Up", "Wide Screen LED Set-Up"};
    String[] s3 = {"This set-up is ideal for small functions address by\n company spokesperson and it includes one stage\n with two screens and backdrop."
            , "This set-up is ideal for small functions address by\n company spokesperson and it includes one stage\n with two screens and backdrop.",
            "This set-up is ideal for small functions address by\n company spokesperson and it includes one stage\n with two screens and backdrop."};

    SetupAdapterCallBack setupAdapterCallBack;

    List<Data> data;

    Context mcontext;


    public SetupAdapter(SetupAdapterCallBack context, List<Data> response, Context c) {
        data = response;
        this.setupAdapterCallBack = context;
        mcontext = c;
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView text1, text2, text3;
        TextView seeDetails, select;

        public Holder(View view) {
            super(view);
            text1 = (TextView) view.findViewById(R.id.text1);
            text2 = (TextView) view.findViewById(R.id.text2);
            text3 = (TextView) view.findViewById(R.id.text3);
            select = (TextView) view.findViewById(R.id.setupSelect);
            seeDetails = (TextView) view.findViewById(R.id.setupSeeDetails);

        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_setup_card, parent, false);
        return new SetupAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        holder.text1.setText(data.get(position).getSetupName());
        holder.text2.setText(data.get(position).getDescription());
        holder.text3.setText(data.get(position).getDescription2());

        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupAdapterCallBack.onSelectClick(data.get(position), position);
            }
        });

        holder.seeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupAdapterCallBack.onDetailsClicked(data.get(position).getDetails(), data.get(position).getPackageImages(), data.get(position).getSetupName());
            }
        });

        if (data.get(position).isSelected()) {
            holder.select.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.btnColorGrey));
            // holder.select.setBackground(ContextCompat.getDrawable(mcontext,R.drawable.btn_grey_solid_dark));
            holder.select.setEnabled(true);
        } else {
            holder.select.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.selection_grey));
            // holder.select.setBackground(ContextCompat.getDrawable(mcontext,R.drawable.btn_design_solid));
            holder.select.setEnabled(true);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface SetupAdapterCallBack {
        void onSelectClick(Data data, int position);

        void onDetailsClicked(List<Details> details, String[] images, String banner);
    }

    public void SelectSetup(int position) {
        if (position != -1) {
            for (int i = 0; i < data.size(); i++) {
                if (i == position) {
                    data.get(i).setSelected(true);
                } else {
                    data.get(i).setSelected(false);
                }
            }
        }else {
            for (int i = 0; i < data.size(); i++) {
                data.get(i).setSelected(false);
            }
        }
    }

}
