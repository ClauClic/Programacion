// ============================================================================
// ENUMERACIONES
// ============================================================================

enum EstadoDispositivo {
    ACTIVO("Dispositivo funcionando normalmente"),
    INACTIVO("Dispositivo desconectado"),
    MANTENIMIENTO("Dispositivo en mantenimiento programado"),
    CRITICO("Dispositivo requiere atención inmediata");
    
    private String descripcion;
    
    EstadoDispositivo(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}

enum NivelAlerta {
    INFO, WARNING, CRITICAL, EMERGENCY
}