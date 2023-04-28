/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import java.util.List;

/**
 *
 * @author michikato
 */
public class TutorAcademico extends Usuario{
    
    private int numeroEstudiantes;

    public TutorAcademico() {
    }

    public TutorAcademico(int numeroEstudiantes) {
        this.numeroEstudiantes = numeroEstudiantes;
    }

    public TutorAcademico(int numeroEstudiantes, int idRol, String contraseña, ProgramaEducativo programaEducativo, int numeroDePersonal, String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronicoInstitucional, List<Rol> listRoles) {
        super(idRol, contraseña, programaEducativo, numeroDePersonal, nombre, apellidoPaterno, apellidoMaterno, correoElectronicoInstitucional, listRoles);
        this.numeroEstudiantes = numeroEstudiantes;
    }

    

    public int getNumeroEstudiantes() {
        return numeroEstudiantes;
    }

    public void setNumeroEstudiantes(int numeroEstudiantes) {
        this.numeroEstudiantes = numeroEstudiantes;
    }
    
    @Override
    public String toString() {
        return this.getNombre()+" "+this.getApellidoPaterno()+" "+this.getApellidoMaterno();
    }
    
}
