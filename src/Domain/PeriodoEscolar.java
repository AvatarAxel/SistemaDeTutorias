package Domain;

import java.sql.Date;

/**
 *
 * @author michikato
 */
public class PeriodoEscolar {

    private String clave;
    private Date fechaInicio;
    private Date fechaFin;
    private int idPeriodoEscolar;

    public PeriodoEscolar() {
    }

    public PeriodoEscolar(String clave, Date fechaInicio, Date fechaFin, int idPeriodoEscolar) {
        this.clave = clave;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idPeriodoEscolar = idPeriodoEscolar;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getIdPeriodoEscolar() {
        return idPeriodoEscolar;
    }

    public void setIdPeriodoEscolar(int idPeriodoEscolar) {
        this.idPeriodoEscolar = idPeriodoEscolar;
    }

    public String getFechasPeridoEscolar() {
        String fechasTutoriaAcademica = util.DateLatinAmerica.DateWithDays(fechaInicio)
                + " - " + util.DateLatinAmerica.DateWithDays(fechaFin);
        return fechasTutoriaAcademica;
    }
    
    public String getMonthsPeridoEscolar() {
        String fechasTutoriaAcademica = util.DateLatinAmerica.DateWithMonths(fechaInicio)
                + " - " + util.DateLatinAmerica.DateWithMonths(fechaFin);
        return fechasTutoriaAcademica;
    }

    @Override
    public String toString() {
        return this.getFechasPeridoEscolar();
    }
    
}
