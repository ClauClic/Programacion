// ============================================================================
// CLASES DE SOPORTE
// ============================================================================

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



class Alerta {
    private static int contadorId = 1;
    private String id;
    private LocalDateTime timestamp;
    private NivelAlerta nivel;
    private String mensaje;
    private String dispositivoOrigen;
    
    public Alerta(NivelAlerta nivel, String mensaje, String dispositivoOrigen) {
        this.id = "ALERT-" + String.format("%04d", contadorId++);
        this.timestamp = LocalDateTime.now();
        this.nivel = nivel;
        this.mensaje = mensaje;
        this.dispositivoOrigen = dispositivoOrigen;
    }
    
    public void notificar() {
        String icono = switch(nivel) {
            case INFO -> "ℹ️";
            case WARNING -> "⚠️";
            case CRITICAL -> "🚨";
            case EMERGENCY -> "🆘";
        };
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println(String.format("%s [%s] %s - %s: %s", 
            icono, nivel, timestamp.format(formatter), dispositivoOrigen, mensaje));
    }
    
    public NivelAlerta getNivel() { return nivel; }
    public String getMensaje() { return mensaje; }
    public String getDispositivoOrigen() { return dispositivoOrigen; }
}

// ============================================================================
// CLASE ABSTRACTA BASE
// ============================================================================

abstract class DispositivoMonitoreable {
    protected String id;
    protected String nombre;
    protected EstadoDispositivo estado;
    protected String ubicacion;
    
    public DispositivoMonitoreable(String id, String nombre, String ubicacion) {
        this.id = id;
        this.nombre = nombre;
        this.estado = EstadoDispositivo.ACTIVO;
        this.ubicacion = ubicacion;
    }
    
    // Métodos abstractos que deben implementar las subclases
    public abstract double obtenerMedicion();
    public abstract String diagnosticar();
    public abstract void calibrar();
    
    // Métodos concretos compartidos
    public void cambiarEstado(EstadoDispositivo nuevoEstado) {
        this.estado = nuevoEstado;
        System.out.println("📊 " + nombre + " cambió a estado: " + nuevoEstado);
    }
    
    public String obtenerInfo() {
        return String.format("ID: %s | Nombre: %s | Estado: %s | Ubicación: %s", 
            id, nombre, estado, ubicacion);
    }
    
    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public EstadoDispositivo getEstado() { return estado; }
    public String getUbicacion() { return ubicacion; }
}

// ============================================================================
// SUBCLASES ESPECIALIZADAS (4 DOMINIOS DE INGENIERÍA)
// ============================================================================

// 1. INGENIERÍA BIOMÉDICA
class SensorBiomedico extends DispositivoMonitoreable {
    private String tipoSensor; // "ECG", "SpO2", "Temperatura", "Presión"
    private double umbralMin;
    private double umbralMax;
    private List<Lectura> lecturas;
    
    public SensorBiomedico(String id, String nombre, String ubicacion, 
                           String tipoSensor, double umbralMin, double umbralMax) {
        super(id, nombre, ubicacion);
        this.tipoSensor = tipoSensor;
        this.umbralMin = umbralMin;
        this.umbralMax = umbralMax;
        this.lecturas = new ArrayList<>();
    }
    
    @Override
    public double obtenerMedicion() {
        // Simulación de lectura del sensor
        double medicion = switch(tipoSensor) {
            case "ECG" -> 60 + Math.random() * 40; // 60-100 BPM
            case "SpO2" -> 95 + Math.random() * 5; // 95-100%
            case "Temperatura" -> 36 + Math.random() * 1.5; // 36-37.5°C
            case "Presión" -> 110 + Math.random() * 30; // 110-140 mmHg
            default -> 0;
        };
        
        String unidad = switch(tipoSensor) {
            case "ECG" -> "BPM";
            case "SpO2" -> "%";
            case "Temperatura" -> "°C";
            case "Presión" -> "mmHg";
            default -> "";
        };
        
        lecturas.add(new Lectura(medicion, unidad));
        return medicion;
    }
    
