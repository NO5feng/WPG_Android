package com.example.wpg.ItemSave;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

@Dao
public interface ItemDao {
    @Insert
    void insert(Item item);

    @Delete
    void delete(Item item);

    @Query("SELECT * FROM items")
    List<Item> getAllItems();

    @Query("SELECT * FROM items WHERE id = :itemId")
    Item getItemById(int itemId);

    @Query("UPDATE items SET name = :newName, birthDate = :newBirthDate, expirationDate = :newExpirationDate, remindDate = :newRemindDate, src = :newSrc WHERE id = :itemId")
    void updateItem(int itemId, String newName, Long newBirthDate, Long newExpirationDate, Long newRemindDate,  String newSrc);

}

