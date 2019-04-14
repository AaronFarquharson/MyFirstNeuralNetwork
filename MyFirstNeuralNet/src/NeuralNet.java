
/**
 * This is my first attempt at making a neural network library to learn the ins and outs of basic neural networks.
 * 
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
		hiddenLayerWeights = new Matrix(new double[hiddenSize][inputSize]);
		outputLayerWeights = new Matrix(new double[outputSize][hiddenSize]);
//		hiddenLayerBiases = new Matrix(new double[hiddenSize]);
//		outputLayerBiases = new Matrix(new double[outputSize]);
		learningRate = 1;
	}
	
	
	
}
