def agregar_producto(inventario, nombre, cantidad, precio):
    if nombre in inventario:
        inventario[nombre]['cantidad'] += cantidad
    else:
        inventario[nombre] = {'cantidad': cantidad, 'precio': precio}
    print(f"Producto {nombre} agregado/actualizado correctamente.")

def mostrar_inventario(inventario):
    if not inventario:
        print("El inventario está vacío.")
    else:
        print("\nInventario actual:")
        for nombre, datos in inventario.items():
            print(f"Producto: {nombre}, Cantidad: {datos['cantidad']}, Precio: {datos['precio']}")

def calcular_valor_total(inventario):
    total = sum(datos['cantidad'] * datos['precio'] for datos in inventario.values())
    print(f"\nValor total del inventario: ${total:.2f}")

## principal
inventario = {}
while True:
    print("\n1. Agregar producto")
    print("2. Mostrar inventario")
    print("3. Calcular valor total")
    print("4. Salir")
    opcion = input("Seleccione una opción: ")
        
    if opcion == "1":
        nombre = input("Ingrese el nombre del producto: ")
        cantidad = int(input("Ingrese la cantidad: "))
        precio = float(input("Ingrese el precio: "))
        agregar_producto(inventario, nombre, cantidad, precio)
    elif opcion == "2":
        mostrar_inventario(inventario)
    elif opcion == "3":
        calcular_valor_total(inventario)
    elif opcion == "4":
        print("Saliendo del programa...")
        break
    else:
        print("Opción no válida. Intente de nuevo.")