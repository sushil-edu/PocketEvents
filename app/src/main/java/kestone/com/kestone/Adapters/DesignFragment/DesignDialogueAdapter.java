package kestone.com.kestone.Adapters.DesignFragment;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kestone.com.kestone.Fragments.DesignFragment;
import kestone.com.kestone.MODEL.Design.RESPONSE.Values;
import kestone.com.kestone.MODEL.Theme.RESPONSE.Payload;
import kestone.com.kestone.R;

/**
 * Created by karan on 10/16/2017.
 */

public class DesignDialogueAdapter extends RecyclerView.Adapter<DesignDialogueAdapter.Holder> {
        List<Values> list;
    DesignFragment mContext;
    ArrayList<Payload> lisImage;
//    boolean status = false;
//    final int sdk = android.os.Build.VERSION.SDK_INT;
    int selectedPos;
    Dialog dialog;


//    public DesignDialogueAdapter(Context context, List<Values> data) {
//        mContext = context;
//        list = data;
//    }

    public DesignDialogueAdapter(DesignFragment activity, ArrayList<Payload> listImage, Dialog dialog, int selectedPos){//}, SelectImage selectImage) {
        this.mContext = activity;
        this.lisImage = listImage;
        this.dialog=dialog;
        this.selectedPos=selectedPos;
//        this.selectImage= selectImage;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_design_dialogue, parent, false );
        return new DesignDialogueAdapter.Holder( itemView );
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {

//        holder.textView.setText(list.get(position).getName());
//        Glide.with(mContext).load(list.get(position).getURL()).crossFade().placeholder(R.drawable.placeholder_big).into(holder.imageView);

        holder.textView.setText( lisImage.get( position ).getThemeType() );
        Glide.with( mContext ).load( lisImage.get( position ).getThemeImage()).crossFade().placeholder( R.drawable.placeholder_big ).into( holder.imageView );

        if (lisImage.get( position ).isSelected) {
            holder.cornerRl.setVisibility( View.VISIBLE);

        }else {
            holder.cornerRl.setVisibility( View.GONE);
        }
        holder.imageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("ID ", lisImage.get( position ).getImageid());

                mContext.setSelectionImage( position );

            }
        } );
//        holder.selected.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                mContext.setSelectionImage( position );
//            }
//        } );


//        if(lisImage.get( position ).isSelected){
//            holder.selected.setText( "Selected" );
//            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                holder.selected.setBackgroundDrawable( ContextCompat.getDrawable(mContext, R.drawable.btn_design_solid) );
//            } else {
//                holder.selected.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_design_solid));
//            }
//        }else {
//            holder.selected.setText( "Select" );
//            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                holder.selected.setBackgroundDrawable( ContextCompat.getDrawable(mContext, R.drawable.btn_grey_solid_dark) );
//            } else {
//                holder.selected.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_grey_solid_dark));
//            }
//        }
//        holder.selected.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                setSelected(position);
//                selectImage.onSelect( lisImage.get( position ).getImageid(),designPosition, lisImage.get( position ).getThemeType() ) ;
//                dialog.dismiss();
//            }
//        } );

    }

    @Override
    public int getItemCount() {
        return lisImage.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView textView, selected;
        ImageView imageView;
        RelativeLayout cornerRl;

        public Holder(View view) {
            super( view );
            textView = (TextView) view.findViewById( R.id.designText );
            selected = (TextView) view.findViewById( R.id.designSelect );
            selected.setVisibility( View.GONE );
            imageView = (ImageView) view.findViewById( R.id.designImage );
            cornerRl=(RelativeLayout)view.findViewById( R.id.cornerRl );
//            cornerRl.setVisibility( View.GONE );
        }
    }

}
