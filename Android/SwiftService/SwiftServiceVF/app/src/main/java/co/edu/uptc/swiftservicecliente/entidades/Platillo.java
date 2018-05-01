package co.edu.uptc.swiftservicecliente.entidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;


public class Platillo {

    private Integer ID;
    private String nombre;
    private String descripcion;
    private String categoria;
    private Bitmap imagen;
    private String metadataImagen;
    private Integer precio;

    public Platillo(){

    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public String getMetadataImagen() {
        return metadataImagen;
    }

    public void setMetadataImagen(String metadataImagen) {
        this.metadataImagen = metadataImagen;
        try {

            int alto=140;//alto en pixeles
            int ancho=140;//ancho en pixeles
            byte[] byteCode = Base64.decode(metadataImagen,Base64.DEFAULT);

            Bitmap foto=BitmapFactory.decodeByteArray(byteCode,0,byteCode.length);

            this.imagen=Bitmap.createScaledBitmap(foto,alto,ancho,true);

        }catch (Exception e){
            System.out.println("errror_entidad_platillo " + e);
        }

    }


}
