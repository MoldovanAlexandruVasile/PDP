#pragma once
#include "Matrix.h"
#include <vector>
#include <thread>
#include <mutex>
#ifndef RunnableThread_H
#define RunnableThread_H

class RunnableThread {
public:
	Matrix matrix1;
	Matrix matrix2;
	Matrix matrix3;
	int fromLineToLine;
	int startLine;
	int col;
	std::string operation;
	std::string name;
	RunnableThread(std::string n, Matrix m1, Matrix m2, Matrix m3, int sl, int fltl, std::string op, int col);
	std::string getName();
	Matrix getMatrix3();
	void addMatrix(Matrix m1, Matrix m2, int line, int col);
	void differenceMatrix(Matrix m1, Matrix m2, int l, int c);
	void multiplyMatrix(Matrix m1, Matrix m2, int l, int c);
	void runRunnableThread();
};

#endif