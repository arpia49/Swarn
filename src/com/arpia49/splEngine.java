package com.arpia49;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.Date;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;

/**
 * 
 * @author Hashir N A <hashir@mobware4u.com>
 * 
 */
public class splEngine extends Thread
{
   private static final int FREQUENCY = 8000;
   private static final int CHANNEL = AudioFormat.CHANNEL_CONFIGURATION_MONO;
   private static final int ENCODING = AudioFormat.ENCODING_PCM_16BIT;
   private static final int MY_MSG = 1;
   protected static final int MAXOVER_MSG = 2;
   private int BUFFSIZE = 320;
   private static final double P0 = 0.000002;
   public volatile boolean isRunning = false;
   private Handler handle;
   
   private static final String CALIB_SLOW_FILE_PATH = "/sdcard/.splslowcalib";
   private static final String CALIB_FAST_FILE_PATH = "/sdcard/.splfastcalib";
   private static final int CALIB_INCREMENT = 3;
   private static final int CALIB_DEFAULT = -80;
   private int caliberationValue = CALIB_DEFAULT;
   
   private double maxValue = 0.0;
   public boolean showMaxValue = false;
   
   private FileWriter splLog = null;
   private boolean isLogging = false;
   private static String LOGPATH = "/sdcard/splmeter_";
   private int LOGLIMIT = 50;
   private int logCount = 0;
   
   private String mode = "FAST";
   
   static AudioRecord recordInstance = null;
      
   /**
    * Reset the Engine by deleting all calibration details and resetting it to a
    * default value
    */
   public void reset()
   {
      File calibFile = new File(CALIB_SLOW_FILE_PATH);
      if (calibFile.exists())
      {
         calibFile.delete();
      }
      
      calibFile = new File(CALIB_FAST_FILE_PATH);
      if (calibFile.exists())
      {
         calibFile.delete();
      }
      
      caliberationValue = CALIB_DEFAULT;
      
   }
   
   /**
    * starts the engine.
    */
   public void start_engine()
   {
      
      this.isRunning = true;
      this.start();
      
   }
   
   /**
    * stops the engine
    */
   public void stop_engine()
   {
      this.isRunning = false;
      recordInstance.stop();
   }
   
   /**
    * sets mode as fast or slow
    * 
    * @param mode
    */
   public void setMode(String mode)
   {
      this.mode = mode;
      setCalibValue(readCalibValue());
      
      if ("SLOW".equals(mode))
      {
         BUFFSIZE = 8000;
         LOGLIMIT = 10;
      }
      else
      {
         BUFFSIZE = 320;
         LOGLIMIT = 50;
      }
      
   }
   
   /**
    * Returns the current calibration value
    * 
    * @return
    */
   public int getCalibValue()
   {
      return caliberationValue;
   }
   
   /**
    * Sets the calibration value to a value passed.
    * 
    * @param value
    */
   public void setCalibValue(int value)
   {
      caliberationValue = value;
   }
   
