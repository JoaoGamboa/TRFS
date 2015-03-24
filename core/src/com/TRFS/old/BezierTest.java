package com.TRFS.old;

import java.util.ArrayList;

import com.TRFS.scenarios.map.Coordinate;

public class BezierTest {
	
	Coordinate[] firstControlPoints;
	Coordinate[] secondControlPoints;
	ArrayList<Coordinate> newCoordinates;
	
	public BezierTest(ArrayList<Coordinate> input)	{
		
		Coordinate[] knots = new Coordinate[input.size()];
		for (int i = 0; i < input.size(); i++) {
			knots[i] = new Coordinate(input.get(i).getX(), input.get(i).getY());
		}

			int n = knots.length - 1;
			if (n < 1)
				try {
					throw new Exception
					("At least two knot points required");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (n == 1)
			{ // Special case: Bezier curve should be a straight line.
				firstControlPoints = new Coordinate[1];
				// 3P1 = 2P0 + P3
				firstControlPoints[0] = new Coordinate((2 * knots[0].getX() + knots[1].getX()) / 3, (2 * knots[0].getY() + knots[1].getY()) / 3);

				secondControlPoints = new Coordinate[1];
				// P2 = 2P1 – P0
				
				secondControlPoints[0] = new Coordinate(2 *	firstControlPoints[0].getX() - knots[0].getX(), 
														2 *	firstControlPoints[0].getY() - knots[0].getY());
				return;
			}

			// Calculate first Bezier control points
			// Right hand side vector
			double[] rhs = new double[n];

			// Set right hand side X values
			for (int i = 1; i < n - 1; ++i)
				rhs[i] = 4 * knots[i].getX() + 2 * knots[i + 1].getX();
			rhs[0] = knots[0].getX() + 2 * knots[1].getX();
			rhs[n - 1] = (8 * knots[n - 1].getX() + knots[n].getX()) / 2.0;
			// Get first control points X-values
			double[] x = GetFirstControlPoints(rhs);

			// Set right hand side Y values
			for (int i = 1; i < n - 1; ++i)
				rhs[i] = 4 * knots[i].getY() + 2 * knots[i + 1].getY();
			rhs[0] = knots[0].getY() + 2 * knots[1].getY();
			rhs[n - 1] = (8 * knots[n - 1].getY() + knots[n].getY()) / 2.0;
			// Get first control points Y-values
			double[] y = GetFirstControlPoints(rhs);

			// Fill output arrays.
			firstControlPoints = new Coordinate[n];
			secondControlPoints = new Coordinate[n];
			for (int i = 0; i < n; ++i)
			{
				// First control point
				firstControlPoints[i] = new Coordinate((float) x[i],(float) y[i]);
				// Second control point
				if (i < n - 1)
					secondControlPoints[i] = new Coordinate((float) (2 * knots
						[i + 1].getX() - x[i + 1]),(float) ( 2 *
						knots[i + 1].getY() - y[i + 1]));
				else
					secondControlPoints[i] = new Coordinate((float) ((knots
						[n].getX() + x[n - 1]) / 2),
						(float) ((knots[n].getY() + y[n - 1]) / 2));
			}
			
			newCoordinates = new ArrayList<Coordinate>();
			for (int i = 0; i < knots.length-1; i++) {
				newCoordinates.add(input.get(i));
				newCoordinates.add(firstControlPoints[i]);
				newCoordinates.add(secondControlPoints[i]);				
			}
			newCoordinates.add(input.get(knots.length-1));
			
		}

		/// <summary>
		/// Solves a tridiagonal system for one of coordinates (x or y)
		/// of first Bezier control points.
		/// </summary>
		/// <param name="rhs">Right hand side vector.</param>
		/// <returns>Solution vector.</returns>
		private static double[] GetFirstControlPoints(double[] rhs)
		{
			int n = rhs.length;
			double[] x = new double[n]; // Solution vector.
			double[] tmp = new double[n]; // Temp workspace.

			double b = 2.0;
			x[0] = rhs[0] / b;
			for (int i = 1; i < n; i++) // Decomposition and forward substitution.
			{
				tmp[i] = 1 / b;
				b = (i < n - 1 ? 4.0 : 3.5) - tmp[i];
				x[i] = (rhs[i] - x[i - 1]) / b;
			}
			for (int i = 1; i < n; i++)
				x[n - i - 1] -= tmp[n - i] * x[n - i]; // Backsubstitution.

			return x;
		}

		public ArrayList<Coordinate> getNewCoordinates() {
			return newCoordinates;
		}
	}

