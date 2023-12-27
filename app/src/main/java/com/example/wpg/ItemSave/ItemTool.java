package com.example.wpg.ItemSave;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

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
                // 插入成功
                Toast.makeText(context, "插入成功", Toast.LENGTH_SHORT).show();
            } else {
                // 插入失败
                Toast.makeText(context, "插入失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class UpdateItemTask extends AsyncTask<Item, Void, Boolean> {
        private Context context;

        public UpdateItemTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Item... items) {
            ItemDatabase database = ItemDatabase.getInstance(context);
            ItemDao itemDao = database.itemDao();
            itemDao.update(items[0]);
            // 返回更新操作的结果，true表示成功，false表示失败
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                // 更新成功
                Toast.makeText(context, "更新成功", Toast.LENGTH_SHORT).show();
            } else {
                // 更新失败
                Toast.makeText(context, "更新失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class DeleteItemTask extends AsyncTask<Item, Void, Boolean> {
        private Context context;

        public DeleteItemTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Item... items) {
            ItemDatabase database = ItemDatabase.getInstance(context);
            ItemDao itemDao = database.itemDao();
            itemDao.delete(items[0]);
            // 返回删除操作的结果，true表示成功，false表示失败
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                // 删除成功
                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
            } else {
                // 删除失败
                Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
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

    public interface OnTaskCompleted {
        void onTaskCompleted(List<Item> items);
    }

}
