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
                Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
            } else {
                // 插入失败
                Toast.makeText(context, "添加失败", Toast.LENGTH_SHORT).show();
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

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "复制失败", Toast.LENGTH_SHORT).show();
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
