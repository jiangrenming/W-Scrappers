package main.demo.mobads.baidu.com.banner.bananer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 *
 * @author jiangrenming
 * @date 2018/4/12
 */

public class ScaleYTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.9F;
    @Override
    public void transformPage(View page, float position) {

        if(position < -1){
            page.setScaleY(MIN_SCALE);
        }else if(position<= 1){
            //
            float scale = Math.max(MIN_SCALE,1 - Math.abs(position));
            page.setScaleY(scale);
            /*page.setScaleX(scale);
            if(position<0){
                page.setTranslationX(width * (1 - scale) /2);
            }else{
                page.setTranslationX(-width * (1 - scale) /2);
            }*/

        }else{
            page.setScaleY(MIN_SCALE);
        }
    }

}
