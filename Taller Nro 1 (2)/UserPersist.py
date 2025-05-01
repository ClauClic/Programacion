import csv

# Nombre del archivo donde se guardarán los datos
archivo_datos = "usuarios.csv"

# Función para guardar un usuario en el archivo CSV
def guardar_usuario(nombre, edad):
    with open(archivo_datos, mode='a', newline='') as archivo:
        escritor = csv.writer(archivo)
        escritor.writerow([nombre, edad])
    print(f"Usuario {nombre} guardado correctamente.")

# Función para leer los usuarios almacenados
def leer_usuarios():
    try:
        with open(archivo_datos, mode='r') as archivo:
            lector = csv.reader(archivo)
            print("Lista de usuarios:")
            for fila in lector:
                print(f"Nombre: {fila[0]}, Edad: {fila[1]}")
    except FileNotFoundError:
        print("No hay usuarios registrados aún.")

#  principal

while True:
    print("\n1. Agregar usuario")
    print("2. Mostrar usuarios")
    print("3. Salir")
    opcion = input("Seleccione una opción: ")
        
    if opcion == "1":
        nombre = input("Ingrese el nombre: ")
        edad = input("Ingrese la edad: ")
        guardar_usuario(nombre, edad)
    elif opcion == "2":
        leer_usuarios()
    elif opcion == "3":
        print("Saliendo del programa...")
        break
    else:
        print("Opción no válida. Intente de nuevo.")
