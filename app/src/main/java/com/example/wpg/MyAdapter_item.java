//package com.example.wpg;
//
//import android.graphics.Typeface;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.wpg.entity.Item;
//import com.example.wpg.utils.switchDate;
//
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//// MyAdapter.java
//public class MyAdapter_item extends RecyclerView.Adapter<MyAdapter_item.ViewHolder> {
//    private List<Item> dataList;
//    private String name;
//    private long birthDate;
//    private long expirationDate;
//
//    private OnItemClickListener onItemClickListener;
//
//    // 构造方法
//    public MyAdapter_item(List<Item> dataList) {
//        this.dataList = dataList;
//    }
//
//    // 创建 ViewHolder
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_layout, parent, false);
//        return new ViewHolder(view);
//    }
//
//    // 绑定数据
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Item item = dataList.get(position);
//        name = item.getName();
//        birthDate = item.getBirthDate();
//        expirationDate = item.getExpirationDate();
//
//        holder.itemNameTextView.setText(name);
//        disposeDate(holder, birthDate, expirationDate);
//
//    }
//
//    // 获取数据项数量
//    @Override
//    public int getItemCount() {
//        return dataList.size();
//    }
//
//    // 定义 ViewHolder
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        View itemStateBoxView;
//        TextView itemNameTextView;
//        TextView itemStateTextView;
//        TextView itemDateTextView;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            itemNameTextView = itemView.findViewById(R.id.item_name);
//            itemStateTextView = itemView.findViewById(R.id.item_state);
//            itemDateTextView = itemView.findViewById(R.id.item_date);
//            itemStateBoxView = itemView.findViewById(R.id.item_state_box);
//
//            // 设置点击事件监听器
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            if (onItemClickListener != null) {
//                onItemClickListener.onItemClick(getAdapterPosition());
//            }
//        }
//    }
//
//    // 点击事件监听器接口
//    public interface OnItemClickListener {
//        void onItemClick(int position);
//    }
//
//    // 设置点击事件监听器
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }
//
//    private void disposeDate(@NonNull ViewHolder holder, long timestamp1, long timestamp2) {
//        Date date1 = switchDate.convertTimestampToDate(timestamp1);
//        Date date2 = switchDate.convertTimestampToDate(timestamp2);
//
//        long difference = Math.abs(date1.getTime() - date2.getTime());
//        long differenceInDays = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
//        String differenceInDaysStr = Long.toString(differenceInDays);
//
//        boolean type = date1.before(date2);
//        if (date1.before(date2)) {
//            // 字体变粗
//            Typeface originalTypeface = holder.itemStateTextView.getTypeface();
//            Typeface boldTypeface = Typeface.create(originalTypeface, Typeface.BOLD);
//            holder.itemStateTextView.setTypeface(boldTypeface);
//
//            holder.itemStateTextView.setText(differenceInDaysStr);
//            holder.itemDateTextView.setText(R.string.day);
//            holder.itemStateBoxView.setBackgroundResource(R.drawable.rounded_background_right_pink);
//        }else{
//            holder.itemStateTextView.setText(R.string.overdue);
//            holder.itemDateTextView.setText(differenceInDaysStr);
//        }
//
//    }
//
//}
//
