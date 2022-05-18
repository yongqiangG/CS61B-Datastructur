/**
 * @Description: 行星
 * @author: Johnny
 * @date: 2022/5/13
 */
public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double G = 6.67E-11;

    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;
    }

    public Planet(Planet planet) {
        this.xxPos = planet.xxPos;
        this.yyPos = planet.yyPos;
        this.xxVel = planet.xxVel;
        this.yyVel = planet.yyVel;
        this.mass = planet.mass;
        this.imgFileName = planet.imgFileName;
    }

    /**
     * 计算两颗行星距离
     */
    public double calcDistance(Planet p) {
        double dx = p.xxPos - this.xxPos;
        double dy = p.yyPos - this.yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * 计算受到其他行星的吸引力
     *
     * @param p
     * @return
     */
    public double calcForceExertedBy(Planet p) {
        return (G * p.mass * this.mass) / (calcDistance(p) * calcDistance(p));
    }

    /**
     * calc X force
     */
    public double calcForceExertedByX(Planet p) {
        return calcForceExertedBy(p) * (p.xxPos - this.xxPos) / calcDistance(p);
    }

    /**
     * calc Y force
     */
    public double calcForceExertedByY(Planet p) {
        return calcForceExertedBy(p) * (p.yyPos - this.yyPos) / calcDistance(p);
    }

    /**
     * calc X force for Net
     */
    public double calcNetForceExertedByX(Planet[] planets) {
        double total = 0;
        for (Planet planet : planets) {
            if (!planet.equals(this)) {
                total += calcForceExertedByX(planet);
            }
        }
        return total;
    }

    /**
     * calc X force for Net
     */
    public double calcNetForceExertedByY(Planet[] planets) {
        double total = 0;
        for (Planet planet : planets) {
            if (!planet.equals(this)) {
                total += calcForceExertedByY(planet);
            }
        }
        return total;
    }

    /**
     * calc position update when force
     */
    public void update(double sec, double fx, double fy) {
        double ax = fx / this.mass;
        double ay = fy / this.mass;
        this.xxVel += sec * ax;
        this.yyVel += sec * ay;
        this.xxPos += sec * this.xxVel;
        this.yyPos += sec * this.yyVel;
    }

    /**
     * draw a Planet
     */
    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, "./images/" + this.imgFileName);
        StdDraw.show();
    }
}