    public boolean verificarAlarma() {
        if (lecturas.isEmpty()) return false;
        
        double ultimaLectura = lecturas.get(lecturas.size() - 1).getValor();
        return ultimaLectura < umbralMin || ultimaLectura > umbralMax;
    }
    
    public double medirSignoVital() {
        double medicion = obtenerMedicion();
        System.out.println("💓 " + nombre + " (" + tipoSensor + "): " + 
                          String.format("%.2f", medicion));
        return medicion;
    }
    
    @Override
    public String diagnosticar() {
        if (lecturas.size() < 3) {
            return "Datos insuficientes para diagnóstico";
        }
        
        double promedio = lecturas.stream()
            .mapToDouble(Lectura::getValor)
            .average()
            .orElse(0);
        
        if (promedio < umbralMin) {
            return "CRÍTICO: Valores por debajo del umbral mínimo";
        } else if (promedio > umbralMax) {
            return "CRÍTICO: Valores por encima del umbral máximo";
        } else {
            return "NORMAL: Valores dentro del rango esperado";
        }
    }
    
    @Override
    public void calibrar() {
        System.out.println("🔧 Calibrando sensor " + tipoSensor + "...");
        lecturas.clear();
        System.out.println("✅ Calibración completada");
    }
    
    public String generarReporte() {
        if (lecturas.isEmpty()) return "Sin datos disponibles";
        
        double promedio = lecturas.stream()
            .mapToDouble(Lectura::getValor)
            .average()
            .orElse(0);
        
        double max = lecturas.stream()
            .mapToDouble(Lectura::getValor)
            .max()
            .orElse(0);
        
        double min = lecturas.stream()
            .mapToDouble(Lectura::getValor)
            .min()
            .orElse(0);
        
        return String.format("📊 Reporte %s:\n   Promedio: %.2f\n   Máximo: %.2f\n   Mínimo: %.2f\n   Lecturas: %d",
            tipoSensor, promedio, max, min, lecturas.size());
    }
}

// 2. INGENIERÍA EN ENERGÍA
class PanelEnergetico extends DispositivoMonitoreable {
    private double capacidadKw;
    private double eficiencia;
    private double generacionActual;
    private double temperatura;
    
    public PanelEnergetico(String id, String nombre, String ubicacion, 
                          double capacidadKw, double eficiencia) {
        super(id, nombre, ubicacion);
        this.capacidadKw = capacidadKw;
        this.eficiencia = eficiencia;
        this.generacionActual = 0;
        this.temperatura = 25;
    }
    
    @Override
    public double obtenerMedicion() {
        return generacionActual;
    }
    
    public double calcularGeneracion(double irradiancia, double temperaturaAmbiente) {
        // Irradiancia en W/m², temperatura en °C
        temperatura = temperaturaAmbiente + (irradiancia * 0.02);
        
        // Corrección por temperatura (coeficiente típico: -0.4% por °C)
        double deltaTemp = temperatura - 25;
        double factorTemp = 1 + (-0.004 * deltaTemp);
        
        // Cálculo de generación
        generacionActual = capacidadKw * (irradiancia / 1000) * eficiencia * factorTemp;
        
        return generacionActual;
    }
    
    public double obtenerEficiencia() {
        if (capacidadKw == 0) return 0;
        return (generacionActual / capacidadKw) * 100;
    }
    
    public void optimizarRendimiento() {
        System.out.println("⚡ Optimizando rendimiento del panel...");
        // Simulación de optimización
        eficiencia = Math.min(eficiencia * 1.05, 0.25); // Máximo 25%
        System.out.println("✅ Nueva eficiencia: " + String.format("%.2f%%", eficiencia * 100));
    }
    
