
public class Matrix {

	private int rows;
	private int cols;
	private double[][] matrix;
	
	public Matrix(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		matrix = new double[rows][cols];
	}
	
	public Matrix(double[][] mat) {
		rows = mat.length;
		cols = mat[0].length;
		matrix = mat;
	}
	
	/**
	 * A method to perform matrix multiplication (dot product).
	 * 
	 * @param mat1 the first matrix
	 * @param mat2 the second matrix
	 * @return the dot product of the two matrices
	 */
	public static Matrix matrixMultiply(Matrix mat1, Matrix mat2) {
		if(mat1.getCols() == mat2.getRows()) {
			double[][] newMat = new double[mat1.getRows()][mat2.getCols()];
			for(int i = 0; i < mat1.getRows(); i++) {
				for(int j = 0; j < mat2.getCols(); j++) {
					for(int k = 0; k < mat1.getCols(); k++) {
						newMat[i][j] += mat1.get(i, k) * mat2.get(k, j);
					}
				}
			}
			return new Matrix(newMat);
		}
		else {
			System.out.println("Cannot multiply these matrices. Their dimensions do not allow it");
			return null;
		}
	}
	
	/**
	 * A method to produce the hadamard product needed for the backprop algorithm.
	 * @param m1 matrix one
	 * @param m2 matrix two
	 * @return the hadamard product of the two
	 */
	public static Matrix hadamardProduct(Matrix m1, Matrix m2) {
		double[][] newMat = new double[m1.getRows()][m1.getCols()];
		for(int i = 0; i < m1.getRows(); i++) {
			for(int j = 0; j < m1.getCols(); j++) {
				newMat[i][j] = m1.get(i, j) * m2.get(i, j);
			}
		}
		return new Matrix(newMat);
	}
	
	/**
	 * A method for adding two matrices.
	 * 
	 * @param mat1 the first matrix
	 * @param mat2 the second matrix
	 * @return the matrices added together
	 */
	public static Matrix matrixAdd(Matrix mat1, Matrix mat2) {
		if(mat1.getRows() == mat2.getRows() && mat1.getCols() == mat2.getCols()) {
			double[][] newMat = new double[mat1.getRows()][mat1.getCols()];
			for(int i = 0; i < mat1.getRows(); i++) {
				for(int j = 0; j < mat1.getCols(); j++) {
					newMat[i][j] = mat1.get(i, j) + mat2.get(i, j);
				}
			}
			return new Matrix(newMat);
		}
		else {
			return null;
		}
	}
	
	/**
	 * A method for subtracting two matrices.
	 * 
	 * @param mat1 the first matrix
	 * @param mat2 the second matrix
	 * @return the second matrix subtracted from the first
	 */
	public static Matrix matrixSubtract(Matrix mat1, Matrix mat2) {
		if(mat1.getRows() == mat2.getRows() && mat1.getCols() == mat2.getCols()) {
			double[][] newMat = new double[mat1.getRows()][mat1.getCols()];
			for(int i = 0; i < mat1.getRows(); i++) {
				for(int j = 0; j < mat1.getCols(); j++) {
					newMat[i][j] = mat1.get(i, j) - mat2.get(i, j);
				}
			}
			return new Matrix(newMat);
		}
		else {
			System.out.println("Cannot subtract these matrices. Their dimensions do not allow it");
			return null;
		}
	}
	
	public Matrix multiply(Matrix m) {
		if(getCols() == m.getRows()) {
			double[][] newMat = new double[getRows()][m.getCols()];
			for(int i = 0; i < getRows(); i++) {
				for(int j = 0; j < m.getCols(); j++) {
					for(int k = 0; k < getCols(); k++) {
						newMat[i][j] += get(i, k) * m.get(k, j);
					}
				}
			}
			return new Matrix(newMat);
		}
		else {
			System.out.println("Cannot multiply these matrices. Their dimensions do not allow it");
			return null;
		}
	}
	
	public Matrix multiplyConst(double constant) {
		double[][] newMat = new double[getRows()][getCols()];
		for(int i = 0; i < getRows(); i++) {
			for(int j = 0; j < getCols(); j++) {
				newMat[i][j] = get(i, j) * constant;
			}
		}
		return new Matrix(newMat);
	}
	
	public Matrix addConst(double constant) {
		double[][] newMat = new double[getRows()][getCols()];
		for(int i = 0; i < getRows(); i++) {
			for(int j = 0; j < getCols(); j++) {
				newMat[i][j] = get(i, j) + constant;
			}
		}
		return new Matrix(newMat);
	}
	
	public Matrix transpose() {
		double[][] newMat = new double[cols][rows];
		for(int i = 0; i < getRows(); i++) {
			for(int j = 0; j < getCols(); j++) {
				newMat[j][i] = matrix[i][j];
			}
		}
		return new Matrix(newMat);
	}
	
	public double get(int i, int j) {
		return matrix[i][j];
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public double[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(double[][] matrix) {
		this.matrix = matrix;
	}
	
	public void display() {
		System.out.println(this);
	}
	
	public String toString() {
		String s = "";
		for(int i = 0; i < getRows(); i++) {
			for(int j = 0; j < getCols(); j++) {
				s += get(i, j) + "\t";
			}
			s += "\n";
		}
		return s;
    }
	
	public static void main(String[] args) {
//		Matrix m1 = new Matrix(new double[][] {{2, 3, 4}, {1, 5, 6}});
//		Matrix m2 = new Matrix(new double[][] {{2, 3, 4}, {1, 5, 6}, {6, 7, 10}});
//		Matrix m3 = Matrix.matrixMultiply(m1, m2);
//		System.out.println(m3);
//		m3.multiply(2);
//		System.out.println(m3);
//		Matrix m4 = m3.transpose();
//		System.out.println(m4);
	}
	
}
