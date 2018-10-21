#include "RunnableThread.h"
#include <thread>

RunnableThread::RunnableThread(std::string n, Matrix m1, Matrix m2, Matrix m3, int sl, int fltl, std::string o, int c) {
	name = n;
	matrix1 = m1;
	matrix2 = m2;
	matrix3 = m3;
	startLine = sl;
	operation = o;
	col = c;
}

std::string RunnableThread::getName() {
	return name;
}

void RunnableThread::addMatrix(Matrix m1, Matrix m2, int line, int col) {
	if (col == -1)
		for (int c = 0; c < m1.getNumberColumns(); c++)
			matrix3.setElement(line, c, m1.getElement(line, c) + m2.getElement(line, c));
	else {
		matrix3.setElement(line, col, m1.getElement(line, col) + m2.getElement(line, col));
	}
}

void RunnableThread::differenceMatrix(Matrix m1, Matrix m2, int line, int col) {
	if (col == -1)
		for (int c = 0; c < m1.getNumberColumns(); c++)
			matrix3.setElement(line, c, m1.getElement(line, c) - m2.getElement(line, c));
	else matrix3.setElement(line, col, m1.getElement(line, col) - m2.getElement(line, col));
}

void RunnableThread::multiplyMatrix(Matrix m1, Matrix m2, int line, int col) {
	if (col == -1)
		for (int j = 0; j < m2.getNumberColumns(); j++)
			for (int k = 0; k < m1.getNumberColumns(); k++)
				matrix3.setElement(line, j, matrix3.getElement(line, j) + m1.getElement(line, k) * m2.getElement(k, j));
	else
		for (int j = 0; j < m2.getNumberColumns(); j++)
			matrix3.setElement(line, j, matrix3.getElement(line, j) + m1.getElement(line, col) * m2.getElement(col, j));
}

void RunnableThread::runThread()
{
	std::mutex _mutex;
	std::unique_lock<std::mutex> lock(_mutex);
	if (operation.compare("+") == 0) {
		if (col == -1)
			while (startLine < matrix1.getNumberLines()) {
				addMatrix(matrix1, matrix2, startLine, col);
				startLine += fromLineToLine;
			}
		else addMatrix(matrix1, matrix2, startLine, col);

	}
	else if (operation.compare("-") == 0) {
		if (col == -1)
			while (startLine < matrix1.getNumberLines()) {
				differenceMatrix(matrix1, matrix2, startLine, col);
				startLine += fromLineToLine;
			}
		else { differenceMatrix(matrix1, matrix2, startLine, col); }
	}
	else if (operation.compare("*") == 0) {
		if (col == -1)
			while (startLine < matrix1.getNumberLines()) {
				multiplyMatrix(matrix1, matrix2, startLine, col);
				startLine += fromLineToLine;
			}
		else { multiplyMatrix(matrix1, matrix2, startLine, col); }
	}
	std::this_thread::sleep_for(std::chrono::seconds(1));
}

Matrix RunnableThread::getMatrix3() {
	return matrix3;
}