    @Override
    public String diagnosticar() {
        double eficienciaActual = obtenerEficiencia();
        double eficienciaEsperada = eficiencia * 100;
        
        if (eficienciaActual < eficienciaEsperada * 0.7) {
            return "DEGRADADO: Limpieza o mantenimiento requerido";
        } else if (eficienciaActual < eficienciaEsperada * 0.85) {
            return "ACEPTABLE: Monitoreo continuo recomendado";
        } else {
            return "ÓPTIMO: Funcionamiento normal";
        }
    }
    
    @Override
    public void calibrar() {
        System.out.println("🔧 Calibrando panel energético...");
        generacionActual = 0;
        temperatura = 25;
        System.out.println("✅ Panel restablecido a condiciones estándar");
    }
}

// 3. INGENIERÍA MECATRÓNICA
class ActuadorMecatronico extends DispositivoMonitoreable {
    private double posicionActual;
    private double velocidadMax;
    private double torque;
    private double[] rangoMovimiento; // [min, max]
    
    public ActuadorMecatronico(String id, String nombre, String ubicacion,
                              double velocidadMax, double posicionMin, double posicionMax) {
        super(id, nombre, ubicacion);
        this.posicionActual = 0;
        this.velocidadMax = velocidadMax;
        this.torque = 0;
        this.rangoMovimiento = new double[]{posicionMin, posicionMax};
    }
    
    @Override
    public double obtenerMedicion() {
        return posicionActual;
    }
    
    public boolean moverAPosicion(double posicionObjetivo, double velocidad) {
        // Verificar rango
        if (posicionObjetivo < rangoMovimiento[0] || posicionObjetivo > rangoMovimiento[1]) {
            System.out.println("❌ Posición fuera de rango: " + posicionObjetivo);
            return false;
        }
        
        // Verificar velocidad
        if (velocidad > velocidadMax) {
            System.out.println("⚠️  Velocidad limitada a máxima permitida");
            velocidad = velocidadMax;
        }
        
        double distancia = Math.abs(posicionObjetivo - posicionActual);
        torque = distancia * 0.5; // Simulación de torque requerido
        
        System.out.println("🔄 Moviendo actuador " + nombre);
        System.out.println("   Desde: " + String.format("%.2f", posicionActual) + 
                         " → Hasta: " + String.format("%.2f", posicionObjetivo));
        
        posicionActual = posicionObjetivo;
        return true;
    }
    
    public void detenerEmergencia() {
        System.out.println("🛑 PARADA DE EMERGENCIA en " + nombre);
        torque = 0;
        cambiarEstado(EstadoDispositivo.CRITICO);
    }
    
    public boolean detectarColision(double umbralTorque) {
        if (torque > umbralTorque) {
            System.out.println("⚠️  Colisión detectada en " + nombre);
            detenerEmergencia();
            return true;
        }
        return false;
    }
    
    @Override
    public String diagnosticar() {
        if (estado == EstadoDispositivo.CRITICO) {
            return "CRÍTICO: Requiere inspección inmediata";
        }
        
        double porcentajeRango = Math.abs(posicionActual) / rangoMovimiento[1] * 100;
        
        if (torque > 10) {
            return "ADVERTENCIA: Torque elevado - posible obstrucción";
        } else if (porcentajeRango > 90) {
            return "ADVERTENCIA: Cerca del límite de movimiento";
        } else {
            return "NORMAL: Operación dentro de parámetros";
        }
    }
    
    @Override
    public void calibrar() {
        System.out.println("🔧 Calibrando actuador...");
        posicionActual = 0;
        torque = 0;
        cambiarEstado(EstadoDispositivo.ACTIVO);
        System.out.println("✅ Actuador en posición home");
    }
    
    public void calibrarPosicion() {
        calibrar();
    }
    
    public double getPosicionActual() { return posicionActual; }
    public double getTorque() { return torque; }
}

