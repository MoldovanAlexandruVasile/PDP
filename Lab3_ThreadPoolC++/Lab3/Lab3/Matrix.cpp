#include <iostream>
#include "Matrix.h";
	static int randomNumber() {
		int lowBound = 1;
		int highBound = 5;
		return int(rand() % (highBound - lowBound + 1) + lowBound);
	}

	int Matrix::getElement(int line, int column) {
		return matrix[line][column];
	}

	void Matrix::setElement(int line, int column, int element) {
		matrix[line][column] = element;
	}

	int Matrix::getNumberLines() {
		return lines;
	}

	int Matrix::getNumberColumns() {
		return columns;
	}

	Matrix::Matrix(){}

	Matrix::Matrix(int l, int c, bool generate) {
		lines = l;
		columns = c;
		if (generate == true)
			generateMatrix();
		else generateNullMatrix();
	}

	void Matrix::generateMatrix() {
		for (int l = 0; l < getNumberLines(); l++)
			for (int c = 0; c < getNumberColumns(); c++)
				setElement(l, c, randomNumber());
	}

	void Matrix::generateNullMatrix() {
		for (int l = 0; l < getNumberLines(); l++)
			for (int c = 0; c < getNumberColumns(); c++)
				setElement(l, c, 0);
	}