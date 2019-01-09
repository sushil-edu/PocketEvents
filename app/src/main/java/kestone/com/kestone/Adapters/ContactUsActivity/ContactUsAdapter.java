package kestone.com.kestone.Adapters.ContactUsActivity;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import kestone.com.kestone.MODEL.ContactUs.ContactUsPayload;
import kestone.com.kestone.R;

/**
 * Created by karan on 11/6/2017.
 */

public class ContactUsAdapter extends RecyclerView.Adapter<ContactUsAdapter.Holder> {
//    List<ContactUsPayload> list;
    ContactUsPayload list;
    Context mcontext;
    ContactUsCallback callback;

    public ContactUsAdapter(Context context, ContactUsPayload data) {
        list = data;
        mcontext = context;
        callback = (ContactUsCallback) context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact_us, parent, false);
        return new ContactUsAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetExpanded();
                if (holder.expandableView.getVisibility() == View.VISIBLE){
                    holder.expandableView.setVisibility(View.GONE);
                    list.setExpanded(false);
                }else {
                    holder.expandableView.setVisibility(View.VISIBLE);
                    list.setExpanded(true);
                }

                callback.resetExpanded();
            }
        });

        if (list.isExpanded())
        {
            holder.expandableView.setVisibility(View.VISIBLE);
            holder.text.setTextColor(ContextCompat.getColor(mcontext,R.color.textColorRed));
        }else {
            holder.expandableView.setVisibility(View.GONE);
            holder.text.setTextColor(ContextCompat.getColor(mcontext,R.color.textColorBlack));
        }

        if (list.getText_required().equals("1")){
            holder.query.setVisibility(View.VISIBLE);
            holder.line.setVisibility(View.VISIBLE);
        }else {
            holder.query.setVisibility(View.GONE);
            holder.line.setVisibility(View.GONE);
        }

        holder.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onSubmitClick(list.getQuestionId(),holder.invoice.getText().toString(),holder.query.getText().toString());
            }
        });


        holder.text.setText(list.getQuestion());

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class Holder extends RecyclerView.ViewHolder{
        LinearLayout card, expandableView;
        TextView text;
        View line;
        TextView submit;
        AppCompatEditText query, invoice;

        public Holder(View itemView) {
            super(itemView);
            card = (LinearLayout)itemView.findViewById(R.id.contact_us_card);
            expandableView = (LinearLayout)itemView.findViewById(R.id.contact_us_expanding);
            text = (TextView)itemView.findViewById(R.id.contact_us_Text);
            line = itemView.findViewById(R.id.query_line);
            submit = (TextView) itemView.findViewById(R.id.btn_Submit);
            query = (AppCompatEditText)itemView.findViewById(R.id.et_yourQuery);
            invoice = (AppCompatEditText)itemView.findViewById(R.id.et_invoice);
        }
    }

    public void ResetExpanded(){
//        for (int i = 0; i<list.size();i++){
            list.setExpanded(false);
//        }
    }

    public interface ContactUsCallback {
        public void onSubmitClick(String id, String Invoice, String Query);
        public void resetExpanded();
    }
}