// 4. INGENIERÍA DE SISTEMAS
class ServidorComputo extends DispositivoMonitoreable {
    private int cpuCores;
    private double ramGB;
    private double utilizacionCPU;
    private int procesosActivos;
    private double almacenamientoGB;
    private double almacenamientoUsado;
    
    public ServidorComputo(String id, String nombre, String ubicacion,
                          int cpuCores, double ramGB, double almacenamientoGB) {
        super(id, nombre, ubicacion);
        this.cpuCores = cpuCores;
        this.ramGB = ramGB;
        this.almacenamientoGB = almacenamientoGB;
        this.utilizacionCPU = 0;
        this.procesosActivos = 0;
        this.almacenamientoUsado = 0;
    }
    
    @Override
    public double obtenerMedicion() {
        return utilizacionCPU;
    }
    
    public void procesarDatos(int numProcesos, double cargaCPU) {
        this.procesosActivos += numProcesos;
        this.utilizacionCPU = Math.min(utilizacionCPU + cargaCPU, 100);
        
        System.out.println("💻 Procesando en " + nombre);
        System.out.println("   CPU: " + String.format("%.1f%%", utilizacionCPU));
        System.out.println("   Procesos: " + procesosActivos);
    }
    
    public Map<String, Double> monitorearRecursos() {
        Map<String, Double> metricas = new HashMap<>();
        metricas.put("cpu_uso", utilizacionCPU);
        metricas.put("ram_disponible", ramGB - (ramGB * utilizacionCPU / 100));
        metricas.put("storage_uso", (almacenamientoUsado / almacenamientoGB) * 100);
        metricas.put("procesos_activos", (double) procesosActivos);
        
        return metricas;
    }
    
    public void escalarRecursos(int nuevosCores, double nuevaRAM) {
        System.out.println("⬆️  Escalando recursos de " + nombre);
        System.out.println("   CPU: " + cpuCores + " → " + nuevosCores + " cores");
        System.out.println("   RAM: " + ramGB + " → " + nuevaRAM + " GB");
        
        this.cpuCores = nuevosCores;
        this.ramGB = nuevaRAM;
        
        // Recalcular utilización con nuevos recursos
        this.utilizacionCPU = this.utilizacionCPU * (cpuCores / (double) nuevosCores);
    }
    
    @Override
    public String diagnosticar() {
        Map<String, Double> metricas = monitorearRecursos();
        double cpuUso = metricas.get("cpu_uso");
        double storageUso = metricas.get("storage_uso");
        
        if (cpuUso > 90 || storageUso > 90) {
            return "CRÍTICO: Recursos al límite - Escalar inmediatamente";
        } else if (cpuUso > 75 || storageUso > 80) {
            return "ADVERTENCIA: Alta utilización - Considerar escalamiento";
        } else {
            return "NORMAL: Recursos dentro de límites aceptables";
        }
    }
    
    @Override
    public void calibrar() {
        System.out.println("🔧 Reiniciando servidor...");
        utilizacionCPU = 0;
        procesosActivos = 0;
        System.out.println("✅ Servidor reiniciado y optimizado");
    }
    
    public int getCpuCores() { return cpuCores; }
    public double getRamGB() { return ramGB; }
}

// ============================================================================
// CLASE CENTRAL DE MONITOREO
// ============================================================================

import java.util.*;
import java.util.stream.Collectors;

class CentralMonitoreo {
    private List<DispositivoMonitoreable> dispositivos;
    private List<Alerta> alertas;
    private Map<String, Double> estadisticas;
    
    public CentralMonitoreo() {
        this.dispositivos = new ArrayList<>();
        this.alertas = new ArrayList<>();
        this.estadisticas = new HashMap<>();
    }
    
    public void registrarDispositivo(DispositivoMonitoreable dispositivo) {
        dispositivos.add(dispositivo);
        System.out.println("✅ Dispositivo registrado: " + dispositivo.getNombre());
    }
    
