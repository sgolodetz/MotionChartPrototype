/**
 * motionchartprototype: MotionChart.java
 * Copyright Stuart Golodetz, 2012. All rights reserved.
 */

package net.gxstudios.mcp.client;

import net.gxstudios.mcp.shared.DataPoint;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MotionChart extends VerticalPanel
{
	//#################### PRIVATE VARIABLES ####################
	private final Canvas[] m_buffers = new Canvas[2];
	private Timer m_timer;
	private boolean m_dirty = false;
	private DataPoint[][] m_dataPointSets;
	private float m_time = 0;

	private int m_width = 640;
	private int m_height = 480;

	private int m_currentBuffer = 0;

	//#################### CONSTRUCTORS ####################
	public MotionChart()
	{
		// Create the front and back buffers.
		for(int i = 0; i < 2; ++i)
		{
			m_buffers[i] = Canvas.createIfSupported();
			if(m_buffers[i] == null)
			{
				add(new Label("This browser does not support HTML5 Canvas - sorry."));
				return;
			}

			m_buffers[i].setCoordinateSpaceWidth(m_width);
			m_buffers[i].setCoordinateSpaceHeight(m_height);

			m_buffers[i].setVisible(false);

			add(m_buffers[i]);
		}

		// Set up redrawing every 25 milliseconds.
		m_timer = new Timer()
		{
			public void run()
			{
				if(m_dirty)
				{
					draw();
					flipBuffers();
					m_dirty = false;
				}
			}
		};
		m_timer.scheduleRepeating(25);
	}

	//#################### PUBLIC METHODS ####################
	public float getTime()
	{
		return m_time;
	}

	public void setDataPointSets(DataPoint[][] dataPointSets)
	{
		m_dataPointSets = dataPointSets;
		m_dirty = true;
	}

	public boolean setTime(float time)
	{
		if(time < 0) return false;
		if(time > m_dataPointSets.length - 1) return false;

		m_time = time;
		m_dirty = true;

		return true;
	}

	//#################### PRIVATE METHODS ####################
	/**
	 * Draws the chart.
	 */
	private void draw()
	{
		Context2d context = getCurrentContext();

		// Draw the background.
		context.setFillStyle(CssColor.make(200, 200, 200));
		context.fillRect(0, 0, m_width, m_height);

		// Draw the data points.
		context.setFillStyle(CssColor.make(0, 0, 255));
		/*for(DataPoint p: m_dataPointSets[m_time])
		{
			context.fillRect(p.x - 10, p.y - 10, 20, 20);
		}*/

		int beforeTime = (int)Math.floor(m_time);
		int afterTime = (int)Math.ceil(m_time);
		float t = afterTime - m_time;
		float oneMinusT = 1 - t;
		for(int i = 0; i < m_dataPointSets[beforeTime].length && i < m_dataPointSets[afterTime].length; ++i)
		{
			float x = m_dataPointSets[beforeTime][i].x * t + m_dataPointSets[afterTime][i].x * oneMinusT;
			float y = m_dataPointSets[beforeTime][i].y * t + m_dataPointSets[afterTime][i].y * oneMinusT;
			context.fillRect(x - 10, y - 10, 20, 20);
		}
	}

	/**
	 * 'Flips' the front and back buffers.
	 */
	private void flipBuffers()
	{
		m_buffers[m_currentBuffer].setVisible(true);
		m_currentBuffer = 1 - m_currentBuffer;
		m_buffers[m_currentBuffer].setVisible(false);
	}

	/**
	 * Gets the context for whichever buffer is currently active.
	 * @return The context for the active buffer.
	 */
	private Context2d getCurrentContext()
	{
		return m_buffers[m_currentBuffer].getContext2d();
	}
}
