package main.demo.mobads.baidu.com.banner.bananer;

/**
 * Created by jiangrenming on 2018/4/12.
 */

public interface MZHolderCreator <VH extends MZViewHolder> {

    /**
     * 创建ViewHolder
     * @return
     */
    public VH createViewHolder();
}
