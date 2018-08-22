package kestone.com.kestone.Adapters.CompareActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Arrays;

import kestone.com.kestone.MODEL.Manager.CompareLeftRight;
import kestone.com.kestone.MODEL.Manager.MySingleton;
import kestone.com.kestone.MODEL.Venue.RESPONSE.Payload;
import kestone.com.kestone.R;

/**
 * Created by karan on 7/18/2017.
 */

public class CompareAdapter extends BaseAdapter {

    LayoutInflater inflater;

    Context mContext;

    CompareAdapterCallback compareAdapterCallback;

    Payload payloadLeft, payloadRight;

    boolean nextBool;

    CompareLeftRight compare;

    String capacityValues[] = {"Hall Capacity", "   Cluster", "   Class Room", "   Theater","Hall Dimensions", "   Hall Area", "   Hall Length", "   Hall Width", "   Hall Height"};


    public CompareAdapter(Context context, Payload p1, Payload p2, boolean n) {
        mContext = context;
        nextBool = n;
        this.compareAdapterCallback = ((CompareAdapterCallback) context);
        payloadLeft = p1;
        payloadRight = p2;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (MySingleton.getInstance().getCompareCount() > 2) {
            if (nextBool) {
                compare = new CompareLeftRight(MySingleton.getInstance().getCompareIndex1(), MySingleton.getInstance().getCompareIndex2(), MySingleton.getInstance().getCompareHallIndex1()
                        , MySingleton.getInstance().getCompareHallIndex2());

            } else {
                compare = new CompareLeftRight(MySingleton.getInstance().getCompareIndex2(), MySingleton.getInstance().getCompareIndex3(), MySingleton.getInstance().getCompareHallIndex2()
                        , MySingleton.getInstance().getCompareHallIndex3());
            }
        } else {
            compare = new CompareLeftRight(MySingleton.getInstance().getCompareIndex1(), MySingleton.getInstance().getCompareIndex2(), MySingleton.getInstance().getCompareHallIndex1()
                    , MySingleton.getInstance().getCompareHallIndex2());
        }

    }

    public class ViewHolder {
        TextView title, l1, l2,l3,l4;
        TextView next;
    }

