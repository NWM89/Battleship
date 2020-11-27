abstract class Shape {

    abstract double getPerimeter();

    abstract double getArea();
}

class Triangle extends Shape {

    protected double sideA;

    protected double sideB;

    protected double sideC;

    public Triangle(double sideA, double sideB, double sideC) {
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    public double getSideA() {
        return sideA;
    }

    public void setSideA(int sideA) {
        this.sideA = sideA;
    }

    public double getSideB() {
        return sideB;
    }

    public void setSideB(int sideB) {
        this.sideB = sideB;
    }

    public double getSideC() {
        return sideC;
    }

    public void setSideC(int sideC) {
        this.sideC = sideC;
    }

    @Override
    double getPerimeter() {
        return this.sideA + this.sideB + this.sideC;
    }

    @Override
    double getArea() {
        return Math.sqrt(getPerimeter() * (getPerimeter() - this.sideA) *
                (getPerimeter() - this.sideB) * (getPerimeter() - this.sideC));
    }
}

class Rectangle extends Shape {

    protected double sideA;

    protected double sideB;

    public Rectangle(double sideA, double sideB) {
        this.sideA = sideA;
        this.sideB = sideB;
    }

    public double getSideA() {
        return sideA;
    }

    public void setSideA(double sideA) {
        this.sideA = sideA;
    }

    public double getSideB() {
        return sideB;
    }

    public void setSideB(double sideB) {
        this.sideB = sideB;
    }

    @Override
    double getPerimeter() {
        return (this.sideA + this.sideB) * 2;
    }

    @Override
    double getArea() {
        return this.sideA * this.sideB;
    }
}

class Circle extends Shape {

    protected double r;

    public Circle(double r) {
        this.r = r;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    @Override
    double getPerimeter() {
        return 2 * Math.PI * this.r;
    }

    @Override
    double getArea() {
        return Math.PI * Math.pow(r, 2);
    }
}