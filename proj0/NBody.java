/**
 * Description: simulator
 * @author: Johnny
 * @date: 2022/5/17
 */
public class NBody {
    public static double readRadius(String file) {
        In in = new In(file);
        in.readLine();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String file) {
        In in = new In(file);
        int num = in.readInt();
        Planet[] planets = new Planet[num];
        in.readDouble();
        for (int i = 0; i < num; i++) {
            double xp = in.readDouble();
            double yp = in.readDouble();
            double xv = in.readDouble();
            double yv = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            planets[i] = new Planet(xp, yp, xv, yv, m, img);
        }
        return planets;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        // read init data
        double radius = readRadius(filename);
        Planet[] planets = NBody.readPlanets(filename);
        // init background
        StdDraw.setScale(-radius,radius);
        StdDraw.picture(0,0, "./images/starfield.jpg");
        for (Planet planet : planets) {
            planet.draw();
        }
        StdDraw.enableDoubleBuffering();

        // update planet position
        long time=0;
        while(time<T){
            time+=dt;
            double[] fx = new double[planets.length];
            double[] fy = new double[planets.length];

            for (int i = 0; i < planets.length; i++) {
                fx[i] = planets[i].calcNetForceExertedByX(planets);
                fy[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < planets.length; i++) {
                planets[i].update(dt,fx[i],fy[i]);
            }
            StdDraw.picture(0,0,"./images/starfield.jpg");
            for (Planet planet : planets) {
                planet.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }
        // print final position
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", planets[i].xxPos, planets[i].yyPos,
                    planets[i].xxVel, planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
