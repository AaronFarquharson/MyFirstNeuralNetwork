import java.lang.Math;
import java.util.ArrayList;
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
	
	public NeuralNet(int inputSize, int hiddenSize, int outputSize, double lr) {
		hiddenLayerWeights = setRandomWeightsAndBiases(hiddenSize, inputSize);
		outputLayerWeights = setRandomWeightsAndBiases(outputSize, hiddenSize);
		hiddenLayerBiases = setRandomWeightsAndBiases(hiddenSize, 1);	//one column
		outputLayerBiases = setRandomWeightsAndBiases(outputSize, 1);	//one column
		learningRate = lr;
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
				outputs[i][j] = elementSigmoid(m.getMatrix()[i][j]);			
			}
		}
		m.setMatrix(outputs);
		return m;
	}
	
	/**
	 * A method for taking the derivative of the sigmoid function.
	 * @param m the input matrix
	 * @return the results matrix
	 */
	public Matrix dSigmoid(Matrix m) {
		Matrix s = sigmoid(m);
		Matrix oneMinusS = s.addConst(-1).multiplyConst(-1);
		return Matrix.hadamardProduct(s, oneMinusS);
	}
	
	/**
	 * A method to get the weighted and biased inputs for a layer of the neural network.
	 * This method assumes the input is n x 1.
	 * @param m
	 * @return
	 */
	public Matrix getWeightedInputs(Matrix a, Matrix weights, Matrix biases) {
		//get z = w*a + b for one layer
		
		return Matrix.matrixAdd(Matrix.matrixMultiply(weights, a), biases);
	}
	
	/**
	 * A method for determining the error at the output of the network.
	 * @param a the input matrix
	 * @param y the desired matrix
	 * @return the difference between the two
	 */
	public Matrix outputError(Matrix z, Matrix y) {
		Matrix difference = Matrix.matrixSubtract(sigmoid(z), y);
		return Matrix.hadamardProduct(difference, dSigmoid(z));
	}
	
	/**
	 * A method for determining the hidden errors of a network.
	 * Computes the hidden error based on the errors of the layers following it.
	 * @param a the input matrix
	 * @param y the desired matrix
	 * @return the difference between the two
	 */
	public Matrix hiddenError(Matrix z, Matrix weights, Matrix errorAtNextLayer) {
		Matrix temp = Matrix.matrixMultiply(weights.transpose(), errorAtNextLayer);
		Matrix temp2 = dSigmoid(z);
		return Matrix.hadamardProduct(temp, temp2);
	}
	
	/**
	 * Method for determining the costs of a matrix by comparing it to it's desired outputs
	 * @param a the input to be checked
	 * @param y the desired outputs
	 * @return the cost matrix
	 */
//	public Matrix cost(Matrix a, Matrix y) {
//		return squared(error(a, y));
//	}
	
	/**
	 * This method squares each element of a matrix
	 * @param s the matrix to be squared
	 * @return a matrix where all the elements are the squares of their inputs
	 */
	public Matrix squared(Matrix s) {
		double[][] squared = new double[s.getRows()][s.getCols()];
		for(int i = 0; i < s.getRows(); i++) {
			for(int j = 0; j < s.getCols(); j++) {
				squared[i][j] = Math.pow(s.get(i, j), 2);
			}
		}
		return new Matrix(squared);
	}
	
	
	public void train(ArrayList<Matrix> inputs, ArrayList<Matrix> expectedOutputs) {
		for(int i = 0; i < inputs.size(); i++) {
			Matrix z0 = getWeightedInputs(inputs.get(i), hiddenLayerWeights, hiddenLayerBiases);
			Matrix a0 = sigmoid(z0);
			
			Matrix z1 = getWeightedInputs(a0, outputLayerWeights, outputLayerBiases);
			Matrix a1 = sigmoid(z1);
			
			//Calculate errors in weights from input to hidden layer.
			//initial activations for layer 0 are just the inputs themselves
			Matrix outputError = outputError(z1, expectedOutputs.get(i));
			Matrix hiddenError = hiddenError(z0, outputLayerWeights, outputError);
			
			Matrix dCostBiasesOutput = outputError;
			Matrix dCostBiasesHidden = hiddenError;
			
			
			Matrix dCostWeightsOutput = Matrix.matrixMultiply(outputError, a0.transpose());
			Matrix dCostWeightsHidden = Matrix.matrixMultiply(hiddenError, inputs.get(i).transpose());
			
			outputLayerWeights = Matrix.matrixSubtract(outputLayerWeights, dCostWeightsOutput.multiplyConst(learningRate));
			hiddenLayerWeights = Matrix.matrixSubtract(hiddenLayerWeights, dCostWeightsHidden.multiplyConst(learningRate));
			
			outputLayerBiases = Matrix.matrixSubtract(outputLayerBiases, dCostBiasesOutput.multiplyConst(learningRate));
			hiddenLayerBiases = Matrix.matrixSubtract(hiddenLayerBiases, dCostBiasesHidden.multiplyConst(learningRate));
		}
	}
	
//	public void train(Matrix inputs, Matrix expectedOutputs) {
//		Matrix z0 = getWeightedInputs(inputs, hiddenLayerWeights, hiddenLayerBiases);
//		Matrix a0 = sigmoid(z0);
//		
//		Matrix z1 = getWeightedInputs(a0, outputLayerWeights, outputLayerBiases);
//		Matrix a1 = sigmoid(z1);
//		
//		//Calculate errors in weights from input to hidden layer.
//		//initial activations for layer 0 are just the inputs themselves
//		Matrix outputError = outputError(z1, expectedOutputs);
//		Matrix hiddenError = hiddenError(z0, outputLayerWeights, outputError);
//		
//		Matrix dCostBiasesOutput = outputError;
//		Matrix dCostBiasesHidden = hiddenError;
//		
//		
//		Matrix dCostWeightsOutput = Matrix.matrixMultiply(outputError, a0.transpose());
//		Matrix dCostWeightsHidden = Matrix.matrixMultiply(hiddenError, inputs.transpose());
//		
//		outputLayerWeights = Matrix.matrixSubtract(outputLayerWeights, dCostWeightsOutput.multiplyConst(learningRate));
//		hiddenLayerWeights = Matrix.matrixSubtract(hiddenLayerWeights, dCostWeightsHidden.multiplyConst(learningRate));
//		
//		outputLayerBiases = Matrix.matrixSubtract(outputLayerBiases, dCostBiasesOutput.multiplyConst(learningRate));
//		hiddenLayerBiases = Matrix.matrixSubtract(hiddenLayerBiases, dCostBiasesHidden.multiplyConst(learningRate));
//	}
	
	public Matrix evaluate(Matrix inputs) {
		Matrix z0 = getWeightedInputs(inputs, hiddenLayerWeights, hiddenLayerBiases);
		Matrix a0 = sigmoid(z0);
		
		Matrix z1 = getWeightedInputs(a0, outputLayerWeights, outputLayerBiases);
		Matrix a1 = sigmoid(z1);
		return a1;
	}
	
}
