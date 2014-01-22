package org.overture.interpreter.messages.rtlog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import org.overture.interpreter.messages.Console;
import org.overture.interpreter.messages.rtlog.RTThreadSwapMessage.SwapType;

public class RTTextLogger implements IRTLogger
{
	private static List<RTMessage> events = new LinkedList<RTMessage>();
	private static PrintWriter logfile = null;
	private static RTMessage cached = null;
	private static boolean enabled = false;

	/*
	 * (non-Javadoc)
	 * @see org.overture.interpreter.messages.rtlog.IRTLogger#enable(boolean)
	 */
	@Override
	public synchronized void enable(boolean on)
	{
		if (!on)
		{
			dump(true);
			cached = null;
		}

		enabled = on;
	}

	/*
	 * (non-Javadoc)
	 * @see org.overture.interpreter.messages.rtlog.IRTLogger#log(org.overture.interpreter.messages.rtlog.RTMessage)
	 */
	@Override
	public synchronized void log(RTMessage message)
	{
		if (!enabled)
		{
			return;
		}
		// generate any static deploys required for this message
		message.generateStaticDeploys();

		doLog(message);

	}

	/*
	 * (non-Javadoc)
	 * @see org.overture.interpreter.messages.rtlog.IRTLogger#setLogfile(java.io.PrintWriter)
	 */
	private void setLogfile(PrintWriter out)
	{
		dump(true); // Write out and close previous
		logfile = out;
		cached = null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.overture.interpreter.messages.rtlog.IRTLogger#getLogSize()
	 */
	@Override
	public int getLogSize()
	{
		return events.size();
	}

	/*
	 * (non-Javadoc)
	 * @see org.overture.interpreter.messages.rtlog.IRTLogger#dump(boolean)
	 */
	@Override
	public synchronized void dump(boolean close)
	{
		if (logfile != null)
		{
			for (RTMessage event : events)
			{
				logfile.println(event.getMessage());
			}

			logfile.flush();
			events.clear();

			if (close)
			{
				logfile.close();
			}

		}
	}

	private synchronized void doLog(RTMessage message)
	{
		RTMessage event = message;

		if (event instanceof RTThreadSwapMessage
				&& (((RTThreadSwapMessage) event).getType() == SwapType.In || ((RTThreadSwapMessage) event).getType() == SwapType.DelayedIn))
		{
			if (cached != null)
			{
				doInternalLog(cached);
			}

			cached = event;
			return;
		}

		if (cached != null)
		{
			if (event instanceof RTThreadSwapMessage
					&& ((RTThreadSwapMessage) event).getType() == SwapType.Out)
			{
				RTThreadMessage eventThreadMessage = (RTThreadMessage) event;
				if (cached instanceof RTThreadSwapMessage)
				{
					RTThreadSwapMessage cachedThreadSwap = (RTThreadSwapMessage) cached;

					if ((cachedThreadSwap.getType() == SwapType.DelayedIn || cachedThreadSwap.getType() == SwapType.In)
							&& cachedThreadSwap.equals(eventThreadMessage.getThread())
							&& cachedThreadSwap.getLogTime().equals(eventThreadMessage.getLogTime()))
					{
						cached = null;
						return;
					}

				}
			}

			doInternalLog(cached);
			cached = null;
		}

		doInternalLog(event);
	}

	private void doInternalLog(RTMessage event)
	{
		if (logfile == null)
		{
			Console.out.println(event);
		} else
		{
			events.add(event);

			if (events.size() > 1000)
			{
				dump(false);
			}
		}
	}

	@Override
	public void setLogfile(File file) throws FileNotFoundException
	{
		if (file != null)
		{
			PrintWriter p = new PrintWriter(new FileOutputStream(file, true));
			setLogfile(p);
			enabled = true;
		}
	}
}
