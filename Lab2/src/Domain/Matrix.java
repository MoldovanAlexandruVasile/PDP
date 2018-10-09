package Domain;

import static Domain.AppUtils.randomNumber;

public class Matrix {

    private Integer lines = 0;
    private Integer columns = 0;
    private Integer[][] matrix;

    public Matrix(Integer lines, Integer columns, Boolean generate) {
        this.lines = lines;
        this.columns = columns;
        this.matrix = new Integer[lines][columns];
        if (generate)
            this.generateMatrix();
        else this.generateNullMatrix();
    }

    public Integer getNumberLines() {
        return lines;
    }

    public void setNumberLines(Integer lines) {
        this.lines = lines;
    }

    public Integer getNumberColumns() {
        return columns;
    }

    public void setNumberColumns(Integer columns) {
        this.columns = columns;
    }

    private void generateMatrix() {
        for (Integer l = 0; l < this.lines; l++)
            for (Integer c = 0; c < this.columns; c++)
                this.matrix[l][c] = randomNumber();
    }

    private void generateNullMatrix() {
        for (Integer l = 0; l < this.lines; l++)
            for (Integer c = 0; c < this.columns; c++)
                this.matrix[l][c] = 0;
    }


    public void printMatrix() {
        for (Integer l = 0; l < this.lines; l++) {
            for (Integer c = 0; c < this.columns; c++)
                System.out.print(this.matrix[l][c] + " ");
            System.out.println();
        }
    }

    public Integer getElement(Integer line, Integer column){
        return matrix[line][column];
    }

    public void setElement(Integer line, Integer column, Integer element){
        matrix[line][column] = element;
    }
}
