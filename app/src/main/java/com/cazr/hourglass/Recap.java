package com.cazr.hourglass;

import android.graphics.drawable.Drawable;

/**
 * Created by cgosiak on 8/11/2015.
 */
public class Recap {
    public String date;
    public Integer weight;
    public Integer lost;
    public Integer gained;
    public Integer recap_drawable;
    public Integer gained_lost_amt;

    public Recap(String date,Integer weight,Integer lost,Integer gained){
        this.date = date;
        this.weight = weight;
        this.lost = lost;
        this.gained = gained;

        if (this.gained > 0){
            recap_drawable = R.drawable.ic_up;
            this.gained_lost_amt = this.gained;
        }
        else {
            if (this.lost > 0){
                recap_drawable = R.drawable.ic_down;
                this.gained_lost_amt = this.lost;
            }
            else {
                recap_drawable = R.drawable.ic_back;
                this.gained_lost_amt = 0;
            }
        }
    }
}
