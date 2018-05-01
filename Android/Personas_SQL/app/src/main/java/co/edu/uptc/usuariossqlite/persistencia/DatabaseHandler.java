package co.edu.uptc.usuariossqlite.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.usuariossqlite.entidades.Persona;

/**
 * Created by Frank on 13/03/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final  int DATABASE_VERSION = 1;

    private static final String DATABASE_NOMBRE = "BDSQLitePersonas";

    public DatabaseHandler(Context context){
        super(context,DATABASE_NOMBRE,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE Persona" + "("
                + "id INTEGER PRIMARY KEY," + "nombre TEXT,"
                + "correo TEXT," +  "telefono TEXT," +  "fechaNacimiento TEXT" +  ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Persona");
        onCreate(db);
    }


    /**
     * Metodo para realizar un registro en la BD.
     * @param persona
     */
    public void addPersona (Persona persona){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre",persona.getNombre());
        values.put("correo",persona.getCorreo());
        values.put("telefono",persona.getTelefono());
        values.put("fechaNacimiento",persona.getFecha());

        db.insert("Persona",null,values);
        db.close();
    }

    /**
     * Metodo que permite realizar la consulta por ID de una Persona en la BD,
     * @param id
     * @return
     */
    public Persona getPersona (int id){
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = db.query("Persona", new String[]{"id",
                "nombre","correo","telefono","fechaNacimiento"},"id" +"=?",
                new String[]{String.valueOf(id)},null,null,null,null);

        if (cursor!=null){
            cursor.moveToFirst();
        }

        Persona persona = new Persona();
        persona.setId(Integer.parseInt(cursor.getString(0)));
        persona.setNombre(cursor.getString(1));
        persona.setCorreo(cursor.getString(2));
        persona.setTelefono(cursor.getString(3));
        persona.setFecha(cursor.getString(4));

        return persona;
    }


    /**
     * Metoodo que permite retornar una lista de registros consultados en la BD.
     * @return
     */
    public List<Persona> getPersonas (){
        List<Persona> contactList  = new ArrayList<>();

        String selectQuery = "SELECT * FROM PERSONA";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do{
                Persona persona = new Persona();
                persona.setId(Integer.parseInt(cursor.getString(0)));
                persona.setNombre(cursor.getString(1));
                persona.setCorreo(cursor.getString(2));
                persona.setTelefono(cursor.getString(3));
                persona.setFecha(cursor.getString(4));

                contactList.add(persona);


            } while (cursor.moveToNext());
        }

        return  contactList;
    }


    /**
     * Metodo que permite la actualizacion de un persona como parametro.
     * @param persona
     * @return
     */
    public int updatePersona (Persona persona){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values  = new ContentValues();
        values.put("nombre", persona.getNombre());
        values.put("correo", persona.getCorreo());
        values.put("telefono", persona.getTelefono());
        values.put("fechaNacimiento", persona.getFecha());

        return db.update("Persona",values,"id"+"=?",
                new String[]{String.valueOf(persona.getId())});
    }

    /**
     * Metodo que permite la elimminacion de un persona en la BD.
     * @param persona
     */
    public void deleteRegistro (Persona persona){
        SQLiteDatabase bd = this.getWritableDatabase();
        bd.delete("Persona","id" + "=?",
                new String[]{String.valueOf(persona.getId())});
        bd.close();
    }


}
