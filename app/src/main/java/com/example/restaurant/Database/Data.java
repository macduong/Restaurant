package com.example.restaurant.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.restaurant.Objects.Food;
import com.example.restaurant.Objects.Manager;
import com.example.restaurant.Objects.Table;
import com.example.restaurant.Objects.TableFood;

import java.util.ArrayList;
import java.util.List;

public class Data extends SQLiteOpenHelper {

    public static final String DB_NAME = "RESTAURANT_DATA";
    public static final int VERSION = 1;
    private SQLiteDatabase database;
    private ContentValues contentValues;
    private Cursor cursor;

    //ten bien tinh cho de viet ham
    public static final String FOOD_TABLE = "foodTable";
    public static final String FOOD_ID = "foodId";
    public static final String FOOD_NAME = "foodName";
    public static final String FOOD_COST = "foodCost";
    public static final String FOOD_UNIT = "foodUnit";

    public static final String MANAGER_TABLE = "managerTable";
    public static final String MANAGER_ID = "managerId";
    public static final String MANAGER_FULLNAME = "managerFullname";
    public static final String MANAGER_USERNAME = "managerUsername";
    public static final String MANAGER_PASSWORD = "managerPassword";

    public static final String TABLE_TABLE = "tableTable";
    public static final String TABLE_ID = "tableId";
    public static final String TABLE_ISAVAILABLE = "tableIsavailable";
    public static final String TABLE_PHONENUM = "tablePhonenum";
    public static final String TABLE_GUESTNAME = "tableGuestname";

    public static final String TABLEFOOD_TABLE = "tablefoodTable";
    public static final String TABLEFOOD_ID = "tablefoodId";
    public static final String TABLEFOOD_TABLEID = "tablefoodTableid";
    public static final String TABLEFOOD_TABLEISAVAILABLE = "tablefoodTableIsAvailable";
    public static final String TABLEFOOD_TABLEPHONENUM = "tablefoodTablePhonenum";
    public static final String TABLEFOOD_TABLEGUESTNAME = "tablefoodTableGuestname";
    public static final String TABLEFOOD_FOODID = "tablefoodFoodid";
    public static final String TABLEFOOD_FOODNAME = "tablefoodFoodName";
    public static final String TABLEFOOD_FOODCOST = "tablefoodFoodCost";
    public static final String TABLEFOOD_FOODUNIT = "tablefoodFoodUnit";
    public static final String TABLEFOOD_AMOUNT = "tablefoodAmount";


