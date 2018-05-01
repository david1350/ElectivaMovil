package co.edu.uptc.primerformulario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 13/03/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final  int DATABASE_VERSION = 1;

    private static final String DATABASE_NOMBRE = "PruebaBD";

    public DatabaseHandler (Context context){
        super(context,DATABASE_NOMBRE,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE Registro" + "("
                + "id INTEGER PRIMARY KEY," + "nombre TEXT,"
                + "correo TEXT" + ")";
              //  + "telefono INTEGER," + "fechaNacimiento INTEGER"
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Registro");
        onCreate(db);
    }


    /**
     * Metodo para realizar un registro en la BD.
     * @param registro
     */
    public void addRegistro (Registro registro){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre",registro.getNombre());
        values.put("correo",registro.getCorreo());
        //values.put("telefono",registro.getTelefono());
        //values.put("fechaNacimiento",registro.getFecha());

        db.insert("Registro",null,values);
        db.close();
    }

    /**
     * Metodo que permite realizar la consulta por ID de un registro en la BD,
     * @param id
     * @return
     */
    public Registro getRegistro (int id){
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = db.query("Registro", new String[]{"id",
                "nombre","correo","telefono","fechaNacimiento"},"id" +"=?",
                new String[]{String.valueOf(id)},null,null,null,null);

        if (cursor!=null){
            cursor.moveToFirst();
        }

        Registro registro = new Registro();
        registro.setId(Integer.parseInt(cursor.getString(0)));
        registro.setNombre(cursor.getString(1));
        registro.setCorreo(cursor.getString(2));
        registro.setTelefono(Integer.parseInt(cursor.getString(3)));
        registro.setFecha(Integer.parseInt(cursor.getString(4)));

        return registro;
    }


    /**
     * Metoodo que permite retornar una lista de registros consultados en la BD.
     * @return
     */
    public List<Registro> getRegistros (){
        List<Registro> contactList  = new ArrayList<>();

        String selectQuery = "SELECT * FROM REGISTRO";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do{
                Registro registro = new Registro();
                registro.setId(Integer.parseInt(cursor.getString(0)));
                registro.setNombre(cursor.getString(1));
                registro.setCorreo(cursor.getString(2));
                //registro.setTelefono(Integer.parseInt(cursor.getString(3)));
                //registro.setFecha(Integer.parseInt(cursor.getString(4)));

                contactList.add(registro);


            } while (cursor.moveToNext());
        }

        return  contactList;
    }


    /**
     * Metodo que permite la actualizacion de un registro como parametro.
     * @param registro
     * @return
     */
    public int updateRegistro (Registro registro){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values  = new ContentValues();
        values.put("nombre",registro.getNombre());
        values.put("correo", registro.getCorreo());
        values.put("telefono", registro.getTelefono());
        values.put("fechaNacimiento", registro.getFecha());

        return db.update("Registro",values,"id"+"=?",
                new String[]{String.valueOf(registro.getId())});
    }

    /**
     * Metodo que permite la elimminacion de un registro en la BD.
     * @param registro
     */
    public void deleteRegistro (Registro registro){
        SQLiteDatabase bd = this.getWritableDatabase();
        bd.delete("Registro","id" + "=?",
                new String[]{String.valueOf(registro.getId())});
        bd.close();
    }


}
