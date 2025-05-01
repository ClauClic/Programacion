n=0
 # Lectura de un archivo:
with open("datos.txt") as archivo:
    for frase in archivo:
        n=n+1
        print(str(n) + ". " + str(frase))
print()
print("archivo.read")               
with open("datos.txt") as archivo:
    contenido=archivo.read()
    print(contenido)

print()
print("archivo.readline")
with open("datos.txt") as archivo:
    linea= archivo.readline()
    print(linea)
    print(archivo.readline(5))

print()
print("archivo.readlines")
with open("datos.txt") as archivo:
    lineas=archivo.readlines()
    for linea in lineas:
        print(linea)
    print(linea[15])
        