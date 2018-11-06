import java.util.ArrayList;
import java.util.List;

public class Polynomial {
    private List<Integer> coefs = new ArrayList<>();
    private Integer degree;

    public Polynomial(List<Integer> coefs, Integer degree) throws Exception{
        if (degree < 0)
            throw new Exception("Error: The Polynomial function cannot have negative degree.");
        else if (coefs.size() != degree + 1)
            throw new Exception("Error: The number of coefficients and the degree does not corresponds.");
        else {
            this.coefs = coefs;
            this.degree = degree;
        }
    }

    public List<Integer> getCoefs() {
        return coefs;
    }

    public void setCoefs(List<Integer> coefs) {
        this.coefs = coefs;
    }

    public void addCoefAtIndex(Integer index, Integer v1, Integer v2){
        Integer value = coefs.get(index);
        value += v1 * v2;
        coefs.set(index, value);
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public void printPolynom(String string) {

        String polynom = "";
        Integer first = 0;
        for (Integer d = degree; d > 0; d--) {
            Integer coef = coefs.get(coefs.size() - d - 1);
            if (coef < 0)
                if (first == 0) {
                    polynom += coef + "*X^" + d;
                    first++;
                } else polynom += coef + "*X^" + d;
            else {
                if (first == 0) {
                    polynom += coef + "*X^" + d;
                    first++;
                } else polynom += "+" + coef + "*X^" + d;
            }
        }
        if (coefs.get(coefs.size() - 1) < 0)
            polynom += coefs.get(coefs.size() - 1);
        else polynom += "+" + coefs.get(coefs.size() - 1);
        System.out.println(string + polynom);

    }
}
