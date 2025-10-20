package varios;

public class Matrices {

    public static void main(String[] args) {
        int filas = 2, cols = 3;
        int[][] matrixA = { {1, 9, 4}, {5, 02, 3} };
        int[][] matrixB = { {-4, -3, 3}, {2, -6, 3} };

        // Adding Two matrices
        int[][] sum = new int[filas][cols];
        for(int i = 0; i < filas; i++) {
            for (int j = 0; j < cols; j++) {
                sum[i][j] = matrixA[i][j] + matrixB[i][j];
            }
        }

        // Displaying the result
        System.out.println("Suma de las matrices es: ");
        for(int[] fila : sum) {
            for (int col : fila) {
                System.out.print(col + "    ");
            }
            System.out.println();
        }
    }
}