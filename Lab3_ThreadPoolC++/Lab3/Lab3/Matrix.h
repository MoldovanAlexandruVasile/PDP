#pragma once
#ifndef Matrix_H
#define Matrix_H

class Matrix {
public:
	int lines;
	int columns;
	int matrix[10][10];
	Matrix();
	Matrix(int l, int c, bool generate);
	void generateMatrix();
	void generateNullMatrix();
	int getElement(int line, int column);
	void setElement(int line, int column, int element);
	int getNumberLines();
	int getNumberColumns();
};

#endif