package main;

import java.util.List;

import matrix.Matrix;

public class NeuralNetwork {
	Matrix weights_ih,weights_ho , bias_h,bias_o;	
	double l_rate=0.01;
	
	public NeuralNetwork(int i,int h,int o) {
		weights_ih = new Matrix(h,i);
		weights_ho = new Matrix(o,h);
		
		bias_h= new Matrix(h,1);
		bias_o= new Matrix(o,1);
		
	}
	
	public List<Double> predict(double[] X)
	{
		Matrix input = Matrix.fromArray(X);
		Matrix hidden = Matrix.multiply(weights_ih, input);
		hidden.add(bias_h);
		hidden.sigmoid();
		
		Matrix output = Matrix.multiply(weights_ho, hidden);
		output.add(bias_o);
		output.sigmoid();
		
		return output.toArray();
	}
	
	public void fit(double[][]X,double[][]Y,int epochs)
	{
		for(int i=0;i<epochs;i++)
		{	
			int sampleN =  (int)(Math.random() * X.length);
			this.train(X[sampleN], Y[sampleN]);						//X[sampleN] gives back a row of X
		}
	}
	
	public void train(double [] X,double [] Y)
	{
		Matrix input = Matrix.fromArray(X);							//get input layer as Matrix

		Matrix hidden = Matrix.multiply(weights_ih, input);			//multiply input with weight
		hidden.add(bias_h);											//add bias
		hidden.sigmoid();											//calculate
		//input*weight+bias -> sigmoid = hidden layer
		
		Matrix output = Matrix.multiply(weights_ho, hidden);		//same for hidden to output
		output.add(bias_o);
		output.sigmoid();
		//hidden*weight+bias -> sigmoid = output
		
		//output is the guess from the neuronal network according to the current weighting
		
		
		// now the adjustment of the weights begins
		
		Matrix target = Matrix.fromArray(Y);						//get target as Matrix
	
		Matrix error = Matrix.subtract(target, output);
		Matrix gradient = output.dsigmoid();
		gradient.multiply(error);
		gradient.multiply(l_rate);									//calculate gradient
		
		//adjusting of weighting
		Matrix hidden_T = Matrix.transpose(hidden);					//transpose hidden Layer (mirror Matrix)
		Matrix who_delta =  Matrix.multiply(gradient, hidden_T);	//gradient multiplied with hiddn_T is weight_hidden_output_delta
		
		weights_ho.add(who_delta);									//add who_delta to weights_hidden_output
		bias_o.add(gradient);										//add gradient matrix to bias_output
		
		Matrix who_T = Matrix.transpose(weights_ho);
		Matrix hidden_errors = Matrix.multiply(who_T, error);
		
		Matrix h_gradient = hidden.dsigmoid();
		h_gradient.multiply(hidden_errors);
		h_gradient.multiply(l_rate);
		
		Matrix i_T = Matrix.transpose(input);
		Matrix wih_delta = Matrix.multiply(h_gradient, i_T);
		
		weights_ih.add(wih_delta);
		bias_h.add(h_gradient);
	}
}
