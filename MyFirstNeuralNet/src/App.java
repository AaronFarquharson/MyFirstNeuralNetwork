
public class App {
	
	public static void main(String[] args) {
		NeuralNet nn = new NeuralNet(2, 2, 1);
		nn.printAll();
//		Matrix test = new Matrix(new double[][] {{1.0}, {0.2}});
		Matrix output = nn.feedForward(new Matrix(new double[][] {{1.0}, {0.2}}));
		System.out.println(output);
	}
}
