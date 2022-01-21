package org.tensorflow.lite.examples.transfer;

public class TrainingSample {
	float[] bottleneck;
	float[] label;

	TrainingSample(float[] bottleneck, float[] label) {
		this.bottleneck = bottleneck;
		this.label = label;
	}
}