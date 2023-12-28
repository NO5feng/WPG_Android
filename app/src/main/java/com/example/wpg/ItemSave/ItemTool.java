package com.example.wpg.ItemSave;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wpg.R;
import com.example.wpg.utils.switchDate;

import java.lang.ref.WeakReference;
import java.util.List;

public class ItemTool {
    public static class InsertItemTask extends AsyncTask<Item, Void, Boolean> {
        @SuppressLint("StaticFieldLeak")
        private Context context;

        public InsertItemTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Item... items) {
            ItemDatabase database = ItemDatabase.getInstance(context);
            ItemDao itemDao = database.itemDao();
            itemDao.insert(items[0]);
            // 返回插入操作的结果，true表示成功，false表示失败
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "添加失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static class UpdateItemTask extends AsyncTask<Item, Void, Boolean> {
        private Context context;
        private int id;

        public UpdateItemTask(Context context, int id) {
            this.id = id;
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Item... items) {
            ItemDatabase database = ItemDatabase.getInstance(context);
            ItemDao itemDao = database.itemDao();
            itemDao.updateItem(id, items[0].getName(), items[0].getBirthDate(), items[0].getExpirationDate(), items[0].getRemindDate(), items[0].getSrc());
            // 返回更新操作的结果，true表示成功，false表示失败
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(context, "更新成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "更新失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static class DeleteItemTask extends AsyncTask<Integer, Void, Boolean> {
        @SuppressLint("StaticFieldLeak")
        private Context context;
        private final int id;

        public DeleteItemTask(Context context, int id) {
            this.context = context;;
            this.id = id;
        }

        @Override
        protected Boolean doInBackground(Integer... ids) {
            ItemDatabase database = ItemDatabase.getInstance(context);
            ItemDao itemDao = database.itemDao();
            Item item = itemDao.getItemById(id);
            if (item != null) {
                itemDao.delete(item);
                return true;
            } else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static class CopyItemTask extends AsyncTask<Integer, Void, Boolean> {
        @SuppressLint("StaticFieldLeak")
        private Context context;
        private final int id;

        public CopyItemTask(Context context, int id) {
            this.context = context;;
            this.id = id;
        }

        @SuppressLint("WrongThread")
        @Override
        protected Boolean doInBackground(Integer... ids) {
            ItemDatabase database = ItemDatabase.getInstance(context);
            ItemDao itemDao = database.itemDao();
            Item item = itemDao.getItemById(id);
            if (item != null) {
                Item Copyitem = new Item();
                Copyitem.setName(item.getName());
                Copyitem.setBirthDate(item.getBirthDate());
                Copyitem.setExpirationDate(item.getExpirationDate());
                Copyitem.setRemindDate(item.getRemindDate());
                new InsertItemTask(context).execute(Copyitem);
                return true;
            } else {
                return false;
            }
        }
    }

    public static class GetAllItemsTask extends AsyncTask<Void, Void, List<Item>> {
        private Context context;
        private OnTaskCompleted listener;

        public GetAllItemsTask(Context context, OnTaskCompleted listener) {
            this.context = context;
            this.listener = listener;
        }

        @Override
        protected List<Item> doInBackground(Void... voids) {
            ItemDatabase database = ItemDatabase.getInstance(context);
            ItemDao itemDao = database.itemDao();
            return itemDao.getAllItems();
        }

        @Override
        protected void onPostExecute(List<Item> items) {
            listener.onTaskCompleted(items);
        }
    }

    public static class editItem extends AsyncTask<Void, Void, Item> {
        private final int id;
        private WeakReference<Activity> activityRef;
        private Context context;

        public editItem(Context context, Activity activity, int id) {
            activityRef = new WeakReference<>(activity);
            this.context = context;
            this.id = id;
        }

        @Override
        protected Item doInBackground(Void... voids) {
            ItemDatabase database = ItemDatabase.getInstance(context);
            ItemDao itemDao = database.itemDao();
            Item item = itemDao.getItemById(id);
            return item;
        }

        protected void onPostExecute(Item item) {
            Activity activity = activityRef.get();
            if (activity != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView dateText = activity.findViewById(R.id.birthDate_text);
                        TextView timeoutText = activity.findViewById(R.id.expirationDate_text);
                        TextView titleTextView = activity.findViewById(R.id.titleTextView);
                        EditText editText = activity.findViewById(R.id.add_editText);
                        dateText.setText(switchDate.convertTimestampToString(item.getBirthDate()));
                        timeoutText.setText(switchDate.convertTimestampToString(item.getExpirationDate()));
                        titleTextView.setText("编辑");
                        editText.setText(item.getName());
                    }
                });
            }
        }

    }

    public interface OnTaskCompleted {
        void onTaskCompleted(List<Item> items);
    }

}
