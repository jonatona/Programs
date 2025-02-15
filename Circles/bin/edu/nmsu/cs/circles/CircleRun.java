// even though package is being included, we are still
// getting error saying "cannot find symbol"
// on line 18, 19, 31, and 35
package edu.nmsu.cs.circles;

// attempted adding following line, did not work
//import Circle.java

/**
 * Sample tester for Circle1 and Circle2
 **/
public class CircleRun
{

	/**
	 * Accept command line args for two circles and then run their intersect() methods.
	 **/
	public static void main(String args[])
	{
		Circle1 c1;
		Circle2 c2;
		if (args.length != 6)
		{
		    // not desriptive enough, added line and edited
		    // where error is displayed
		    System.out.println("Error: Not enough arguments.\n");
			System.out.println("args must be x1 y1 r1 x2 y2 r2");
			return;
		}
		try
		{
			double x, y, r;
			x = Double.parseDouble(args[0]);
			y = Double.parseDouble(args[1]);
			r = Double.parseDouble(args[2]);
			c1 = new Circle1(x, y, r);
			x = Double.parseDouble(args[3]);
			y = Double.parseDouble(args[4]);
			r = Double.parseDouble(args[5]);
			c2 = new Circle2(x, y, r);
		}
		catch (Exception e)
		{
		    // not desriptive enough, made sure to add more detail
			System.out.println("Arguments must be double type values! " + e);
			e.printStackTrace();
			return;
		}
		System.out.println("Circle 1 says: " + c1.intersects(c2));
		System.out.println("Circle 2 says: " + c2.intersects(c1));
	}

}

// FIX: Added all classes to avoid "cannot find symbol" error

public class Point
{
	double	x;

	double	y;
}


public abstract class Circle
{
	protected Point		center;

	protected double	radius;

	/**
	 * Create new circle
	 *
	 * @param x
	 *          is the x coordinate of the center
	 * @param y
	 *          is the y coordinate of the center
	 * @param radius
	 *          is the radius
	 **/
	public Circle(double x, double y, double radius)
	{
		center = new Point();
		center.x = x;
		center.y = y;
		this.radius = radius;
	}

	/**
	 * Change size of circle
	 *
	 * @param factor
	 *          is the scaling factor (0.8 make it 80% as big, 2.0 doubles its size)
	 * @return the new radius
	 **/

	 // ERROR: factor is supposed to scale radius, but it
	 // performs an addition instead of a multiplication
	 // FIX: changed plus sign to multiplication sign
	public double scale(double factor)
	{
		//radius = radius + factor; WRONG
		radius *= factor; // CORRECT
		return radius;
	}

	/**
	 * Change position of circle relative to current position
	 *
	 * @param xOffset
	 *          is amount to change x coordinate
	 * @param yOffset
	 *          is amount to change y coordinate
	 * @return the new center coordinate
	 **/
	public Point moveBy(double xOffset, double yOffset)
	{
		center.x = center.x + xOffset;
		center.y = center.y + xOffset;
		return center;
	}

	/**
	 * Test if this circle intersects another circle.
	 *
	 * @param other
	 *          is the other circle
	 * @return True if the circles' outer edges intersect at all, False otherwise
	 **/
	public abstract boolean intersects(Circle other);

}

public class Circle1 extends Circle
{

	public Circle1(double x, double y, double radius)
	{
		super(x, y, radius);
	}

	// intersects function only works if it's within own radius's length,
	// does not account for other circle's radius
	// added lines to accommodate for this calculation error
	public boolean intersects(Circle other)
	{
	    double maxDist = radius + other.radius; // CORRECT

		// if (Math.abs(center.x - other.center.x) < radius &&
		//		Math.abs(center.y - other.center.y) < radius) WRONG

		if (Math.abs(center.x - other.center.x) < maxDist &&
		    Math.abs(center.y - other.center.y) < maxDist) // CORRECT
			return true;
		return false;
	}

}

public class Circle2 extends Circle
{

	public Circle2(double x, double y, double radius)
	{
	    // the order of parameters is not correct accoring to
	    // Circle constructor
		// super(y, x, radius); WRONG
	    super(x, y, radius); // CORRECT
	}

	// intersects function only works if it's within own radius's length,
	// does not account for other circle's radius
	// added lines to accommodate for this calculation error
	public boolean intersects(Circle other)
	{
		double d;
		double maxDist = radius + other.radius; // CORRECT

		d = Math.sqrt(Math.pow(center.x - other.center.x, 2) +
				Math.pow(center.y - other.center.y, 2));

		//if (d < radius) WRONG
		if (d < maxDist) // CORRECT
			return true;
		else
			return false;
	}

}

public class Circle1Test
{
	// Data you need for each test case
	private Circle1 circle1;

	//
	// Stuff you want to do before each test case
	//
	@Before
	public void setup()
	{
		System.out.println("\nTest starting...");
		circle1 = new Circle1(1, 2, 3);
	}

	//
	// Stuff you want to do after each test case
	//
	@After
	public void teardown()
	{
		System.out.println("\nTest finished.");
	}

	//
	// Test a simple positive move
	//
	@Test
	public void simpleMove()
	{
		Point p;
		System.out.println("Running test simpleMove.");
		p = circle1.moveBy(1, 1);
		Assert.assertTrue(p.x == 2 && p.y == 3);
	}

	//
	// Test a simple negative move
	//
	@Test
	public void simpleMoveNeg()
	{
		Point p;
		System.out.println("Running test simpleMoveNeg.");
		p = circle1.moveBy(-1, -1);
		Assert.assertTrue(p.x == 0 && p.y == 1);
	}

	/***
	 * NOT USED public static void main(String args[]) { try { org.junit.runner.JUnitCore.runClasses(
	 * java.lang.Class.forName("Circle1Test")); } catch (Exception e) { System.out.println("Exception:
	 * " + e); } }
	 ***/

}