   /**
    * Reads the calibration details from the settings file. separate files used
    * for slow and fast mode if files doesn't exits, sets calibration to a
    * default value
    * 
    * @return
    */
   public int readCalibValue()
   {
      File calibFile;
      
      if ("SLOW".equals(mode))
      {
         calibFile = new File(CALIB_SLOW_FILE_PATH);
      }
      else
      {
         calibFile = new File(CALIB_FAST_FILE_PATH);
      }
      
      try
      {
         if (!calibFile.exists())
         {
            return CALIB_DEFAULT;
         }
         else
         {
            BufferedReader bfr = new BufferedReader(new FileReader(calibFile));
            while (bfr.ready())
            {
               return Integer.parseInt(bfr.readLine());
            }
            bfr.close();
            
         }
         
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      
      return CALIB_DEFAULT;
   }
   
   /**
    * Stores the current calibration value to a file separate calibration for
    * SLOW and FAST modes
    */
   public void storeCalibvalue()
   {
      try
      {
         FileWriter fw;
         if ("SLOW".equals(mode))
         {
            fw = new FileWriter(CALIB_SLOW_FILE_PATH);
         }
         else
         {
            fw = new FileWriter(CALIB_FAST_FILE_PATH);
         }
         
         fw.write(caliberationValue + "\n");
         fw.close();
         
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }
   
   /**
    * Increase the calibration by an fixed increment
    */
   public void calibUp()
   {
      caliberationValue = caliberationValue + CALIB_INCREMENT;
      if (caliberationValue == 0)
      {
         caliberationValue = caliberationValue + 1;
      }
      
   }
   
   /**
    * Descrease the calibration by a fixed value
    */
   public void calibDown()
   {
      caliberationValue = caliberationValue - CALIB_INCREMENT;
      if (caliberationValue == 0)
      {
         caliberationValue = caliberationValue - 1;
      }
   }
   
   /* Display max value for 2 seconds and then resume */
   public double showMaxValue()
   {
      showMaxValue = true;
      return maxValue;
   }
   
   /**
    * Get the maximum value recorded so far
    * 
    * @return
    */
   public double getMaxValue()
   {
      return maxValue;
   }
   
   /*
    * Sets the max value of SPL
    */
   public void setMaxValue(double max)
   {
      maxValue = max;
   }
   
   /*
    * Start logging the values to a log file
    */
   public void startLogging()
   {
      isLogging = true;
   }
   
   /*
    * Stop the logging
    */
   public void stopLogging()
   {
      isLogging = false;
   }
   
   /*
    * If logging, then store the spl values to a log file. separate log file for
    * each day.
    */
   private void writeLog(double value)
   {
      if (isLogging)
      {
         if (logCount++ > LOGLIMIT)
         {
            try
            {
               Date now = new Date();
               
               splLog = new FileWriter(LOGPATH + now.getDate() + "_"
                     + now.getMonth() + "_" + (now.getYear() + 1900) + ".xls",
                     true);
               splLog.append(value + "\n");
               splLog.close();
               
            }
            catch (Exception e)
            {
               
            }
            logCount = 0;
         }
      }
      
   }
   
   /*
    * The main thread. Records audio and calculates the SPL The heart of the
    * Engine.
    */
   public void run()
   {
      try
      {
         
         android.os.Process
               .setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
         recordInstance = new AudioRecord(MediaRecorder.AudioSource.MIC,
               FREQUENCY, CHANNEL, ENCODING, 8000);
         
         recordInstance.startRecording();
         short[] tempBuffer = new short[BUFFSIZE];
         
         while (this.isRunning)
         {
            double splValue = 0.0;
            double rmsValue = 0.0;
            
            for (int i = 0; i < BUFFSIZE - 1; i++)
            {
               tempBuffer[i] = 0;
            }
            
            recordInstance.read(tempBuffer, 0, BUFFSIZE);
            
            for (int i = 0; i < BUFFSIZE - 1; i++)
            {
               rmsValue += tempBuffer[i] * tempBuffer[i];
               
            }
            rmsValue = rmsValue / BUFFSIZE;
            rmsValue = Math.sqrt(rmsValue);
            
            splValue = 20 * Math.log10(rmsValue / P0);
            splValue = splValue + caliberationValue;
            splValue = round(splValue, 2);
            
            if (maxValue < splValue)
            {
               maxValue = splValue;
            }
            
            if (!showMaxValue)
            {
               Message msg = handle.obtainMessage(MY_MSG, splValue);
               handle.sendMessage(msg);
            }
            else
            {
               Message msg = handle.obtainMessage(MY_MSG, maxValue);
               handle.sendMessage(msg);
               Thread.sleep(2000);
               msg = handle.obtainMessage(MAXOVER_MSG, maxValue);
               handle.sendMessage(msg);
               showMaxValue = false;
               
            }
            
            writeLog(splValue);
            
         }
         
         recordInstance.stop();
      }
      catch (Exception e)
      {
         
      }
   }
   
   /*
    * Utility function for rounding decimal values
    */
   public double round(double d, int decimalPlace)
   {
      // see the Javadoc about why we use a String in the constructor
      // http://java.sun.com/j2se/1.5.0/docs/api/java/math/BigDecimal.html#BigDecimal(double)
      BigDecimal bd = new BigDecimal(Double.toString(d));
      bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
      return bd.doubleValue();
   }
   
}