// =====================================
// This file is not used in the project.
// =====================================

package com.github.hezavehir.models;

import java.util.ArrayList;

class StaticNeuron {
    float bias = rand;
    ArrayList<Float> weight = new ArrayList<>();

    Neuron() {
    }

    Neuron(ArrayList<Float> weight) {
        updateWeights(weight);
    }

    int getNumberOfInputs() {
        return weight.size();
    }

    void addInput(float weight) {
        weight.add(weight);
    }

    void updateWeights(ArrayList<Float> weight) {
        this.weight = weight;
    }

    void updateWeight(int index, float weight) {
        this.weight.set(index, weight);
    }

    float receive(ArrayList<Float> input) {
        if (input.size() != weight.size()) {

        }
        float rtn = b;
        for (int i = 0; i < weight.size(); i++) {
            rtn += input.get(i) * weight.get(i);
        }
        return rtn;
    }

    // Activation Function
    float activation(float input) {
        if (input < 0) {
            return 0;
        } else {
            return input;
        }
    }

    // It passes an input through a neuron and returns the output
    float signal(ArrayList<Float> input) {
        return activation(receive(input));
    }
}

public class NeuralNetwork {

    public static void main(String[] args) {

    }

}
