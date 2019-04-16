import java.lang.Math;
import java.util.Random;

/**
 * This is my first attempt at making a neural network library to learn the ins and outs of basic neural networks.
 * @author Aaron
 *
 */
public class NeuralNet {

	private Matrix hiddenLayerWeights;
	private Matrix outputLayerWeights;
	private Matrix hiddenLayerBiases;
	private Matrix outputLayerBiases;
	private double learningRate;
	
	public NeuralNet(int inputSize, int hiddenSize, int outputSize) {
		hiddenLayerWeights = setRandomWeightsAndBiases(hiddenSize, inputSize);
		outputLayerWeights = setRandomWeightsAndBiases(outputSize, hiddenSize);
		hiddenLayerBiases = setRandomWeightsAndBiases(hiddenSize, 1);	//one column
		outputLayerBiases = setRandomWeightsAndBiases(outputSize, 1);	//one column
		learningRate = 1;
	}
	
	public void printAll() {
		System.out.println("\nHidden Weights");
		System.out.println(hiddenLayerWeights);
		System.out.println("\nOutput Weights");
		System.out.println(outputLayerWeights);
		System.out.println("\nHidden Biases");
		System.out.println(hiddenLayerBiases);
		System.out.println("\nOutput Biases");
		System.out.println(outputLayerBiases);
	}
	
	/**
	 * A method to randomly assign starting values to our Matrices.
	 * @param rows number of rows in the matrix
	 * @param cols number of cols in the matrix
	 * @return
	 */
	public Matrix setRandomWeightsAndBiases(int rows, int cols) {
		double[][] mat = new double[rows][cols];
		Random r = new Random();
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				mat[i][j] = r.nextDouble();
			}
		}
		return new Matrix(mat);
	}
	
	/**
	 * Computes the sigmoid for a single element.
	 * @param element the element to be sigmoided
	 * @return the element sigmoided
	 */
	public double elementSigmoid(double element) {
		return 1.0 / (1.0 + Math.exp(-element));
	}
	
	/**
	 * Computes the sigmoid of a whole matrix of elements.
	 * @param m the matrix to be fully sigmoided
	 * @return the sigmoided matrix
	 */
	public Matrix sigmoid(Matrix m) {
		double[][] outputs = new double[m.getRows()][m.getCols()];
		for(int i = 0; i < m.getRows(); i++) {
			for(int j = 0; j < m.getCols(); j++) {
				System.out.println(m.getMatrix()[i][j]);
				outputs[i][j] = elementSigmoid(m.getMatrix()[i][j]);			
			}
		}
		m.setMatrix(outputs);
		return m;
	}
	
	/**
	 * A feedforward method for the neural network.
	 * This method assumes the input is n x 1.
	 * @param m
	 * @return
	 */
	public Matrix feedForward(Matrix m) {
		//inputs to hidden layer
		m = sigmoid(Matrix.matrixAdd(Matrix.matrixMultiply(hiddenLayerWeights, m), hiddenLayerBiases));
		
		System.out.println(m);
		
		//hidden layer to output layer
		m = sigmoid(Matrix.matrixAdd(Matrix.matrixMultiply(outputLayerWeights, m), outputLayerBiases));
		
		return m;
	}
	
	public Matrix cost() {
		
	}
	
	public void backProp(Matrix inputs, Matrix expectedOutputs) {
		
	}
	
}
