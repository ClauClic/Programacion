def agregar_producto(inventario, nombre, cantidad, precio):
    for producto in inventario:
        if producto[0] == nombre:
            producto[1] += cantidad
            print(f"Producto {nombre} actualizado correctamente.")
            return
    inventario.append([nombre, cantidad, precio])
    print(f"Producto {nombre} agregado correctamente.")

def mostrar_inventario(inventario):
    if not inventario:
        print("El inventario está vacío.")
    else:
        print("\nInventario actual:")
        for producto in inventario:
            print(f"Producto: {producto[0]}, Cantidad: {producto[1]}, Precio: {producto[2]}")

def calcular_valor_total(inventario):
    total = sum(producto[1] * producto[2] for producto in inventario)
    print(f"\nValor total del inventario: ${total:.2f}")

##
inventario = []
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