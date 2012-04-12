/**
 * motionchartprototype: MotionChartPrototype.java
 * Copyright Stuart Golodetz, 2012. All rights reserved.
 */

package net.gxstudios.mcp.client;

import net.gxstudios.mcp.shared.DataPoint;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * The entry point for the application.
 */
public class MotionChartPrototype implements EntryPoint
{
	private Timer m_timer;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad()
	{
		RootPanel page = RootPanel.get("page");
		final MotionChart motionChart = new MotionChart();
		page.add(motionChart);

		/*DataPoint[][] dataPointSets = new DataPoint[][]
		{
				new DataPoint[] { new DataPoint(50, 50), new DataPoint(150, 50), new DataPoint(25, 300) },
				new DataPoint[] { new DataPoint(50, 200), new DataPoint(300, 100), new DataPoint(25, 50) },
				new DataPoint[] { new DataPoint(50, 50), new DataPoint(150, 50), new DataPoint(300, 75) }
		};*/
		final int sampleCount = 3;
		final int pointCount = 500;
		DataPoint[][] dataPointSets = new DataPoint[sampleCount][pointCount];
		for(int time = 0; time < sampleCount; ++time)
		{
			for(int i = 0; i < pointCount; ++i)
			{
				int x = Random.nextInt(640);
				int y = Random.nextInt(480);
				dataPointSets[time][i] = new DataPoint(x, y);
			}
		}
		motionChart.setDataPointSets(dataPointSets);

		m_timer = new Timer()
		{
			private float m_sign = 1f;

			public void run()
			{
				if(!motionChart.setTime(motionChart.getTime() + m_sign * 0.01f))
				{
					m_sign = -m_sign;
				}
			}
		};
		m_timer.scheduleRepeating(25);
	}
}