    @Override
    public int getCount() {
        //return payloadRight.getAttributes().size() + 1;
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return compare;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {
//        View view = convertview;

        ViewHolder vh;
        if (convertview == null) {
            vh = new ViewHolder();
            convertview = inflater.inflate(R.layout.item_compare_list, viewGroup, false);

            vh.title = (TextView) convertview.findViewById(R.id.compareAttributes);
            vh.l1 = (TextView) convertview.findViewById(R.id.CompareList1);
            vh.l2 = (TextView) convertview.findViewById(R.id.CompareList2);
            vh.l3 = (TextView) convertview.findViewById(R.id.CompareList3);
            vh.l4 = (TextView) convertview.findViewById(R.id.CompareList4);
            vh.next = (TextView) convertview.findViewById(R.id.compareNext_btn);

            convertview.setTag(vh);
        } else {
            vh = (ViewHolder) convertview.getTag();
        }

        if (i == 0) {
            vh.title.setText("");
            if (MySingleton.getInstance().getCompareCount() > 2) {
                vh.next.setVisibility(View.VISIBLE);
                vh.next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        compareAdapterCallback.onNextClicked();
                    }
                });
                if (nextBool) {
                    vh.next.setBackground(mContext.getResources().getDrawable(R.drawable.next));
                    vh.l1.setText(payloadLeft.getHalls().get(MySingleton.getInstance().getCompareHallIndex1()).getHallName());
                    vh.l2.setText(payloadRight.getHalls().get(MySingleton.getInstance().getCompareHallIndex2()).getHallName());
                    vh.l3.setText("("+payloadLeft.getVenueName()+")");
                    vh.l4.setText("("+payloadRight.getVenueName()+")");

                } else {
                    vh.next.setBackground(mContext.getResources().getDrawable(R.drawable.previous));
                    vh.l1.setText(payloadLeft.getHalls().get(MySingleton.getInstance().getCompareHallIndex2()).getHallName());
                    vh.l2.setText(payloadRight.getHalls().get(MySingleton.getInstance().getCompareHallIndex3()).getHallName());
                    vh.l3.setText("("+payloadLeft.getVenueName()+")");
                    vh.l4.setText("("+payloadRight.getVenueName()+")");
                }
            } else {
                vh.l1.setText(payloadLeft.getHalls().get(MySingleton.getInstance().getCompareHallIndex1()).getHallName());
                vh.l2.setText(payloadRight.getHalls().get(MySingleton.getInstance().getCompareHallIndex2()).getHallName());
                vh.l3.setText("("+payloadLeft.getVenueName()+")");
                vh.l4.setText("("+payloadRight.getVenueName()+")");
            }
            vh.l1.setTextColor(Color.RED);
            vh.l2.setTextColor(Color.RED);
            vh.l3.setVisibility(View.VISIBLE);
            vh.l4.setVisibility(View.VISIBLE);
        } else {
            vh.next.setVisibility(View.GONE);

            vh.l3.setVisibility(View.GONE);
            vh.l4.setVisibility(View.GONE);
//            vh.title.setText(payloadLeft.getAttributes().get(i - 1).getAttrName());
            //vh.title.setText(payloadLeft.getHalls().get(MySingleton.getInstance().getCompareHallIndex1()).getHallSizes().get(0).getSize() + " Sq. Ft.");
            vh.title.setText(capacityValues[i - 1]);
            vh.l1.setTextColor(ContextCompat.getColor(mContext, R.color.textColorBlack));
            vh.l2.setTextColor(ContextCompat.getColor(mContext, R.color.textColorBlack));
//            vh.l1.setText(payloadLeft.getAttributes().get(i - 1).getAttrValue());
//            vh.l2.setText(payloadRight.getAttributes().get(i - 1).getAttrValue());
            if (nextBool) {
                if (i < 5) {
                    if (i != 1) {
                        vh.l1.setText(payloadLeft.getHalls().get(MySingleton.getInstance().getCompareHallIndex1()).getCapacities().get(i - 2).getSeatingCapacity());
                        vh.l2.setText(payloadRight.getHalls().get(MySingleton.getInstance().getCompareHallIndex2()).getCapacities().get(i - 2).getSeatingCapacity());
                    }else {
                        vh.l1.setText("");
                        vh.l2.setText("");
                        vh.title.setTextColor(mContext.getResources().getColor(R.color.textColorBlack));
                        vh.title.setTypeface(null, Typeface.BOLD);
                    }
                } else {
                    String arr1[] = payloadLeft.getHalls().get(MySingleton.getInstance().getCompareHallIndex1()).getHallSizes().get(0).getSize().split("Sq. Ft.");
                    String arr2[] = payloadRight.getHalls().get(MySingleton.getInstance().getCompareHallIndex2()).getHallSizes().get(0).getSize().split("Sq. Ft.");
                    if(i==5){
                        vh.l1.setText("");
                        vh.l2.setText("");
                        vh.title.setTextColor(mContext.getResources().getColor(R.color.textColorBlack));
                        vh.title.setTypeface(null, Typeface.BOLD);
                    }
                    else if (i == 6) {
                        vh.l1.setText(arr1[0] + "Sq. Ft.");
                        vh.l2.setText(arr2[0] + "Sq. Ft.");
                    } else {
                        String tempStrOne = arr1[1];
                        String tempStrTwo = arr2[1];
                        tempStrOne = tempStrOne.replace("(", "");
                        tempStrOne = tempStrOne.replace(")", "");
                        tempStrOne = tempStrOne.replace("L:", "");
                        tempStrOne = tempStrOne.replace("W:", "");
                        tempStrOne = tempStrOne.replace("H:", "");
                        String tempArrOne[] = tempStrOne.split("\\|");

                        tempStrTwo = tempStrTwo.replace("(", "");
                        tempStrTwo = tempStrTwo.replace(")", "");
                        tempStrTwo = tempStrTwo.replace("L:", "");
                        tempStrTwo = tempStrTwo.replace("W:", "");
                        tempStrTwo = tempStrTwo.replace("H:", "");
                        String tempArrTwo[] = tempStrTwo.split("\\|");

                        vh.l1.setText(tempArrOne[i - 7]);
                        vh.l2.setText(tempArrTwo[i - 7]);

                    }
                }
            } else {
                if (i < 5) {
                    if (i != 1) {
                        vh.l1.setText(payloadLeft.getHalls().get(MySingleton.getInstance().getCompareHallIndex2()).getCapacities().get(i - 2).getSeatingCapacity());
                        vh.l2.setText(payloadRight.getHalls().get(MySingleton.getInstance().getCompareHallIndex3()).getCapacities().get(i - 2).getSeatingCapacity());
                    }else {
                        vh.l1.setText("");
                        vh.l2.setText("");
                        vh.title.setTextColor(mContext.getResources().getColor(R.color.textColorBlack));
                        vh.title.setTypeface(null, Typeface.BOLD);
                    }
                }else {
                    String arr1[] = payloadLeft.getHalls().get(MySingleton.getInstance().getCompareHallIndex2()).getHallSizes().get(0).getSize().split("Sq. Ft.");
                    String arr2[] = payloadRight.getHalls().get(MySingleton.getInstance().getCompareHallIndex3()).getHallSizes().get(0).getSize().split("Sq. Ft.");
                    if(i==5){
                        vh.l1.setText("");
                        vh.l2.setText("");
                        vh.title.setTextColor(mContext.getResources().getColor(R.color.textColorBlack));
                        vh.title.setTypeface(null, Typeface.BOLD);
                    }
                    else if (i == 6) {
                        vh.l1.setText(arr1[0] + "Sq. Ft.");
                        vh.l2.setText(arr2[0] + "Sq. Ft.");
                    } else {
                        String tempStrOne = arr1[1];
                        String tempStrTwo = arr2[1];
                        Log.d("tempStrOne", tempStrOne);
                        tempStrOne = tempStrOne.replace("(", "");
                        Log.d("tempStrOne", tempStrOne);
                        tempStrOne = tempStrOne.replace(")", "");
                        Log.d("tempStrOne", tempStrOne);
                        tempStrOne = tempStrOne.replace("L:", "");
                        Log.d("tempStrOne", tempStrOne);
                        tempStrOne = tempStrOne.replace("W:", "");
                        Log.d("tempStrOne", tempStrOne);
                        tempStrOne = tempStrOne.replace("H:", "");
                        Log.d("tempStrOne", tempStrOne);
                        String tempArrOne[] = tempStrOne.split("\\|");


                        Log.d("tempArrOne", Arrays.toString(tempArrOne));

                        tempStrTwo = tempStrTwo.replace("(", "");
                        tempStrTwo = tempStrTwo.replace(")", "");
                        tempStrTwo = tempStrTwo.replace("L:", "");
                        tempStrTwo = tempStrTwo.replace("W:", "");
                        tempStrTwo = tempStrTwo.replace("H:", "");
                        String tempArrTwo[] = tempStrTwo.split("\\|");

                        vh.l1.setText(tempArrOne[i - 7]);
                        vh.l2.setText(tempArrTwo[i - 7]);


                    }

                }
            }
        }
        return convertview;
    }

    public interface CompareAdapterCallback {
        void onNextClicked();
    }
}