    public Data(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Tao cac bang khoi tao trong CSDL
        String createManagers = "CREATE TABLE " + MANAGER_TABLE + " (" +
                MANAGER_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                MANAGER_FULLNAME + " TEXT, " +
                MANAGER_USERNAME + " TEXT, " +
                MANAGER_PASSWORD + " TEXT)";
        db.execSQL(createManagers);

        String createFoods = "CREATE TABLE " + FOOD_TABLE + " (" +
                FOOD_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                FOOD_NAME + " TEXT, " +
                FOOD_COST + " INT, " +
                FOOD_UNIT + " TEXT)";
        db.execSQL(createFoods);

        String createTables = "CREATE TABLE " + TABLE_TABLE + " (" +
                TABLE_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                TABLE_ISAVAILABLE + " INT, " +
                TABLE_PHONENUM + " TEXT, " +
                TABLE_GUESTNAME + " TEXT)";
        db.execSQL(createTables);

        String createTableFood = "CREATE TABLE " + TABLEFOOD_TABLE + " (" +
                TABLEFOOD_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                TABLEFOOD_TABLEID + " INT, " +
                TABLEFOOD_TABLEISAVAILABLE + " INT, " +
                TABLEFOOD_TABLEPHONENUM + " TEXT, " +
                TABLEFOOD_TABLEGUESTNAME + " TEXT, " +
                TABLEFOOD_FOODID + " INT, " +
                TABLEFOOD_FOODNAME + " TEXT, " +
                TABLEFOOD_FOODCOST + " INT, " +
                TABLEFOOD_FOODUNIT + " TEXT, " +
                TABLEFOOD_AMOUNT + " INT)";
        db.execSQL(createTableFood);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + FOOD_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + MANAGER_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLEFOOD_TABLE);
        }
    }

    //thao tac voi bang Managers:

    //Ham login, tra ve managerId
    public int login(String user, String pass) {
        int mId = 0;
        String login = "SELECT * FROM " + MANAGER_TABLE +
                " WHERE " + MANAGER_USERNAME + " = '" + user + "' AND " + MANAGER_PASSWORD + " = '" + pass + "'";
        database = getWritableDatabase();
        cursor = database.rawQuery(login, null);
        if (cursor.moveToFirst() && cursor.getCount() > 0) {
            mId = cursor.getInt(0);
        }
        closeDB();
        return mId;
    }

    //Ham kiem tra trung username, tra ve bool
    public boolean checkUsedUsername(String user) {
        String check = "SELECT * FROM " + MANAGER_TABLE + " WHERE " + MANAGER_USERNAME + " = '" + user + "'";
        boolean result = false;
        database = getReadableDatabase();
        cursor = database.rawQuery(check, null);
        if (cursor.getCount() > 0) {
            result = true;
        }
        closeDB();
        return result;
    }

    //Ham insert manager vao bang
    public void insertManager(Manager manager) {
        database = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(MANAGER_FULLNAME, manager.getFullName());
        contentValues.put(MANAGER_USERNAME, manager.getUserName());
        contentValues.put(MANAGER_PASSWORD, manager.getPassword());
        try {
            database.insert(MANAGER_TABLE, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeDB();
    }

    //Ham sua Manager trong bang
    public void updateManager(Manager manager) {
        String getM = "SELECT * FROM " + MANAGER_TABLE + " WHERE " + MANAGER_ID + " = '" + manager.getId() + "'";
        database = getWritableDatabase();
        cursor = database.rawQuery(getM, null);
        if (cursor.moveToFirst()) {
            contentValues = new ContentValues();
            contentValues.put(MANAGER_ID, manager.getId());
            contentValues.put(MANAGER_FULLNAME, manager.getFullName());
            contentValues.put(MANAGER_USERNAME, manager.getUserName());
            contentValues.put(MANAGER_PASSWORD, manager.getPassword());
            database.update(MANAGER_TABLE, contentValues, MANAGER_ID + " =?", new String[]{String.valueOf(manager.getId())});
        }
        closeDB();
    }

    //Ham xoa Manager khoi bang
    public void deleteManagerById(int id) {
        database = getWritableDatabase();
        database.delete(MANAGER_TABLE, MANAGER_ID + " =?", new String[]{String.valueOf(id)});
        closeDB();
    }

    //Ham tim kiem Manager trong bang theo id
    public Manager searchOneManagerById(int mId) {
        database = getReadableDatabase();
        String search = "SELECT * FROM " + MANAGER_TABLE + " WHERE " + MANAGER_ID + " = '" + mId + "'";
        Manager manager = new Manager();
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String fullName = cursor.getString(1);
            String username = cursor.getString(2);
            String password = cursor.getString(3);
            manager = new Manager(id, fullName, username, password);
        }
        closeDB();
        return manager;
    }


    //thao tac voi bang Food:

    //Ham insert Food vao bang
    public void insertFood(Food food) {
        database = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(FOOD_NAME, food.getName());
        contentValues.put(FOOD_COST, food.getCost());
        contentValues.put(FOOD_UNIT, food.getUnit());
        try {
            database.insert(FOOD_TABLE, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeDB();
    }

    //Ham sua Food trong bang
    public void updateFood(Food food) {
        String getF = "SELECT * FROM " + FOOD_TABLE + " WHERE " + FOOD_ID + " = '" + food.getId() + "'";
        database = getWritableDatabase();
        cursor = database.rawQuery(getF, null);
        if (cursor.moveToFirst()) {
            contentValues = new ContentValues();
            contentValues.put(FOOD_ID, food.getId());
            contentValues.put(FOOD_NAME, food.getName());
            contentValues.put(FOOD_COST, food.getCost());
            contentValues.put(FOOD_UNIT, food.getUnit());
            database.update(FOOD_TABLE, contentValues, FOOD_ID + " =?", new String[]{String.valueOf(food.getId())});
        }
        closeDB();
    }

    //Ham xoa toan bo Food khoi bang
    public void deleteAllFood() {
        database = getWritableDatabase();
        String drop = "DROP TABLE IF EXISTS " + FOOD_TABLE;
        database.execSQL(drop);
        String createFoods = "CREATE TABLE " + FOOD_TABLE + " (" +
                FOOD_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                FOOD_NAME + " TEXT, " +
                FOOD_COST + " INT, " +
                FOOD_UNIT + " TEXT)";
        database.execSQL(createFoods);
        closeDB();
    }

    //Ham xoa 1 Food khoi bang
    public void deleteFoodById(int id) {
        database = getWritableDatabase();
        database.delete(FOOD_TABLE, FOOD_ID + " =?", new String[]{String.valueOf(id)});
        database.delete(TABLEFOOD_TABLE, TABLEFOOD_FOODID + " =?", new String[]{String.valueOf(id)});
        closeDB();
    }

    //Ham search tat ca Food trong bang
    public List<Food> searchAllFood() {
        database = getReadableDatabase();
        String search = "SELECT * FROM " + FOOD_TABLE;
        List<Food> foodList = new ArrayList<>();
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int cost = cursor.getInt(2);
                String unit = cursor.getString(3);
                foodList.add(new Food(id, name, cost, unit));
            }
            while (cursor.moveToNext());
        }
        closeDB();
        return foodList;
    }

    //Ham search 1 food theo ID
    public Food searchFoodById(int fId) {
        database = getReadableDatabase();
        String search = "SELECT * FROM " + FOOD_TABLE + " WHERE " + FOOD_ID + " = '" + fId + "'";
        Food food = new Food();
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int cost = cursor.getInt(2);
            String unit = cursor.getString(3);
            food = new Food(id, name, cost, unit);
        }
        closeDB();
        return food;
    }

    //Ham search  food theo name
    public List<Food> searchAllFoodByName(String fName) {
        database = getReadableDatabase();
        String search = "SELECT * FROM " + FOOD_TABLE + " WHERE " + FOOD_NAME + " LIKE '%" + fName + "%'";
        List<Food> foodList = new ArrayList<>();
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int cost = cursor.getInt(2);
                String unit = cursor.getString(3);
                foodList.add(new Food(id, name, cost, unit));
            }
            while (cursor.moveToNext());
        }
        closeDB();
        return foodList;
    }


    //thao tac voi bang Table:

    //Ham insert Table vao bang
    public void insertTable(Table table) {
        database = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(TABLE_ISAVAILABLE, table.getIsAvailable());
        contentValues.put(TABLE_PHONENUM, table.getPhoneNum());
        contentValues.put(TABLE_GUESTNAME, table.getGuestName());
        try {
            database.insert(TABLE_TABLE, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeDB();
    }

    //Ham sua Table trong bang
    public void updateTable(Table table) {
        String getF = "SELECT * FROM " + TABLE_TABLE + " WHERE " + TABLE_ID + " = '" + table.getId() + "'";
        database = getWritableDatabase();
        cursor = database.rawQuery(getF, null);
        if (cursor.moveToFirst()) {
            contentValues = new ContentValues();
            contentValues.put(TABLE_ID, table.getId());
            contentValues.put(TABLE_ISAVAILABLE, table.getIsAvailable());
            contentValues.put(TABLE_PHONENUM, table.getPhoneNum());
            contentValues.put(TABLE_GUESTNAME, table.getGuestName());
            database.update(TABLE_TABLE, contentValues, TABLE_ID + " =?", new String[]{String.valueOf(table.getId())});
        }
        closeDB();
    }

    //Ham xoa toan bo Table khoi bang
    public void deleteAllTables() {
        database = getWritableDatabase();
        String drop = "DROP TABLE IF EXISTS " + TABLE_TABLE;
        database.execSQL(drop);
        String createTables = "CREATE TABLE " + TABLE_TABLE + " (" +
                TABLE_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                TABLE_ISAVAILABLE + " INT, " +
                TABLE_PHONENUM + " TEXT, " +
                TABLE_GUESTNAME + " TEXT)";
        database.execSQL(createTables);
        closeDB();
    }

    //Ham kiem tra bang Table ton tai
    public boolean tableDataExists() {
        boolean result = false;
        database = getReadableDatabase();
        String search = "SELECT * FROM " + TABLE_TABLE;
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst() && cursor.getCount() > 0) {
            result = true;
        }
        closeDB();
        return result;
    }

    //Ham search toan bo table co san
    public List<Table> searchAllAvailableTables() {
        database = getWritableDatabase();
        String search = "SELECT * FROM " + TABLE_TABLE + " WHERE " + TABLE_ISAVAILABLE + " = '1'";
        List<Table> tableList = new ArrayList<>();
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int isAvailable = cursor.getInt(1);
                String phone = cursor.getString(2);
                String guestName = cursor.getString(3);
                tableList.add(new Table(id, isAvailable, phone, guestName));
            }
            while (cursor.moveToNext());
        }
        closeDB();
        return tableList;
    }

    //Ham search toan bo table dang ban
    public List<Table> searchAllUnavailableTables() {
        database = getWritableDatabase();
        String search = "SELECT * FROM " + TABLE_TABLE + " WHERE " + TABLE_ISAVAILABLE + " = '0'";
        List<Table> tableList = new ArrayList<>();
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int isAvailable = cursor.getInt(1);
                String phone = cursor.getString(2);
                String guestName = cursor.getString(3);
                tableList.add(new Table(id, isAvailable, phone, guestName));
            }
            while (cursor.moveToNext());
        }
        closeDB();
        return tableList;
    }

    //Ham search toan bo table
    public List<Table> searchAllTables() {
        database = getWritableDatabase();
        String search = "SELECT * FROM " + TABLE_TABLE;
        List<Table> tableList = new ArrayList<>();
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int isAvailable = cursor.getInt(1);
                String phone = cursor.getString(2);
                String guestName = cursor.getString(3);
                tableList.add(new Table(id, isAvailable, phone, guestName));
            }
            while (cursor.moveToNext());
        }
        closeDB();
        return tableList;
    }

    //Ham search toan bo Table theo ID hoac ten hoac sdt
    public List<Table> searchAllTableByNameId(String nameOrId) {
        database = getReadableDatabase();
        String search = "SELECT * FROM " + TABLE_TABLE + " WHERE " + TABLE_GUESTNAME + " LIKE '%" + nameOrId + "%' OR "
                + TABLE_ID + " LIKE '%" + nameOrId + "%' OR "
                + TABLE_PHONENUM + " = '" + nameOrId + "'";
        List<Table> tableList = new ArrayList<>();
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int available = cursor.getInt(1);
                String phone = cursor.getString(2);
                String name = cursor.getString(3);
                tableList.add(new Table(id, available, phone, name));
            }
            while (cursor.moveToNext());
        }
        closeDB();
        return tableList;
    }

    //Ham search 1 Table theo ID
    public Table searchTableById(int tId) {
        database = getReadableDatabase();
        Table table = new Table();
        String search = "SELECT * FROM " + TABLE_TABLE + " WHERE " + TABLE_ID + " = '" + tId + "'";
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {

            int id = cursor.getInt(0);
            int available = cursor.getInt(1);
            String phone = cursor.getString(2);
            String name = cursor.getString(3);
            table = new Table(id, available, phone, name);

        }
        closeDB();
        return table;
    }


    //thao tac voi bang TableFood:

    //Ham insert TableFood vao bang
    public void insertTableFood(Table table, Food food, int amount) {
        database = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(TABLEFOOD_TABLEID, table.getId());
        contentValues.put(TABLEFOOD_TABLEISAVAILABLE, table.getIsAvailable());
        contentValues.put(TABLEFOOD_TABLEPHONENUM, table.getPhoneNum());
        contentValues.put(TABLEFOOD_TABLEGUESTNAME, table.getGuestName());

        contentValues.put(TABLEFOOD_FOODID, food.getId());
        contentValues.put(TABLEFOOD_FOODNAME, food.getName());
        contentValues.put(TABLEFOOD_FOODCOST, food.getCost());
        contentValues.put(TABLEFOOD_FOODUNIT, food.getUnit());

        contentValues.put(TABLEFOOD_AMOUNT, amount);

        try {
            database.insert(TABLEFOOD_TABLE, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeDB();
    }

    //Ham sua TableFood trong bang
    public void updateTableFood(TableFood tableFood) {
        String getF = "SELECT * FROM " + TABLEFOOD_TABLE + " WHERE " + TABLEFOOD_ID + " = '" + tableFood.getId() + "'";
        database = getWritableDatabase();
        cursor = database.rawQuery(getF, null);
        if (cursor.moveToFirst()) {
            contentValues = new ContentValues();

            contentValues.put(TABLEFOOD_TABLEID, tableFood.getTableId());
            contentValues.put(TABLEFOOD_TABLEISAVAILABLE, tableFood.getIsAvailable());

            contentValues.put(TABLEFOOD_TABLEPHONENUM, tableFood.getPhoneNum());
            contentValues.put(TABLEFOOD_TABLEGUESTNAME, tableFood.getGuestName());

            contentValues.put(TABLEFOOD_FOODID, tableFood.getFoodId());
            contentValues.put(TABLEFOOD_FOODNAME, tableFood.getName());
            contentValues.put(TABLEFOOD_FOODCOST, tableFood.getCost());
            contentValues.put(TABLEFOOD_FOODUNIT, tableFood.getUnit());

            contentValues.put(TABLEFOOD_AMOUNT, tableFood.getAmount());

            database.update(TABLEFOOD_TABLE, contentValues, TABLEFOOD_ID + " =?", new String[]{String.valueOf(tableFood.getId())});
        }
        closeDB();
    }

    //Ham xoa toan bo TableFood khoi bang
    public void deleteAllTableFood() {
        database = getWritableDatabase();
        String drop = "DROP TABLE IF EXISTS " + TABLEFOOD_TABLE;
        database.execSQL(drop);
        String createTableFood = "CREATE TABLE " + TABLEFOOD_TABLE + " (" +
                TABLEFOOD_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                TABLEFOOD_TABLEID + " INT, " +
                TABLEFOOD_TABLEISAVAILABLE + " INT, " +
                TABLEFOOD_TABLEPHONENUM + " TEXT, " +
                TABLEFOOD_TABLEGUESTNAME + " TEXT, " +
                TABLEFOOD_FOODID + " INT, " +
                TABLEFOOD_FOODNAME + " TEXT, " +
                TABLEFOOD_FOODCOST + " INT, " +
                TABLEFOOD_FOODUNIT + " TEXT, " +
                TABLEFOOD_AMOUNT + " INT)";
        database.execSQL(createTableFood);
        closeDB();
    }

    //Ham xoa TableFood khoi bang
    public void deleteTableFoodById(int id) {
        database = getWritableDatabase();
        database.delete(TABLEFOOD_TABLE, TABLEFOOD_ID + " =?", new String[]{String.valueOf(id)});
        closeDB();
    }

    //Ham search toan bo tableFood
    public List<TableFood> searchAllTableFoodByTableId(int tId) {
        database = getWritableDatabase();
        String search = "SELECT * FROM " + TABLEFOOD_TABLE + " WHERE " + TABLEFOOD_TABLEID + " = '" + tId + "' AND " + TABLEFOOD_TABLEISAVAILABLE + " = '0'";
        List<TableFood> tableFoodList = new ArrayList<>();
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int tableId = cursor.getInt(1);
                int isAvailable = cursor.getInt(2);
                String phoneNum = cursor.getString(3);
                String guestName = cursor.getString(4);
                int foodId = cursor.getInt(5);
                String foodName = cursor.getString(6);
                int foodCost = cursor.getInt(7);
                String foodUnit = cursor.getString(8);
                int amount = cursor.getInt(9);
                tableFoodList.add(new TableFood(id, tableId, isAvailable, phoneNum, guestName, foodId, foodName, foodCost, foodUnit, amount));
            }
            while (cursor.moveToNext());
        }
        closeDB();
        return tableFoodList;
    }

    //Ham search toan bo TableFood theo tableId va foodId
    public TableFood searchTableFoodByTFId(int tId, int fId) {
        database = getReadableDatabase();
        String search = "SELECT * FROM " + TABLEFOOD_TABLE + " WHERE " + TABLEFOOD_TABLEID + " = '" + tId + "' AND "
                + TABLEFOOD_FOODID + " = '" + fId + "'";
        TableFood tableFood = new TableFood();
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {

            int id = cursor.getInt(0);
            int tableId = cursor.getInt(1);
            int isAvailable = cursor.getInt(2);
            String phoneNum = cursor.getString(3);
            String guestName = cursor.getString(4);
            int foodId = cursor.getInt(5);
            String foodName = cursor.getString(6);
            int foodCost = cursor.getInt(7);
            String foodUnit = cursor.getString(8);
            int amount = cursor.getInt(9);
            tableFood = new TableFood(id, tableId, isAvailable, phoneNum, guestName, foodId, foodName, foodCost, foodUnit, amount);

        }
        closeDB();
        return tableFood;
    }

    //Ham search 1 TableFood theo ID
    public TableFood searchTableFoodById(int tfId) {
        database = getReadableDatabase();
        TableFood tableFood = new TableFood();
        String search = "SELECT * FROM " + TABLEFOOD_TABLE + " WHERE " + TABLEFOOD_ID + " = '" + tfId + "'";
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {

            int id = cursor.getInt(0);
            int tableId = cursor.getInt(1);
            int isAvailable = cursor.getInt(2);
            String phoneNum = cursor.getString(3);
            String guestName = cursor.getString(4);
            int foodId = cursor.getInt(5);
            String foodName = cursor.getString(6);
            int foodCost = cursor.getInt(7);
            String foodUnit = cursor.getString(8);
            int amount = cursor.getInt(9);
            tableFood = new TableFood(id, tableId, isAvailable, phoneNum, guestName, foodId, foodName, foodCost, foodUnit, amount);

        }
        closeDB();
        return tableFood;
    }


    private void closeDB() {
        if (database != null) {
            database.close();
        }
        if (contentValues != null) {
            contentValues.clear();
        }
        if (cursor != null) {
            cursor.close();
        }
    }
}