    public void monitorearTodos() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🔍 MONITOREANDO TODOS LOS DISPOSITIVOS");
        System.out.println("=".repeat(80) + "\n");
        
        for (DispositivoMonitoreable dispositivo : dispositivos) {
            if (dispositivo.getEstado() == EstadoDispositivo.ACTIVO) {
                double medicion = dispositivo.obtenerMedicion();
                String diagnostico = dispositivo.diagnosticar();
                
                System.out.println("📊 " + dispositivo.getNombre());
                System.out.println("   Medición: " + String.format("%.2f", medicion));
                System.out.println("   Diagnóstico: " + diagnostico);
                
                // Generar alertas según diagnóstico
                if (diagnostico.contains("CRÍTICO")) {
                    generarAlerta(NivelAlerta.CRITICAL, diagnostico, dispositivo.getId());
                } else if (diagnostico.contains("ADVERTENCIA")) {
                    generarAlerta(NivelAlerta.WARNING, diagnostico, dispositivo.getId());
                }
                
                System.out.println();
            }
        }
    }
    
    private void generarAlerta(NivelAlerta nivel, String mensaje, String dispositivoId) {
        Alerta alerta = new Alerta(nivel, mensaje, dispositivoId);
        alertas.add(alerta);
    }
    
    public void procesarAlertas() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🚨 PROCESANDO ALERTAS");
        System.out.println("=".repeat(80) + "\n");
        
        if (alertas.isEmpty()) {
            System.out.println("✅ No hay alertas pendientes\n");
            return;
        }
        
        // Ordenar por nivel de criticidad
        List<Alerta> alertasOrdenadas = alertas.stream()
            .sorted((a1, a2) -> a2.getNivel().compareTo(a1.getNivel()))
            .collect(Collectors.toList());
        
        for (Alerta alerta : alertasOrdenadas) {
            alerta.notificar();
        }
        
        System.out.println("\nTotal de alertas: " + alertas.size());
    }
    
    public String generarInforme() {
        StringBuilder informe = new StringBuilder();
        informe.append("\n").append("=".repeat(80)).append("\n");
        informe.append("📋 INFORME GENERAL DEL SISTEMA\n");
        informe.append("=".repeat(80)).append("\n\n");
        
        // Estadísticas por tipo de dispositivo
        Map<String, Long> dispositivosPorTipo = dispositivos.stream()
            .collect(Collectors.groupingBy(d -> d.getClass().getSimpleName(), Collectors.counting()));
        
        informe.append("📊 Dispositivos Registrados:\n");
        dispositivosPorTipo.forEach((tipo, cantidad) -> 
            informe.append(String.format("   • %s: %d\n", tipo, cantidad))
        );
        
        // Dispositivos por estado
        informe.append("\n🔋 Estado de Dispositivos:\n");
        Map<EstadoDispositivo, Long> dispositivosPorEstado = dispositivos.stream()
            .collect(Collectors.groupingBy(DispositivoMonitoreable::getEstado, Collectors.counting()));
        
        dispositivosPorEstado.forEach((estado, cantidad) ->
            informe.append(String.format("   • %s: %d\n", estado, cantidad))
        );
        
        // Alertas por nivel
        informe.append("\n⚠️  Alertas Generadas:\n");
        if (alertas.isEmpty()) {
            informe.append("   • Ninguna\n");
        } else {
            Map<NivelAlerta, Long> alertasPorNivel = alertas.stream()
                .collect(Collectors.groupingBy(Alerta::getNivel, Collectors.counting()));
            
            alertasPorNivel.forEach((nivel, cantidad) ->
                informe.append(String.format("   • %s: %d\n", nivel, cantidad))
            );
        }
        
        informe.append("\n").append("=".repeat(80)).append("\n");
        
        return informe.toString();
    }
    
    public Map<String, Double> obtenerEstadisticas() {
        estadisticas.clear();
        
        // Calcular promedios de mediciones
        double promedioMediciones = dispositivos.stream()
            .filter(d -> d.getEstado() == EstadoDispositivo.ACTIVO)
            .mapToDouble(DispositivoMonitoreable::obtenerMedicion)
            .average()
            .orElse(0);
        
        estadisticas.put("promedio_mediciones", promedioMediciones);
        estadisticas.put("total_dispositivos", (double) dispositivos.size());
        estadisticas.put("dispositivos_activos", (double) dispositivos.stream()
            .filter(d -> d.getEstado() == EstadoDispositivo.ACTIVO)
            .count());
        estadisticas.put("total_alertas", (double) alertas.size());
        
        return estadisticas;
    }
    
    public List<DispositivoMonitoreable> getDispositivos() {
        return dispositivos;
    }
    
    public List<Alerta> getAlertas() {
        return alertas;
    }
}

