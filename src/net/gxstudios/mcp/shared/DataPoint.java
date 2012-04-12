/**
 * motionchartprototype: DataPoint.java
 * Copyright Stuart Golodetz, 2012. All rights reserved.
 */

package net.gxstudios.mcp.shared;

public class DataPoint
{
	//#################### PUBLIC VARIABLES ####################
	public int x;
	public int y;

	//#################### CONSTRUCTORS ####################
	/**
	 * Constructs a new data point.
	 * @param x		The x coordinate of the data point
	 * @param y		The y coordinate of the data point
	 */
	public DataPoint(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}
