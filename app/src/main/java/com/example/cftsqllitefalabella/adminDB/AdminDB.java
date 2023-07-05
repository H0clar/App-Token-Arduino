package com.example.cftsqllitefalabella.adminDB;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.cftsqllitefalabella.adminDB.ClienteConstrac.clienteEntry;
import com.example.cftsqllitefalabella.adminDB.CuentaConstrac.cuentaEntry;
import com.example.cftsqllitefalabella.modelo.Cliente;
import com.example.cftsqllitefalabella.modelo.Cuenta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AdminDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "cftsanagustin.db";

    public AdminDB(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + clienteEntry.TABLE_NAME + " ("
                        + clienteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + clienteEntry.NOMBRE + " TEXT NOT NULL, "
                        + clienteEntry.APELLIDOS + " TEXT NOT NULL, "
                        + clienteEntry.CORREO + " TEXT NOT NULL, "
                        + clienteEntry.PASSWORD + " TEXT NOT NULL, "
                        + clienteEntry.COMUNA + " TEXT NOT NULL, "
                        + clienteEntry.NUMCUENTA + " TEXT NOT NULL, "
                        + "UNIQUE (" + clienteEntry._ID + "))"
        );



        sqLiteDatabase.execSQL(
                "CREATE TABLE comuna ("
                        + "id_comuna_ID INTEGER PRIMARY KEY, "
                        + "nombre_comuna TEXT NOT NULL, "
                        + "UNIQUE ( id_comuna_ID ) )"
        );


        String insertQuery = "INSERT INTO comuna (id_comuna_ID, nombre_comuna) VALUES "
                + "(116, 'TALCA'), "
                + "(117, 'CONSTITUCIÓN'), "
                + "(118, 'CUREPTO'), "
                + "(119, 'EMPEDRADO'), "
                + "(120, 'MAULE'), "
                + "(121, 'PELARCO'), "
                + "(122, 'PENCAHUE'), "
                + "(123, 'RÍO CLARO'), "
                + "(124, 'SAN CLEMENTE'), "
                + "(125, 'SAN RAFAEL'), "
                + "(126, 'CAUQUENES'), "
                + "(127, 'CHANCO'), "
                + "(128, 'PELLUHUE'), "
                + "(129, 'CURICÓ'), "
                + "(130, 'HUALAÑÉ'), "
                + "(131, 'LICANTÉN'), "
                + "(132, 'MOLINA'), "
                + "(133, 'RAUCO'), "
                + "(134, 'ROMERAL'), "
                + "(135, 'SAGRADA FAMILIA'), "
                + "(136, 'TENO'), "
                + "(137, 'VICHUQUÉN'), "
                + "(138, 'LINARES'), "
                + "(139, 'COLBÚN'), "
                + "(140, 'LONGAVÍ'), "
                + "(141, 'PARRAL'), "
                + "(142, 'RETIRO'), "
                + "(143, 'SAN JAVIER'), "
                + "(144, 'VILLA ALEGRE'), "
                + "(145, 'YERBAS BUENAS')";

        sqLiteDatabase.execSQL(insertQuery);

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + cuentaEntry.TABLE_NAME + " ("
                        + cuentaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + cuentaEntry.NCUENTA + " TEXT NOT NULL, "
                        + cuentaEntry.SALDO + " TEXT NOT NULL, "
                        + cuentaEntry.TIPOCUENTA + " TEXT NOT NULL, "
                        + "UNIQUE (" + cuentaEntry._ID + "))"
        );

        //insertar un cliente admin pa probar

        String insertQuery2 = "INSERT INTO " + clienteEntry.TABLE_NAME + " ("
                + clienteEntry.NOMBRE + ", "
                + clienteEntry.APELLIDOS + ", "
                + clienteEntry.CORREO + ", "
                + clienteEntry.PASSWORD + ", "
                + clienteEntry.COMUNA + ", "
                + clienteEntry.NUMCUENTA + ") VALUES ('admin', 'admin', 'admin@admin.cl', '12345', 'TALCA', '19043878-3')";

        sqLiteDatabase.execSQL(insertQuery2);

        String insertQuery3 = "INSERT INTO " + cuentaEntry.TABLE_NAME + " ("
                + cuentaEntry.NCUENTA + ", "
                + cuentaEntry.SALDO + ", "
                + cuentaEntry.TIPOCUENTA + ") VALUES ('19043878-3', '1000000', 'Cuenta Corriente')";
        sqLiteDatabase.execSQL(insertQuery3);

    }



    public long GuardarCliente(Cliente cliente){
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(clienteEntry.TABLE_NAME,
                null,
                cliente.toContentValue());
    }

    public long CreaCuenta(String numerodecuenta) {
        SQLiteDatabase db = getWritableDatabase();
        Cuenta cuenta = new Cuenta(numerodecuenta,  0.0f, "Cuenta Corriente");
        return db.insert (cuentaEntry. TABLE_NAME,
                null,
                cuenta.toContentValue());
    }

    @SuppressLint("Range")
    public int ConsultaAccesoCliente(String correo, String passwd){
        SQLiteDatabase db = getReadableDatabase();
        String[] columas = {clienteEntry._ID};
        String seleccion = clienteEntry.CORREO + " = ? AND " + clienteEntry.PASSWORD +" = ?";
        String[] seleccionArgs = {correo, passwd};

        Cursor cursor = db.query(
                clienteEntry.TABLE_NAME,
                columas,
                seleccion,
                seleccionArgs,
                null,
                null,
                null);

        int id = -1;

        if (cursor.moveToFirst()){
            id =  cursor.getInt(cursor.getColumnIndex(clienteEntry._ID));
        }

        cursor.close();
        return id;
    }


    public boolean ConsultaCuentaExistente(String correo, String numeroCuenta) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columnas = {clienteEntry._ID};
        String seleccion = clienteEntry.CORREO + " = ? OR " + clienteEntry.NUMCUENTA + " = ?";
        String[] seleccionArgs = {correo, numeroCuenta};

        Cursor cursor = db.query(
                clienteEntry.TABLE_NAME,
                columnas,
                seleccion,
                seleccionArgs,
                null,
                null,
                null
        );

        boolean existeRegistro = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) {
            cursor.close();
        }
        return existeRegistro;
    }

    public Cursor getAllComunas(){
        return getReadableDatabase().query("comuna",null,null,null,null,null,null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public Cursor obtenerClienteConCuenta(int clienteId) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " +
                "clientes." + clienteEntry._ID + ", " +
                clienteEntry.NOMBRE + ", " +
                clienteEntry.APELLIDOS + ", " +
                clienteEntry.CORREO + ", " +
                clienteEntry.PASSWORD + ", " +
                clienteEntry.COMUNA + ", " +
                "clientes." + clienteEntry.NUMCUENTA + ", " +
                "cuenta." + cuentaEntry.SALDO + ", " +
                cuentaEntry.TIPOCUENTA +
                " FROM " + clienteEntry.TABLE_NAME + " clientes" +
                " JOIN " + cuentaEntry.TABLE_NAME + " cuenta" +
                " ON clientes." + clienteEntry.NUMCUENTA + " = cuenta." + cuentaEntry.NCUENTA +
                " WHERE clientes." + clienteEntry._ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(clienteId)});
        return cursor;
    }



}