// ============================================================================
// CLASE PRINCIPAL CON EJEMPLOS DE USO
// ============================================================================

public class SistemaMonitoreoIndustrial {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("🏭 SISTEMA DE MONITOREO INDUSTRIAL UNIFICADO");
        System.out.println("=".repeat(80));
        
        // Crear la central de monitoreo
        CentralMonitoreo central = new CentralMonitoreo();
        
        // ====================================================================
        // 1. REGISTRAR DISPOSITIVOS BIOMÉDICOS
        // ====================================================================
        System.out.println("\n🏥 MÓDULO BIOMÉDICO");
        System.out.println("-".repeat(80));
        
        SensorBiomedico sensorECG = new SensorBiomedico(
            "BIO-ECG-001", "Monitor Cardíaco UCI-A", "Hospital - UCI Piso 3",
            "ECG", 60, 100
        );
        
        SensorBiomedico sensorSpO2 = new SensorBiomedico(
            "BIO-SPO2-001", "Oxímetro UCI-A", "Hospital - UCI Piso 3",
            "SpO2", 95, 100
        );
        
        central.registrarDispositivo(sensorECG);
        central.registrarDispositivo(sensorSpO2);
        
        // Simular lecturas
        System.out.println("\n📊 Tomando lecturas de signos vitales:");
        for (int i = 0; i < 5; i++) {
            sensorECG.medirSignoVital();
            sensorSpO2.medirSignoVital();
        }
        
        // Generar reportes
        System.out.println("\n" + sensorECG.generarReporte());
        System.out.println(sensorSpO2.generarReporte());
        
        // ====================================================================
        // 2. REGISTRAR DISPOSITIVOS ENERGÉTICOS
        // ====================================================================
        System.out.println("\n\n⚡ MÓDULO ENERGÉTICO");
        System.out.println("-".repeat(80));
        
        PanelEnergetico panel1 = new PanelEnergetico(
            "ENE-SOLAR-001", "Panel Solar Edificio A", "Techo Principal",
            5.0, 0.20
        );
        
        PanelEnergetico panel2 = new PanelEnergetico(
            "ENE-SOLAR-002", "Panel Solar Edificio B", "Techo Secundario",
            3.0, 0.18
        );
        
        central.registrarDispositivo(panel1);
        central.registrarDispositivo(panel2);
        
        // Simular generación
        System.out.println("\n☀️  Simulando generación solar:");
        System.out.println("\nMañana (8:00 AM):");
        double gen1 = panel1.calcularGeneracion(200, 20);
        System.out.println("   Panel 1: " + String.format("%.2f kW", gen1));
        
        System.out.println("\nMediodía (12:00 PM):");
        gen1 = panel1.calcularGeneracion(1000, 30);
        double gen2 = panel2.calcularGeneracion(1000, 30);
        System.out.println("   Panel 1: " + String.format("%.2f kW", gen1));
        System.out.println("   Panel 2: " + String.format("%.2f kW", gen2));
        
        panel1.optimizarRendimiento();
        
