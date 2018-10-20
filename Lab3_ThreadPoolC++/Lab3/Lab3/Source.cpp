#include <string>
#include <vector>
#include <iostream>
#include "Matrix.h"
#include "Source.h"
#include "ThreadPool.h"
#include "RunnableThread.h"
#include <mutex>

static int randomNumber() {
	int lowBound = 1;
	int highBound = 9;
	return int(rand() % (highBound - lowBound + 1) + lowBound);
}

static void separate() {
	std::cout << "\n";
	std::cout << "======================================================================";
	std::cout << "\n\n";
}

static std::vector<Matrix> defineMatrixAndNumber(std::string operation) {
	int lines = randomNumber();
	int columns = randomNumber();
	Matrix matrix1;
	Matrix matrix2;
	if (operation.compare("+") == 0 || operation.compare("-") == 0) {
		matrix1 = Matrix(lines, columns, true);
		matrix2 = Matrix(lines, columns, true);
	}
	else if (operation.compare("*") == 0) {
		matrix1 = Matrix(lines, columns, true);
		matrix2 = Matrix(columns, lines, true);
	}
	else { std::cout << "Error: invalid operation sign."; }
	std::vector<Matrix> list;
	list.push_back(matrix1);
	list.push_back(matrix2);
	return list;
}

static std::string readOperation() {
	std::cout << "Operation to perform: ";
	std::string operation;
	std::getline(std::cin, operation);
	std::cout << "";
	return operation;
}

int readThreadsNumber() {
	std::cout << "Number of threads: ";
	int threads;
	std::cin >> threads;
	std::cout << "";
	return threads;
}

void printMatrix(Matrix m) {
	for (int l = 0; l < m.getNumberLines(); l++) {
		for (int c = 0; c < m.getNumberColumns(); c++) {
			std::cout << m.getElement(l, c);
			std::cout << " ";
		}
		std::cout << "\n";
	}
}

Matrix defineMatrix3(Matrix matrix1, Matrix matrix2, std::string operation) {
	if (operation.compare("+") == 0 || operation.compare("-") == 0)
		return Matrix(matrix1.getNumberLines(), matrix1.getNumberColumns(), false);
	else if (operation.compare("*") == 0)
		return Matrix(matrix1.getNumberLines(), matrix2.getNumberColumns(), false);
}

int checkNumberOfThreadsThatCanBeUsed(int numberLines) {
	if ((numberLines % 2) <= (numberLines % 3))
		return 2;
	else return 3;
}


int main() {
	int MAX_THREADS_TO_RUN = 3;
	std::string operation = readOperation();
	int threads = readThreadsNumber();
	std::vector<Matrix> list = defineMatrixAndNumber(operation);
	std::cout << "\nMatrix 1: \n\n";
	Matrix matrix1 = list.back();
	list.pop_back();
	printMatrix(matrix1);
	separate();
	Matrix matrix2 = list.back();
	list.pop_back();
	std::cout << "Matrix 2: \n\n";
	printMatrix(matrix2);
	Matrix matrix3 = defineMatrix3(matrix1, matrix2, operation);
	std::vector<RunnableThread> threadsCreated;
	int number = 0;

	if (threads != 0) {
		if (threads == 1) {
			std::cout << "First branch\nCreating a single thread for the entire matrix.\n";
			std::cout << "Number of created threads: 1.\n";
			RunnableThread runnableThread = RunnableThread("1", matrix1, matrix2, matrix3, 0, 1, operation, -1);
			threadsCreated.push_back(runnableThread);
			number++;
		}
		else if (threads >= matrix1.getNumberLines() && threads < matrix1.getNumberLines() * matrix1.getNumberColumns()) {
			std::cout << "Second branch\nCreating a thread for each line of the matrix.\n";
			std::cout << "Number of created threads: " << matrix1.getNumberLines() << "\n";
			for (int l = 0; l < matrix1.getNumberLines(); l++) {
				std::string name = "Thread " + l;
				RunnableThread runnableThread = RunnableThread(name, matrix1, matrix2, matrix3, l, 1, operation, -1);
				threadsCreated.push_back(runnableThread);
				number++;
			}
		}
		else if (threads >= matrix1.getNumberLines() * matrix1.getNumberColumns()) {
			std::cout << "Third branch.\nCreating a thread for each value of the matrix.\n";
			std::cout << "Number of created threads: " << matrix1.getNumberLines() * matrix1.getNumberColumns() << "\n";
			for (int l = 0; l < matrix1.getNumberLines(); l++)
				for (int c = 0; c < matrix1.getNumberColumns(); c++) {
					std::string name = "Thread" + l + c;
					RunnableThread runnableThread = RunnableThread(name, matrix1, matrix2, matrix3, l, 1, operation, c);
					threadsCreated.push_back(runnableThread);
					number++;
				}
		}
		else {
			std::cout << "Fourth branch\nCreating a thread that iterates the from K to K lines.\n";
			threads = checkNumberOfThreadsThatCanBeUsed(threads);
			std::cout << "Number of created threads: " << threads << "\n";
			int fromLineToLine = checkNumberOfThreadsThatCanBeUsed(threads);
			for (int startFrom = 0; startFrom < threads; startFrom++) {
				std::string name = "Thread " + startFrom;
				RunnableThread runnableThread = RunnableThread(name, matrix1, matrix2, matrix3, startFrom, fromLineToLine, operation, -1);
				threadsCreated.push_back(runnableThread);
				number++;
			}
		}
	}
	else std::cout << "Cannot create 0 threads to execute this !";
	ThreadPool pool(MAX_THREADS_TO_RUN);
	std::vector<std::future<Matrix>> results;
	for (int i = 0; i < number; ++i) {
		results.emplace_back(
			pool.enqueue([matrix3, threadsCreated] {
			RunnableThread rt = threadsCreated.back();
			rt.runThread();
			Matrix result = rt.getMatrix3();
			std::this_thread::sleep_for(std::chrono::seconds(1));
			return result;
		})
		);
	}
	for (auto && result : results) {
		Matrix newResult = result.get();
		printMatrix(newResult);
	}
	std::cout << std::endl;
	system("pause");

	return 0;
}