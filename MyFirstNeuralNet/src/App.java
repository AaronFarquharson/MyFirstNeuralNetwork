
public class App {
	
	public static void main(String[] args) {
		NeuralNet nn = new NeuralNet(2, 2, 1, 0.1);
//		nn.printAll();
		Matrix test = new Matrix(new double[][] {{1.0}, {0.0}});
		Matrix output = new Matrix(new double[][] {{0.5}});
		System.out.println(nn.evaluate(test));
		nn.train(test, output);
		System.out.println(nn.evaluate(test));
		nn.train(test, output);
		System.out.println(nn.evaluate(test));
	}
}
