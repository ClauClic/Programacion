# Definir diccionario de tarifas dinámicamente
tarifas = {km: 7500 + 1200 * km for km in range(16)}

# Mostrar el diccionario de tarifas
print("\nDiccionario de tarifas:")
for km, costo in tarifas.items():
    print(f"Tarifa {km} km: ${costo}")

# Bucle para solicitar distancia y calcular el costo
while True:
    try:
        distancia = int(input("\nIngrese la distancia en kilómetros (0-15, 16 para salir): "))

        if distancia == 16:
            print("Gracias por utilizar el Simulador de Taxímetro. ¡Hasta luego!")
            break

        if 0 <= distancia <= 15:
            print(f"Costo total del viaje: ${tarifas[distancia]}")
        else:
            print("Por favor, ingrese una distancia válida entre 0 y 15 km.")

    except ValueError:
        print("Error: Ingrese un número válido.")