        // ====================================================================
        // 3. REGISTRAR DISPOSITIVOS MECATRÓNICOS
        // ====================================================================
        System.out.println("\n\n🤖 MÓDULO MECATRÓNICO");
        System.out.println("-".repeat(80));
        
        ActuadorMecatronico actuador1 = new ActuadorMecatronico(
            "MEC-ACT-001", "Actuador Brazo Robot 1", "Línea Producción A",
            100, -180, 180
        );
        
        ActuadorMecatronico actuador2 = new ActuadorMecatronico(
            "MEC-ACT-002", "Actuador Brazo Robot 2", "Línea Producción A",
            80, -90, 90
        );
        
        central.registrarDispositivo(actuador1);
        central.registrarDispositivo(actuador2);
        
        // Simular movimientos
        System.out.println("\n🦾 Ejecutando secuencia de movimiento:");
        actuador1.moverAPosicion(45, 50);
        actuador1.moverAPosicion(90, 50);
        actuador2.moverAPosicion(-45, 40);
        
        // Simular colisión
        System.out.println("\n⚠️  Simulando detección de colisión:");
        actuador1.detectarColision(8);
        
        // ====================================================================
        // 4. REGISTRAR SERVIDORES DE CÓMPUTO
        // ====================================================================
        System.out.println("\n\n💻 MÓDULO SISTEMAS");
        System.out.println("-".repeat(80));
        
        ServidorComputo servidor1 = new ServidorComputo(
            "SYS-SRV-001", "Servidor Web Principal", "DataCenter Rack-A1",
            8, 32, 500
        );
        
        ServidorComputo servidor2 = new ServidorComputo(
            "SYS-SRV-002", "Servidor Base de Datos", "DataCenter Rack-A2",
            16, 64, 1000
        );
        
        central.registrarDispositivo(servidor1);
        central.registrarDispositivo(servidor2);
        
        // Simular carga de trabajo
        System.out.println("\n📊 Simulando carga de trabajo:");
        servidor1.procesarDatos(50, 45);
        servidor2.procesarDatos(30, 75);
        
        // Monitorear recursos
        System.out.println("\n📈 Métricas del Servidor 2:");
        Map<String, Double> metricas = servidor2.monitorearRecursos();
        metricas.forEach((metrica, valor) ->
            System.out.println("   " + metrica + ": " + String.format("%.2f", valor))
        );
        
        // Escalar si es necesario
        if (metricas.get("cpu_uso") > 70) {
            servidor2.escalarRecursos(24, 96);
        }
        
        // ====================================================================
        // MONITOREO GLOBAL Y GENERACIÓN DE REPORTES
        // ====================================================================
        
        // Monitorear todos los dispositivos
        central.monitorearTodos();
        
        // Procesar alertas
        central.procesarAlertas();
        
        // Generar informe completo
        String informe = central.generarInforme();
        System.out.println(informe);
        
        // Estadísticas generales
        System.out.println("📊 ESTADÍSTICAS GENERALES");
        System.out.println("=".repeat(80));
        Map<String, Double> stats = central.obtenerEstadisticas();
        stats.forEach((stat, valor) ->
            System.out.println("   " + stat + ": " + String.format("%.2f", valor))
        );
        
        // ====================================================================
        // OPERACIONES ADICIONALES
        // ====================================================================
        System.out.println("\n\n🔧 OPERACIONES DE MANTENIMIENTO");
        System.out.println("=".repeat(80) + "\n");
        
        // Calibrar dispositivos
        System.out.println("Calibrando sensor ECG:");
        sensorECG.calibrar();
        
        System.out.println("\nCalibrando actuador:");
        actuador1.calibrarPosicion();
        
        System.out.println("\nReiniciando servidor:");
        servidor1.calibrar();
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("✅ SISTEMA DE MONITOREO COMPLETADO");
        System.out.println("=".repeat(80));
    }
}
