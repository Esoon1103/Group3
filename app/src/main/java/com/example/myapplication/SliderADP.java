package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;

class SliderAdp extends SliderViewAdapter<SliderAdp.Holder> {
    int[] image;

    //create constrictor
    public SliderAdp(int[] image){
        this.image=image;

    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        //initialize view
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider,parent,false);

        //return view

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(SliderAdp.Holder viewHolder, int position) {
//set image on image view
        viewHolder.imageView.setImageResource(image[position]);
    }

    @Override
    public int getCount() {
        return image.length;
    }

    public class Holder extends SliderViewAdapter.ViewHolder {
        //Initialize varable
        ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            //Assign Variable
            imageView=itemView.findViewById(R.id.image_view);
        }
    }
}
